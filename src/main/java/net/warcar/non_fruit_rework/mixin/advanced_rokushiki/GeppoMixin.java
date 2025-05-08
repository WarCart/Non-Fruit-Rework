package net.warcar.non_fruit_rework.mixin.advanced_rokushiki;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.GeppoMode;
import net.warcar.non_fruit_rework.helpers.MiscHelper;
import net.warcar.non_fruit_rework.init.ModParticles;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

@Mixin(GeppoAbility.class)
public abstract class GeppoMixin extends Ability {
    @Shadow
    @Final
    private StackComponent stackComponent = new StackComponent(this) {
        @Override
        public void addStacks(LivingEntity entity, IAbility ability, int stacks) {
            if (!MiscHelper.isBerserk(entity)) {
                super.addStacks(entity, ability, stacks);
            }
        }
    };

    @Shadow private boolean hasFallDamage;

    @Shadow protected abstract float getCooldownTicks();

    @Shadow protected abstract int getMaxJumps(LivingEntity entity);

    private GeppoMixin(AbilityCore<? extends IAbility> core) {
        super(core);
    }

    private final AltModeComponent<GeppoMode> modeComponent = new AltModeComponent<>(this, GeppoMode.class, GeppoMode.SIMPLE);

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void onInit(CallbackInfo ci) {
        IHasQuestRequirement.addAltModeEvent(modeComponent);
        this.addComponents(modeComponent);
    }

    @Inject(method = "onUseEvent", at = @At("HEAD"), remap = false, cancellable = true)
    private void onUse(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        if (modeComponent.isMode(GeppoMode.KAMISORI)) {
            ci.cancel();
            this.hasFallDamage = false;
            AbilityHelper.setDeltaMovement(entity, entity.getLookAngle().scale(3));
            entity.level.playSound(null, entity.blockPosition(), ModSounds.GEPPO_SFX.get(), SoundCategory.PLAYERS,
                    2.0F, 0.75F + this.random.nextFloat() / 3.0F);
            MiscHelper.spawnAfterimage(entity);
            entity.addEffect(new EffectInstance(ModEffects.VANISH.get(), 5, 0, false, false));
            entity.level.playSound(null, entity.blockPosition(), ModSounds.TELEPORT_SFX.get(), SoundCategory.PLAYERS,
                    2.0F, 1.0F);
            WyHelper.spawnParticleEffect(ModParticles.ADVANCED_GEPPO, entity, entity.getX(), entity.getY(), entity.getZ());
            this.stackComponent.addStacks(entity, this, -1);
            if (this.stackComponent.getStacks() <= 0) {
                super.cooldownComponent.startCooldown(entity, this.getCooldownTicks());
                this.stackComponent.setStacks(entity, this, this.getMaxJumps(entity));
            } else {
                super.cooldownComponent.startCooldown(entity, 10.0F);
            }
        }
    }
}
