package net.warcar.non_fruit_rework.mixin.advanced_rokushiki;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.ShiganMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility2;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesource.SourceType;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.gomu.GomuGomuNoPistolProjectile;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.ArrayList;
import java.util.List;

@Mixin(ShiganAbility.class)
public abstract class ShiganMixin extends PunchAbility2 {
    @Unique private final AltModeComponent<ShiganMode> modeComponent = new AltModeComponent<>(this, ShiganMode.class, ShiganMode.SIMPLE);
    @Unique private final RangeComponent rangeComponent = new RangeComponent(this);
    @Unique private final RepeaterComponent repeaterComponent = new RepeaterComponent(this).addTriggerEvent(this::trigger).addStopEvent(this::stop);

    private ShiganMixin(AbilityCore<? extends PunchAbility2> core) {
        super(core);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void onInit(AbilityCore<ShiganAbility> core, CallbackInfo ci) {
        this.addComponents(modeComponent, rangeComponent, repeaterComponent);
        IHasQuestRequirement.addAltModeEvent(modeComponent);
        this.addUseEvent(this::onUse);
        this.continuousComponent.addStartEvent(this::startContinuous).addEndEvent(this::endContinuous);
    }

    @Override
    public float getPunchCooldown() {
        if (this.modeComponent.isMode(ShiganMode.JUSHIGAN)) {
            return 520;
        } else if (this.modeComponent.isMode(ShiganMode.OREN)) {
            return this.repeaterComponent.getTriggerCount() * 23 + 40;
        }
        return 160;
    }

    @Override
    public float getPunchDamage() {
        if (this.modeComponent.isMode(ShiganMode.JUSHIGAN)) {
            return 40;
        } else if (this.modeComponent.isMode(ShiganMode.TOBU)) {
            return 15;
        } else if (this.modeComponent.isMode(ShiganMode.OREN)) {
            return 10;
        }
        return 25;
    }

    @Override
    public int getUseLimit() {
        if (this.modeComponent.isMode(ShiganMode.OREN)) {
            return -1;
        }
        return 1;
    }

    @Override
    public float getPunchHoldTime() {
        if (this.modeComponent.isMode(ShiganMode.TOBU)) {
            return 0;
        }
        return super.getPunchHoldTime();
    }

    private void onUse(LivingEntity entity, IAbility iAbility) {
        if (this.modeComponent.isMode(ShiganMode.TOBU)) {
            List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 15, 1);
            ModDamageSource source = ModDamageSource.causeAbilityDamage(entity, this).setHakiNature(SourceHakiNature.IMBUING);
            ArrayList<SourceType> types = source.getSourceTypes();
            types.remove(SourceType.FIST);
            source.setSourceTypes(types);
            if (!targets.isEmpty() && this.onHitEffect(entity, targets.get(0), source)) {
                targets.get(0).hurt(source, this.getPunchDamage());
            }
            this.cooldownComponent.startCooldown(entity, this.getPunchCooldown());
        }
    }

    private void trigger(LivingEntity entity, IAbility iAbility) {
        float speed = 2.2F;
        int projectileSpace = 2;
        float projDmageReduction = 0.6F;

        for(int i = 0; i < 5; ++i) {
            AbilityProjectileEntity projectile = new GomuGomuNoPistolProjectile(entity.level, entity);
            projectile.setEntityCollisionSize(1.25F);
            projectile.setMaxLife(3);
            projectile.setDamage(projectile.getDamage() * (1.0F - projDmageReduction));
            projectile.setMaxLife((int)(projectile.getMaxLife() * 0.75));
            double px = entity.getX() + WyHelper.randomWithRange(-projectileSpace, projectileSpace) + WyHelper.randomDouble();
            double py = entity.getEyeY() + WyHelper.randomWithRange(0, projectileSpace) + WyHelper.randomDouble();
            double pz = entity.getZ() + WyHelper.randomWithRange(-projectileSpace, projectileSpace) + WyHelper.randomDouble();
            projectile.moveTo(px, py, pz, 0.0F, 0.0F);
            entity.level.addFreshEntity(projectile);
            projectile.shootFromRotation(entity, entity.xRot, entity.yRot, 0.0F, speed, 3.0F);
        }
        this.onHitEffect(entity, entity, null);
        entity.swing(Hand.MAIN_HAND, true);
    }

    private void startContinuous(LivingEntity entity, IAbility iAbility) {
        if (this.modeComponent.isMode(ShiganMode.OREN)) {
            this.repeaterComponent.start(entity, 20, 2);
        }
    }

    private void stop(LivingEntity entity, IAbility iAbility) {
        this.continuousComponent.stopContinuity(entity);
    }

    private void endContinuous(LivingEntity entity, IAbility ability) {
        this.repeaterComponent.stop(entity);
    }
}
