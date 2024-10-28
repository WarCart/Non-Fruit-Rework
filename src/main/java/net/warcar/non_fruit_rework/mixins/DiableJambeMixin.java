package net.warcar.non_fruit_rework.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.warcar.non_fruit_rework.abilities.black_leg.IDiableJambeMixin;
import net.warcar.non_fruit_rework.data.entity.germa_genes.GeneDataCapability;
import net.warcar.non_fruit_rework.init.ReworkedParticleEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.DiableJambeAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiEmissionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.UUID;

@Mixin(DiableJambeAbility.class)
public class DiableJambeMixin implements IDiableJambeMixin {
    @Shadow @Final private ContinuousComponent continuousComponent;

    @Shadow private boolean frozen;

    @Shadow @Final private ChangeStatsComponent statsComponent;
    @Shadow @Final public static AbilityCore<DiableJambeAbility> INSTANCE;
    private boolean ifrit = false;

    private static boolean stiffnessReady(LivingEntity entity) {
        AbilityCore<? extends Ability>[] hakies = new AbilityCore[] {BusoshokuHakiEmissionAbility.INSTANCE, BusoshokuHakiInternalDestructionAbility.INSTANCE, HaoshokuHakiInfusionAbility.INSTANCE};
        boolean hasAdvancedHaki = false;
        for (AbilityCore<? extends Ability> haki : hakies) {
            Ability ability = AbilityDataCapability.get(entity).getEquippedAbility(haki);
            hasAdvancedHaki |= ability != null && ability.isContinuous();
        }
        return (HakiHelper.hasHardeningActive(entity) && GeneDataCapability.get(entity).isAwakenGenes()) || hasAdvancedHaki;
    }

    private void onCooldownStart(LivingEntity entity, IAbility ability) {
        this.ifrit = false;
        ((DiableJambeAbility) ability).setDisplayIcon((ResourceLocation) null);
        ((DiableJambeAbility) ability).setDisplayName((ITextComponent) null);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void addComponents(AbilityCore<DiableJambeAbility> core, CallbackInfo ci) {
        ((DiableJambeAbility) (Object) this).getComponent(ModAbilityKeys.COOLDOWN).ifPresent(cooldownComponent -> cooldownComponent.addStartEvent(this::onCooldownStart));
    }

    @Inject(method = "onContinuityTickEvent", at = @At("HEAD"), remap = false, cancellable = true)
    private void reTick(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        ci.cancel();
        if (entity.hasEffect(ModEffects.FROZEN.get()) && this.ifrit) {
            this.frozen = true;
            entity.removeEffect(ModEffects.FROZEN.get());
            this.continuousComponent.stopContinuity(entity);
        } else if (entity.hasEffect(ModEffects.FROZEN.get())) {
            this.frozen = true;
            AbilityHelper.reduceEffect(entity.getEffect(ModEffects.FROZEN.get()), 1.100000023841858D);
            this.continuousComponent.stopContinuity(entity);
        } else if (entity.hasEffect(ModEffects.FROSTBITE.get()) && this.ifrit) {
            this.frozen = true;
            AbilityHelper.reduceEffect(entity.getEffect(ModEffects.FROSTBITE.get()), 4.5D);
            this.continuousComponent.stopContinuity(entity);
        } else if (entity.hasEffect(ModEffects.FROSTBITE.get())) {
            this.frozen = true;
            AbilityHelper.reduceEffect(entity.getEffect(ModEffects.FROSTBITE.get()), 1.5D);
            this.continuousComponent.stopContinuity(entity);
        } else if (this.ifrit && !stiffnessReady(entity)) {
            entity.setSecondsOnFire(10);
            this.continuousComponent.stopContinuity(entity);
            entity.hurt(DamageSource.ON_FIRE, 5);
        } else if (this.ifrit) {
            WyHelper.spawnParticleEffect(ReworkedParticleEffects.IFRIT_JAMBE.get(), entity, entity.getX(), entity.getY(), entity.getZ());
        } else {
            WyHelper.spawnParticleEffect(ModParticleEffects.DIABLE_JAMBE.get(), entity, entity.getX(), entity.getY(), entity.getZ());
        }
    }

    @Inject(method = "onUseEvent", at = @At("HEAD"), remap = false, cancellable = true)
    private void reUse(LivingEntity entity, IAbility ability, CallbackInfo ci) {
        ci.cancel();
        double time = EntityStatsCapability.get(entity).getDoriki() / 250;
        if (entity.isSteppingCarefully() && stiffnessReady(entity)) {
            this.ifrit = true;
            time /= 2;
            ((DiableJambeAbility) ability).setCustomIcon("Ifrit Jambe");
            ((DiableJambeAbility) ability).setDisplayName(new StringTextComponent("Ifrit Jambe"));
        }
        this.statsComponent.clearAttributeModifiers();
        this.statsComponent.addAttributeModifier(Attributes.ATTACK_SPEED, new AbilityAttributeModifier(UUID.fromString("a984413a-7459-4989-8362-7c46a2663f0e"), INSTANCE, "Diable Jambe Speed Multiplier", this.ifrit ? 1.2 : 0.4, AttributeModifier.Operation.ADDITION));
        this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, new AbilityAttributeModifier(UUID.fromString("e3ae074c-40a9-49ff-aa3b-7cc9b98ddc2d"), INSTANCE, "Diable Jambe Attack Multiplier", this.ifrit ? 120 : 4, AttributeModifier.Operation.ADDITION));
        this.statsComponent.applyModifiers(entity);
        this.continuousComponent.triggerContinuity(entity, (float) time * 20);
    }

    public boolean isIfrit() {
        return this.ifrit;
    }
}
