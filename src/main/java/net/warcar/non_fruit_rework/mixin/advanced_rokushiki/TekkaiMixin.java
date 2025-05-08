package net.warcar.non_fruit_rework.mixin.advanced_rokushiki;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.TekkaiMode;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.TekkaiGoQuest;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.*;
import xyz.pixelatedw.mineminenomi.api.damagesource.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.UUID;

@Mixin(TekkaiAbility.class)
public abstract class TekkaiMixin extends Ability {
    @Shadow @Final public static AbilityCore<TekkaiAbility> INSTANCE;
    @Shadow @Final private AnimationComponent animationComponent;
    @Shadow @Final private ContinuousComponent continuousComponent;
    @Unique private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
    @Unique private final AltModeComponent<TekkaiMode> modeComponent = new AltModeComponent<>(this, TekkaiMode.class, TekkaiMode.SIMPLE);
    @Unique private final DamageTakenComponent damageTakenComponent = new DamageTakenComponent(this, this::onDamageTaken, DamageTakenComponent.DamageState.ATTACK);

    @Unique
    private float onDamageTaken(LivingEntity entity, IAbility iAbility, DamageSource source, float v) {
        if (!source.isProjectile() && source.getEntity() instanceof LivingEntity && this.isContinuous() && this.modeComponent.isMode(TekkaiMode.UTSUGI)) {
            source.getEntity().hurt(new AbilityDamageSource("reflection", entity, INSTANCE).setUnavoidable(), v / 4);
            return v / 4 * 3;
        }
        return v;
    }

    private TekkaiMixin(AbilityCore<? extends IAbility> core) {
        super(core);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void onConstruct(AbilityCore<TekkaiAbility> core, CallbackInfo ci) {
        IHasQuestRequirement.addAltModeEvent(modeComponent);
        this.addComponents(statsComponent, modeComponent, damageTakenComponent);
    }

    @Inject(method = "onContinuityStart", at = @At("HEAD"), remap = false, cancellable = true)
    private void onContinuityStart(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        if (!QuestHelper.hasFinishedQuest(entity, TekkaiGoQuest.INSTANCE)) {
            ci.cancel();
        }
        this.statsComponent.clearAttributeModifiers();
        if (!this.modeComponent.isMode(TekkaiMode.TEKKAI_KENPO)) {
            this.statsComponent.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("7d355019-7ef9-4beb-bcba-8b2608a73380"), "Tekkai knockback resistance", 0.5F, AttributeModifier.Operation.ADDITION));
        }
        if (this.modeComponent.isMode(TekkaiMode.TEKKAI_GO)) {
            this.statsComponent.addAttributeModifier(ModAttributes.DAMAGE_REDUCTION.get(), new AttributeModifier("7b3a9108-6a36-11eb-9439-0242ac130002", 0.5, AttributeModifier.Operation.ADDITION));
        } else if (!this.modeComponent.isMode(TekkaiMode.UTSUGI)) {
            this.statsComponent.addAttributeModifier(ModAttributes.DAMAGE_REDUCTION.get(), new AttributeModifier("7b3a9108-6a36-11eb-9439-0242ac130002", 0.25, AttributeModifier.Operation.ADDITION));
        }
        this.statsComponent.applyModifiers(entity);
    }

    @Inject(method = "onContinuityTick", at = @At("HEAD"), remap = false, cancellable = true)
    private void onContinuityTick(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        ci.cancel();
        if (!this.modeComponent.isMode(TekkaiMode.TEKKAI_KENPO)) {
            entity.addEffect(new EffectInstance(ModEffects.MOVEMENT_BLOCKED.get(), 2, 0, false, false));
        }
    }

    @Inject(method = "onContinuityEnd", at = @At("HEAD"), remap = false, cancellable = true)
    private void onContinuityEnd(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        ci.cancel();
        this.animationComponent.stop(entity);
        this.statsComponent.removeModifiers(entity);
        if (modeComponent.isMode(TekkaiMode.SIMPLE)) {
            this.cooldownComponent.startCooldown(entity, this.continuousComponent.getContinueTime() + 60.0F);
        } else {
            this.cooldownComponent.startCooldown(entity, this.continuousComponent.getContinueTime() * 3 + 160.0F);
        }
    }
}
