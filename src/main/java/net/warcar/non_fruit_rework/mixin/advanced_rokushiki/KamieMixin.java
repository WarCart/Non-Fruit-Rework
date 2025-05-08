package net.warcar.non_fruit_rework.mixin.advanced_rokushiki;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.KamieMode;
import net.warcar.non_fruit_rework.entities.AfterimageEntity;
import net.warcar.non_fruit_rework.helpers.MiscHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;

@Mixin(KamieAbility.class)
public abstract class KamieMixin extends Ability {
    @Shadow @Final private ContinuousComponent continuousComponent;
    @Unique private final AltModeComponent<KamieMode> modeComponent = new AltModeComponent<>(this, KamieMode.class, KamieMode.SIMPLE);

    private KamieMixin(AbilityCore<? extends IAbility> core) {
        super(core);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void onConstruct(AbilityCore<KamieAbility> core, CallbackInfo ci) {
        IHasQuestRequirement.addAltModeEvent(modeComponent);
        this.addComponents(modeComponent);
    }

    @Inject(method = "onUseEvent", at = @At("HEAD"), remap = false, cancellable = true)
    private void onUse(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        if (modeComponent.isMode(KamieMode.ZANSHIN)) {
            ci.cancel();
            this.continuousComponent.triggerContinuity(entity);
        }
    }

    @Inject(method = "onDamageTakenEvent", at = @At("RETURN"), remap = false)
    private void onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage, CallbackInfoReturnable<Float> cir) {
        if (cir.getReturnValueF() == 0 && this.modeComponent.isMode(KamieMode.ZANSHIN) && damageSource.getEntity() instanceof LivingEntity) {
            this.continuousComponent.stopContinuity(entity);
            AfterimageEntity afterimageEntity = MiscHelper.spawnAfterimage(entity);
            LivingEntity source = (LivingEntity) damageSource.getEntity();
            Vector3d position = source.position().add(entity.getLookAngle());
            entity.teleportToWithTicket(position.x, position.y, position.z);
            if (source instanceof MobEntity) {
                ((MobEntity) source).setTarget(afterimageEntity);
            }
        }
    }

    @Inject(method = "onEndContinuityEvent", at = @At("HEAD"), remap = false, cancellable = true)
    private void onEndContinuity(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        if (modeComponent.isMode(KamieMode.ZANSHIN)) {
            ci.cancel();
            this.cooldownComponent.startCooldown(entity, 600);
        }
    }
}
