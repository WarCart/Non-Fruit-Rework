package net.warcar.non_fruit_rework.abilities.cyborg;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.cyborg.WeaponsLeftQuest;
import xyz.pixelatedw.mineminenomi.abilities.cyborg.CyborgHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.extra.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.extra.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.extra.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.List;

public class WeaponsLeftAbility extends Ability {
    public static final AbilityCore<WeaponsLeftAbility> INSTANCE = new AbilityCore.Builder<>("Weapons Left", AbilityCategory.RACIAL, WeaponsLeftAbility::new)
            .setUnlockCheck(WeaponsLeftAbility::canUnlock).setSourceHakiNature(SourceHakiNature.IMBUING).build();

    private final AltModeComponent<Mode> modeComponent = new AltModeComponent<>(this, Mode.class, Mode.MACHINE_GUN).addChangeModeEvent(this::changeMode);
    private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
    private final ContinuousComponent continuousComponent = new ContinuousComponent(this, true).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
    private final RepeaterComponent repeaterComponent = new RepeaterComponent(this).addTriggerEvent(this::triggerRepeaterEvent).addStopEvent(this::stopRepeaterEvent);

    private ItemStack bulletStack = ItemStack.EMPTY;

    public WeaponsLeftAbility(AbilityCore<WeaponsLeftAbility> core) {
        super(core);
        this.isNew = true;
        this.addComponents(modeComponent, projectileComponent, continuousComponent, repeaterComponent);
        this.addUseEvent(this::onUse);
    }

    private static boolean canUnlock(LivingEntity entity) {
        return EntityHelper.isTrueRace(entity, ModValues.CYBORG) || QuestHelper.hasFinishedQuest(entity, WeaponsLeftQuest.INSTANCE);
    }

    private void changeMode(LivingEntity entity, IAbility ability, Mode mode) {
        this.setDisplayName(new StringTextComponent(INSTANCE.getLocalizedName().getString() + ": " + new TranslationTextComponent("ability.non_fruit_rework.weapons_left.mode." + WyHelper.getResourceName(mode.name())).getString()));
    }

    private void onUse(LivingEntity entity, IAbility ability) {
        AbilityUseResult result = this.canUse(entity, ability);
        if (result.isFail() && result.getMessage() != null) {
            entity.sendMessage(result.getMessage(), Util.NIL_UUID);
            return;
        }
        if (this.modeComponent.isMode(Mode.CANON)) {
            this.projectileComponent.shoot(entity, 3, 1);
            this.cooldownComponent.startCooldown(entity, 300.0F);
        } else {
            this.continuousComponent.triggerContinuity(entity);
        }
    }

    private AbilityProjectileEntity createProjectile(LivingEntity entity) {
        if (this.modeComponent.isMode(Mode.CANON)) {
            CannonBallProjectile projectile = new CannonBallProjectile(entity.level, entity, INSTANCE);
            projectile.setDamage(30);
            return projectile;
        }
        if (this.bulletStack.getItem() == ModItems.KAIROSEKI_BULLET.get()) {
            return new KairosekiBulletProjectile(entity.level, entity);
        }
        return new NormalBulletProjectile(entity.level, entity);
    }

    private AbilityUseResult canUse(LivingEntity entity, IAbility ability) {
        if (this.modeComponent.isMode(Mode.CANON)) {
            AbilityUseResult colaResult = CyborgHelper.hasEnoughCola(20).canUse(entity, ability);
            AbilityUseResult ball;
            if (entity instanceof PlayerEntity && ((PlayerEntity) entity).inventory.countItem(ModItems.CANNON_BALL.get()) == 0) {
                ball = AbilityUseResult.fail(new TranslationTextComponent(ModI18n.INFO_NEEDS_CANNONBALL_LOADED));
            } else {
                ball = AbilityUseResult.success();
            }
            return AbilityUseResult.and(colaResult, ball);
        } else {
            return CyborgHelper.hasEnoughCola(2).canUse(entity, ability);
        }
    }

    private void startContinuityEvent(LivingEntity entity, IAbility ability) {
        this.repeaterComponent.start(entity, 40, 5);
    }

    private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
        if (EntityStatsCapability.get(entity).getCola() < 2) {
            this.continuousComponent.stopContinuity(entity);
            return;
        }
        EntityStatsCapability.get(entity).alterCola(-2);
        if (this.bulletStack.isEmpty()) {
            this.findNewStack(entity);
            if (this.bulletStack.isEmpty()) {
                this.continuousComponent.stopContinuity(entity);
            }
        }

    }

    private void endContinuityEvent(LivingEntity entity, IAbility ability) {
        this.repeaterComponent.stop(entity);
        this.cooldownComponent.startCooldown(entity, 300.0F);
    }

    private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
        float innacuracy = 1.0F;
        if (entity.isSprinting()) {
            innacuracy = 3.0F;
        }

        if (entity.isCrouching()) {
            innacuracy = 0.0F;
        }

        for (int i = 0; i < 4; i++) {
            this.projectileComponent.shootWithSpread(entity, 3.0F, innacuracy, 2);
            this.bulletStack.shrink(1);
        }
    }

    private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
        this.continuousComponent.stopContinuity(entity);
    }

    private void findNewStack(LivingEntity entity) {
        List<ItemStack> inventory = ItemsHelper.getAllInventoryItems(entity);

        for (ItemStack stack : inventory) {
            if (stack != null && !stack.isEmpty() && ModGunItem.GUN_AMMO.test(stack) && stack.getCount() >= 4) {
                this.bulletStack = stack;
                break;
            }
        }

    }

    public enum Mode {
        MACHINE_GUN,
        CANON
    }
}
