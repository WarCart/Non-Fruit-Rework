package net.warcar.non_fruit_rework.entities.bosses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.warcar.non_fruit_rework.abilities.fishman.FishmanPowerAbility;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SharkOnToothAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUseResult;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.challenges.OPBossEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.GankingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.ai.controllers.HumanoidSwimMoveController;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.*;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.TakedownKickWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.blackleg.PartyTableKickCourseWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.brawler.ChargedPunchWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.fishman.*;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiFullbodyHardeningWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiInternalDestructionWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.phases.SimplePhase;
import xyz.pixelatedw.mineminenomi.init.*;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class HodyJonesBoss extends OPBossEntity<HodyJonesBoss> {
    public static final EntityType<HodyJonesBoss> INSTANCE = WyRegistry.createEntityType(HodyJonesBoss::new).build("");

    private static final UUID STANDARD_UCHIMIZU_COOLDOWN_UUID = UUID.fromString("efa25144-6946-48c2-8e2a-cb2135b98783");
    private static final UUID HARD_MURASAME_COOLDOWN_UUID = UUID.fromString("08737d15-31f0-4103-b09a-99cd98e72cda");
    private static final UUID WATER_UCHIMIZU_COOLDOWN_UUID = UUID.fromString("55220e18-d068-4913-9cf5-ba7c57eeeb4e");
    private static final UUID WATER_SHARK_ON_TOOTH_COOLDOWN_UUID = UUID.fromString("c5c03564-411b-4a21-85e9-e9b003c91123");
    private static final AttributeModifier GCD_MOD;
    private RevengeMeter revengeMeter;
    private float revengeThreshold;
    private MovementController groundMovementController;
    private MovementController waterMovementController;
    private NPCPhase<HodyJonesBoss> firstPhase;
    private NPCPhase<HodyJonesBoss> secondPhase;
    private NPCPhase<HodyJonesBoss> waterPhase;
    private Optional<CooldownComponent> uchimizuCooldownComponent;
    private SharkOnToothWrapperGoal sharkOnToothWrapper;
    private Optional<CooldownComponent> sharkOnToothCooldownComponent;

    public HodyJonesBoss(EntityType type, World world) {
        super(type, world);
    }

    public HodyJonesBoss(InProgressChallenge challenge) {
        super(INSTANCE, challenge);
        this.revengeThreshold = challenge.isStandardDifficulty() ? 0.5F : 0.3F;
    }

    public void initBoss() {
        this.revengeMeter = new RevengeMeter(this, 100, 1);
        this.groundMovementController = new MovementController(this);
        this.waterMovementController = new HumanoidSwimMoveController(this);
        this.firstPhase = new SimplePhase<>("First Phase", this);
        this.secondPhase = new SimplePhase<>("Second Phase", this, this::startSecondPhaseEvent);
        this.waterPhase = new SimplePhase<>("Water Phase", this, this::startWaterPhaseEvent, this::stopWaterPhaseEvent);
        this.entityStats.setFaction(ModValues.PIRATE);
        this.entityStats.setRace(ModValues.FISHMAN);
        this.entityStats.setFightingStyle(ModValues.BRAWLER);
        ExtendedWorldData worldData = ExtendedWorldData.get();
        worldData.addTemporaryCrewMember(ModNPCGroups.ARLONG_PIRATES, this);
        this.getAttribute(ModAttributes.TOUGHNESS.get()).setBaseValue(2.0);
        this.getAttribute(ModAttributes.STEP_HEIGHT.get()).setBaseValue(1.0);
        this.getRevengeMeter().addCheck(new PhysicalHitRevengeCheck(1));
        this.getRevengeMeter().addCheck(new GankingRevengeCheck(5, 5.0F));
        UchimizuWrapperGoal uchimizuWrapper = new UchimizuWrapperGoal(this);
        Optional<CooldownComponent> cooldownComponent = uchimizuWrapper.getAbility().getComponent(ModAbilityKeys.COOLDOWN);
        cooldownComponent.ifPresent((comp) -> {
            comp.getBonusManager().addBonus(STANDARD_UCHIMIZU_COOLDOWN_UUID, "Standard Uchimizu Bonus", BonusOperation.MUL, 2.0F);
            comp.startCooldown(this, 120.0F);
        });
        KachiageHaisokuWrapperGoal kachiageWrapper = new KachiageHaisokuWrapperGoal(this);
        kachiageWrapper.getAbility().addCanUseCheck((entity, ability) -> {
            ChargedPunchAbility abl = AbilityDataCapability.get(entity).getEquippedAbility(ChargedPunchAbility.INSTANCE);
            return abl != null && abl.isCharging() ? AbilityUseResult.fail(null) : AbilityUseResult.success();
        });
        ChargedPunchWrapperGoal chargedPunchWrapper = new ChargedPunchWrapperGoal(this);
        chargedPunchWrapper.getAbility().addCanUseCheck((entity, ability) -> {
            KachiageHaisokuAbility abl = AbilityDataCapability.get(entity).getEquippedAbility(KachiageHaisokuAbility.INSTANCE);
            return abl != null && abl.isContinuous() ? AbilityUseResult.fail(null) : AbilityUseResult.success();
        });
        SamehadaShoteiWrapperGoal samehadaWrapper = new SamehadaShoteiWrapperGoal(this);
        samehadaWrapper.getAbility().addCanUseCheck((entity, ability) -> {
            SharkOnToothAbility abl = AbilityDataCapability.get(entity).getEquippedAbility(SharkOnToothAbility.INSTANCE);
            return abl != null && abl.isContinuous() ? AbilityUseResult.fail(null) : AbilityUseResult.success();
        });
        this.sharkOnToothWrapper = new SharkOnToothWrapperGoal(this);
        this.sharkOnToothCooldownComponent = this.sharkOnToothWrapper.getAbility().getComponent(ModAbilityKeys.COOLDOWN);
        this.uchimizuCooldownComponent = uchimizuWrapper.getAbility().getComponent(ModAbilityKeys.COOLDOWN);
        SharkOnToothAbility sharkOnToothAbility = this.sharkOnToothWrapper.getAbility();
        sharkOnToothAbility.addCanUseCheck((entity, ability) -> {
            LivingEntity target = this.getTarget();
            if (this.waterPhase.isActive(this) && target != null) {
                if (EntityStatsCapability.get(target).isFishman()) {
                    return AbilityUseResult.success();
                }

                if (target.getY() - this.getY() <= 2.0) {
                    return AbilityUseResult.fail(null);
                }
            }

            return AbilityUseResult.success();
        });
        sharkOnToothAbility.addTickEvent((entity, ability) -> {
            if (this.waterPhase.isActive(this) && sharkOnToothAbility.isContinuous() && sharkOnToothAbility.hasHitTarget()) {
                LivingEntity target = sharkOnToothAbility.getComponent(ModAbilityKeys.DAMAGE).get().getLastTarget();
                if (target != null) {
                    sharkOnToothAbility.getComponent(ModAbilityKeys.CONTINUOUS).get().stopContinuity(this);
                    sharkOnToothAbility.getComponent(ModAbilityKeys.ANIMATION).get().start(this, ModAnimations.TAKEDOWN_KICK, 7);
                    AbilityHelper.setDeltaMovement(target, entity.getDeltaMovement().x, -5.0, entity.getDeltaMovement().z);
                }
            }

        });
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        Predicate<Entity> factionScope = ModEntityPredicates.getEnemyFactions(this).and(ModEntityPredicates.IS_ENTITY_HARMLESS.negate());
        this.targetSelector.addGoal(1, new FactionHurtByTargetGoal(this, factionScope));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, MobEntity.class, 10, true, true, factionScope));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, 10, true, true, factionScope));
        this.goalSelector.addGoal(0, new AlwaysActiveAbilityWrapperGoal<>(this, FishmanPowerAbility.INSTANCE));
        this.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.0, true));
        this.firstPhase.addGoal(1, new SprintTowardsTargetGoal(this));
        this.firstPhase.addGoal(3, uchimizuWrapper);
        this.firstPhase.addGoal(3, new TakedownKickWrapperGoal(this));
        this.firstPhase.addGoal(4, chargedPunchWrapper);
        this.firstPhase.addGoal(4, samehadaWrapper);
        this.firstPhase.addGoal(4, kachiageWrapper);
        this.firstPhase.addGoal(4, this.sharkOnToothWrapper);
        this.secondPhase.addGoal(1, new SprintTowardsTargetGoal(this));
        this.secondPhase.addGoal(3, uchimizuWrapper);
        this.secondPhase.addGoal(3, new TakedownKickWrapperGoal(this));
        this.secondPhase.addGoal(4, samehadaWrapper);
        this.secondPhase.addGoal(4, kachiageWrapper);
        this.secondPhase.addGoal(4, this.sharkOnToothWrapper);
        this.waterPhase.addGoal(0, new DashDodgeTargetGoal(this, 100.0F, 5.0F));
        this.waterPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 70.0F, 2.25F));
        this.waterPhase.addGoal(3, uchimizuWrapper);
        this.waterPhase.addGoal(4, this.sharkOnToothWrapper);
        this.getPhaseManager().setPhase(this.firstPhase);
        if (this.getChallengeInfo().isDifficultyStandard()) {
            this.entityStats.setDoriki(4500.0);
            this.getAttribute(ModAttributes.GCD.get()).setBaseValue(40.0);
            this.getAttribute(ModAttributes.PUNCH_DAMAGE.get()).setBaseValue(2.0);
            this.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(3.0);
            this.getAttribute(ModAttributes.FAUX_PROTECTION.get()).setBaseValue(4.0);
            this.getRevengeMeter().addCheck(new DeadzoneRevengeCheck(5));
        } else {
            this.entityStats.setDoriki(10000.0);
            this.hakiCapability.setBusoshokuHakiExp(100.0F);
            this.hakiCapability.setKenbunshokuHakiExp(100.0F);
            this.getAttribute(ModAttributes.GCD.get()).setBaseValue(20.0);
            this.getAttribute(ModAttributes.PUNCH_DAMAGE.get()).setBaseValue(5.0);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0);
            this.getAttribute(ModAttributes.TOUGHNESS.get()).setBaseValue(8.0);
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(400.0);
            this.getAttribute(Attributes.ARMOR).setBaseValue(20.0);
            this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8.0);
            this.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(5.0);
            this.getAttribute(ModAttributes.FAUX_PROTECTION.get()).setBaseValue(16.0);
            this.getRevengeMeter().addCheck(new DeadzoneRevengeCheck(10));
            MurasameWrapperGoal murasameWrapper = new MurasameWrapperGoal(this);
            Optional<CooldownComponent> murasameCooldownComponent = murasameWrapper.getAbility().getComponent(ModAbilityKeys.COOLDOWN);
            murasameCooldownComponent.ifPresent((comp) -> {
                comp.getBonusManager().addBonus(HARD_MURASAME_COOLDOWN_UUID, "Murasame Cooldown Bonus", BonusOperation.ADD, -200.0F);
                comp.startCooldown(this, 100.0F);
            });
            Optional<CooldownComponent> uchimizuCooldownComponent = uchimizuWrapper.getAbility().getComponent(ModAbilityKeys.COOLDOWN);
            uchimizuCooldownComponent.ifPresent((comp) -> {
                comp.getBonusManager().removeBonus(STANDARD_UCHIMIZU_COOLDOWN_UUID);
            });
            this.goalSelector.addGoal(0, new BusoshokuHakiInternalDestructionWrapperGoal(this));
            this.goalSelector.addGoal(0, new BusoshokuHakiFullbodyHardeningWrapperGoal(this));
            this.goalSelector.addGoal(3, murasameWrapper);
            this.firstPhase.addGoal(3, new PartyTableKickCourseWrapperGoal(this));
            this.secondPhase.addGoal(0, new DashDodgeTargetGoal(this, 120.0F, 5.0F));
            this.secondPhase.addGoal(3, new PartyTableKickCourseWrapperGoal(this));
        }

    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 60.0).add(Attributes.MOVEMENT_SPEED, 0.30000001192092896).add(Attributes.ATTACK_DAMAGE, 4.0).add(Attributes.MAX_HEALTH, 300.0).add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
    }

    public void tick() {
        super.tick();
        if (!this.level.isClientSide && this.isAlive()) {
            this.getRevengeMeter().tick();
            if (this.secondPhase.isActive(this) && this.getRevengeMeter().getRevengePercentage() > this.revengeThreshold && this.sharkOnToothCooldownComponent.isPresent() && this.sharkOnToothCooldownComponent.get().isOnCooldown()) {
                this.sharkOnToothCooldownComponent.get().stopCooldown(this);
                this.getRevengeMeter().reduceRevengeValue(25);
            }

            if (this.isInWater()) {
                this.getPhaseManager().setPhase(this.waterPhase);
            } else if (this.waterPhase.isActive(this) && this.getPhaseManager().getPreviousPhase() != null) {
                this.getPhaseManager().setPhase(this.getPhaseManager().getPreviousPhase());
            } else if (this.firstPhase.isActive(this) && (double)this.getHealth() <= WyHelper.percentage(50.0, this.getMaxHealth())) {
                this.getPhaseManager().setPhase(this.secondPhase);
            } else if (!this.secondPhase.isActive(this)) {
                this.getPhaseManager().setPhase(this.firstPhase);
            }
        }

    }

    private void startSecondPhaseEvent(HodyJonesBoss entity) {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.TRIDENT));
        this.getAttribute(ModAttributes.ATTACK_RANGE.get()).setBaseValue(1.0);
        NonFruitDataCapability.get(entity).popEnergySteroids(1);
        if (this.isDifficultyHardOrAbove()) {
            ModifiableAttributeInstance attr = entity.getAttribute(ModAttributes.GCD.get());
            if (attr != null && !attr.hasModifier(GCD_MOD)) {
                attr.addTransientModifier(GCD_MOD);
            }
            NonFruitDataCapability.get(entity).popEnergySteroids(2);
        }
    }

    private void startWaterPhaseEvent(HodyJonesBoss entity) {
        this.moveControl = this.waterMovementController;
        this.uchimizuCooldownComponent.ifPresent((comp) -> {
            if (!comp.getBonusManager().hasBonus(WATER_UCHIMIZU_COOLDOWN_UUID)) {
                comp.getBonusManager().addBonus(WATER_UCHIMIZU_COOLDOWN_UUID, "Uchimizu Cooldown Water Bonus", BonusOperation.MUL, 0.5F);
            }

        });
        this.sharkOnToothCooldownComponent.ifPresent((comp) -> {
            if (!comp.getBonusManager().hasBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID)) {
                comp.getBonusManager().addBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID, "Shark on Tooth Cooldown Water Bonus", BonusOperation.MUL, 0.5F);
            }

        });
        this.sharkOnToothWrapper.setMaxDistance(50.0);
    }

    private void stopWaterPhaseEvent(HodyJonesBoss entity) {
        this.moveControl = this.groundMovementController;
        this.uchimizuCooldownComponent.ifPresent((comp) -> {
            comp.getBonusManager().removeBonus(WATER_UCHIMIZU_COOLDOWN_UUID);
        });
        this.sharkOnToothCooldownComponent.ifPresent((comp) -> {
            comp.getBonusManager().removeBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID);
        });
        this.sharkOnToothWrapper.setMaxDistance(10.0);
    }

    public RevengeMeter getRevengeMeter() {
        return this.revengeMeter;
    }

    static {
        GCD_MOD = new AttributeModifier(UUID.fromString("e478aceb-9865-40da-a137-6359ba503bf0"), "GCD Modifier", -5.0, AttributeModifier.Operation.ADDITION);
    }
}
