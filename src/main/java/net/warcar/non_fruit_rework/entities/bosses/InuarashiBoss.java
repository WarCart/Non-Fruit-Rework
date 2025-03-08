package net.warcar.non_fruit_rework.entities.bosses;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.entities.IHasImplantedSword;
import net.warcar.non_fruit_rework.entities.goals.TransformationWrapperGoal;
import net.warcar.non_fruit_rework.entities.goals.mink.EleclawWrapperGoal;
import net.warcar.non_fruit_rework.entities.goals.mink.ElectricalLunaWrapperGoal;
import net.warcar.non_fruit_rework.entities.goals.mink.ElectricalMissileWrapperGoal;
import net.warcar.non_fruit_rework.entities.goals.mink.ElectricalTempestaWrapperGoal;
import xyz.pixelatedw.mineminenomi.abilities.electro.SulongAbility;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.challenges.OPBossEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiEmissionWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiImbuingWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.rokushiki.GeppoWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.rokushiki.TekkaiWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.swordsman.SanbyakurokujuPoundHoWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.swordsman.ShiShishiSonsonWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.swordsman.YakkodoriWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.phases.SimplePhase;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

public class InuarashiBoss extends OPBossEntity<InuarashiBoss> implements IHasImplantedSword {
    public static final EntityType<InuarashiBoss> INSTANCE = WyRegistry.createEntityType(InuarashiBoss::new).build("");

    private final NPCPhase<InuarashiBoss> basicPhase = new SimplePhase<>("Basic phase", this);
    private final NPCPhase<InuarashiBoss> sulongPhase = new SimplePhase<>("Sulong phase", this);
    private final NPCPhase<InuarashiBoss> finalPhase = new SimplePhase<>("Final phase", this, InuarashiBoss::startFinalPhase);

    public InuarashiBoss(EntityType type, World world) {
        super(type, world);
    }

    public InuarashiBoss(InProgressChallenge inProgressChallenge) {
        super(INSTANCE, inProgressChallenge);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 60.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 8.0).add(Attributes.MAX_HEALTH, 400.0).add(Attributes.ARMOR, 3.0).add(Attributes.KNOCKBACK_RESISTANCE, 0.2);
    }

    @Override
    public void preInit() {
        this.entityStats.setRace(ModValues.MINK);
        this.entityStats.setSubRace(ModValues.MINK_DOG);
    }

    @Override
    public void initBoss() {
        super.initBoss();
        this.entityStats.setFaction(ModValues.PIRATE);
        this.entityStats.setFightingStyle(ModValues.SWORDSMAN);
        this.entityStats.setDoriki(5000);
        this.hakiCapability.setKenbunshokuHakiExp(35);
        this.hakiCapability.setBusoshokuHakiExp(60);
        //Generics
        MobsHelper.addBasicNPCGoals(this);
        this.basicPhase.addGoal(3, new GeppoWrapperGoal(this));
        this.goalSelector.addGoal(3, new TekkaiWrapperGoal(this));
        //Haki
        this.basicPhase.addGoal(1, new BusoshokuHakiImbuingWrapperGoal(this));
        this.sulongPhase.addGoal(1, new BusoshokuHakiImbuingWrapperGoal(this));
        this.finalPhase.addGoal(1, new BusoshokuHakiEmissionWrapperGoal(this));
        //Electro
        this.goalSelector.addGoal(2, new EleclawWrapperGoal(this));
        this.goalSelector.addGoal(2, new ElectricalLunaWrapperGoal(this));
        this.goalSelector.addGoal(2, new ElectricalMissileWrapperGoal(this));
        this.goalSelector.addGoal(2, new ElectricalTempestaWrapperGoal(this));
        this.sulongPhase.addGoal(1, new TransformationWrapperGoal<>(this, SulongAbility.INSTANCE));
        this.finalPhase.addGoal(1, new TransformationWrapperGoal<>(this, SulongAbility.INSTANCE));

        //Swordsman
        this.sulongPhase.addGoal(2, new ShiShishiSonsonWrapperGoal(this));
        this.finalPhase.addGoal(2, new ShiShishiSonsonWrapperGoal(this));
        this.sulongPhase.addGoal(2, new YakkodoriWrapperGoal(this));
        this.finalPhase.addGoal(2, new YakkodoriWrapperGoal(this));
        this.finalPhase.addGoal(2, new SanbyakurokujuPoundHoWrapperGoal(this));
        this.getPhaseManager().setPhase(basicPhase);
    }

    private static void startFinalPhase(InuarashiBoss entity) {
        entity.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModWeapons.CUTLASS.get()));
    }

    @Override
    public void tick() {
        super.tick();
        float hpPercentage = this.getHealth() / this.getMaxHealth();
        if (hpPercentage < 0.25) {
            this.getPhaseManager().setPhase(finalPhase);
        } else if (hpPercentage < 0.5) {
            this.getPhaseManager().setPhase(sulongPhase);
        }
    }
}
