package net.warcar.non_fruit_rework.mixin.advanced_rokushiki;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.RankyakuMode;
import net.warcar.non_fruit_rework.entities.projectiles.rankyaku.HakuraiProjectile;
import net.warcar.non_fruit_rework.entities.projectiles.rankyaku.RankyakuRanProjectile;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.rokushiki.RankyakuProjectile;

@Mixin(RankyakuAbility.class)
public abstract class RankyakuMixin extends Ability {
    @Unique private final AltModeComponent<RankyakuMode> modeComponent = new AltModeComponent<>(this, RankyakuMode.class, RankyakuMode.SIMPLE);
    @Unique private final ContinuousComponent continuousComponent = new ContinuousComponent(this).addStartEvent(this::startContinuous).addEndEvent(this::endContinuous);
    @Unique private final RepeaterComponent repeaterComponent = new RepeaterComponent(this).addTriggerEvent(this::trigger).addStopEvent(this::stop);
    @Shadow @Final private ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

    private RankyakuMixin(AbilityCore<? extends IAbility> core) {
        super(core);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void onInit(AbilityCore<RankyakuAbility> core, CallbackInfo ci) {
        this.addComponents(modeComponent, continuousComponent, repeaterComponent, projectileComponent);
        IHasQuestRequirement.addAltModeEvent(modeComponent);
    }

    @Inject(method = "onUseEvent", at = @At("HEAD"), remap = false, cancellable = true)
    private void useEvent(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        if (this.modeComponent.isMode(RankyakuMode.RAN)) {
            this.continuousComponent.triggerContinuity(entity, 60);
            ci.cancel();
        } else if (this.modeComponent.isMode(RankyakuMode.HAKURAI)) {
            this.projectileComponent.shoot(entity, 3.25F, 1.0F);
            this.cooldownComponent.startCooldown(entity, 320.0F);
            ci.cancel();
        }
    }

    @Unique
    private AbilityProjectileEntity createProjectile(LivingEntity entity) {
        switch (this.modeComponent.getCurrentMode()) {
            case RAN:
                return new RankyakuRanProjectile(entity.level, entity);
            case HAKURAI:
                return new HakuraiProjectile(entity.level, entity);
            default:
                return new RankyakuProjectile(entity.level, entity);
        }
    }

    @Unique
    private void startContinuous(LivingEntity livingEntity, IAbility iAbility) {
        this.repeaterComponent.start(livingEntity, 60, 1);
    }

    @Unique
    private void endContinuous(LivingEntity livingEntity, IAbility iAbility) {
        this.repeaterComponent.stop(livingEntity);
    }

    @Unique
    private void trigger(LivingEntity livingEntity, IAbility iAbility) {
        this.projectileComponent.shootWithSpread(livingEntity, 3.25f, 1, 2);
    }

    @Unique
    private void stop(LivingEntity livingEntity, IAbility iAbility) {
        this.cooldownComponent.startCooldown(livingEntity, this.repeaterComponent.getTriggerCount() * 6 + 100);
    }
}
