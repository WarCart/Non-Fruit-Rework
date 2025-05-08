package net.warcar.non_fruit_rework.mixin.advanced_rokushiki;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.SoruMode;
import net.warcar.non_fruit_rework.helpers.MiscHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;

@Mixin(SoruAbility.class)
public abstract class SoruMixin extends Ability {
    @Shadow
    @Final
    private StackComponent stackComponent = new StackComponent(this, 5) {
        @Override
        public void addStacks(LivingEntity entity, IAbility ability, int stacks) {
            if (!MiscHelper.isBerserk(entity)) {
                super.addStacks(entity, ability, stacks);
            } else {
                this.getAbility().getComponent(ModAbilityKeys.COOLDOWN).ifPresent(cooldownComponent1 -> cooldownComponent1.startCooldown(entity, 10));
            }
        }
    }.addStackChangeEvent(this::onStacksChange);

    @Unique private final AltModeComponent<SoruMode> modeComponent = new AltModeComponent<>(this, SoruMode.class, SoruMode.SIMPLE);
    @Unique private final RangeComponent rangeComponent = new RangeComponent(this);
    @Unique private final ContinuousComponent continuousComponent = new ContinuousComponent(this).addTickEvent(this::onTick).addEndEvent(this::endContinuous);

    @Shadow protected abstract void onStacksChange(LivingEntity entity, IAbility ability, int stacks);

    private SoruMixin(AbilityCore<? extends IAbility> core) {
        super(core);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void onInit(CallbackInfo ci) {
        this.addComponents(modeComponent, rangeComponent, continuousComponent);
        IHasQuestRequirement.addAltModeEvent(modeComponent);
    }

    @Inject(method = "onUseEvent", at = @At("HEAD"), remap = false, cancellable = true)
    private void onUseEvent(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        if (modeComponent.isMode(SoruMode.TEKKAI_DAMA)) {
            Vector3d look = entity.getLookAngle().multiply(3, 0, 3).add(0, 0.1, 0);
            if (entity.zza < 0.0F) {
                look = look.multiply(-1.0F, 1.0F, -1.0F);
            }

            if (entity.isInWater()) {
                look = look.multiply(0.2, 0.2, 0.2);
            }

            AbilityHelper.setDeltaMovement(entity, look);
            this.continuousComponent.startContinuity(entity, 10);
            this.stackComponent.revertStacksToDefault(entity, this);
            TekkaiAbility tekkai = AbilityDataCapability.get(entity).getEquippedAbility(TekkaiAbility.INSTANCE);
            if (tekkai != null) {
                tekkai.getComponent(ModAbilityKeys.COOLDOWN).ifPresent(comp -> comp.startCooldown(entity, 300));
            }
            ci.cancel();
        } else {
            MiscHelper.spawnAfterimage(entity);
        }
    }

    @Unique
    private void onTick(LivingEntity entity, IAbility ability) {
        if (this.modeComponent.isMode(SoruMode.TEKKAI_DAMA)) {
            double speed = entity.getDeltaMovement().length();
            for (LivingEntity target : rangeComponent.getTargetsInArea(entity, 1.8f)) {
                target.hurt(ModDamageSource.causeAbilityDamage(entity, this), (float) speed * 10);
            }
        }
    }

    @Unique
    private void endContinuous(LivingEntity livingEntity, IAbility iAbility) {
        cooldownComponent.startCooldown(livingEntity, 300);
    }
}
