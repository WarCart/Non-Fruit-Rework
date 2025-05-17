package net.warcar.non_fruit_rework.abilities.hybrid;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeMod;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.init.ModEntityAttributes;
import net.warcar.non_fruit_rework.init.ModRaces;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModValues;

import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class HybridPassiveBonusesAbility extends PassiveStatBonusAbility {
    public static final AbilityCore<HybridPassiveBonusesAbility> INSTANCE = new AbilityCore.Builder<>("Hybrid passive bonuses", AbilityCategory.RACIAL, AbilityType.PASSIVE, HybridPassiveBonusesAbility::new)
            .setUnlockCheck(HybridPassiveBonusesAbility::canUnlock).setHidden().build();

    public HybridPassiveBonusesAbility(AbilityCore<HybridPassiveBonusesAbility> core) {
        super(core);
        this.pushDynamicAttribute(ModEntityAttributes.SIZE, entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 10;
            return new AttributeModifier(UUID.fromString("4b60ab34-0a3e-4c67-8166-84ed65c70d0e"), "Hybrid Height modifier", giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(Attributes.ATTACK_DAMAGE, entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 15;
            double fishmanGen = genome.getOrDefault(ModValues.FISHMAN, 0f) * 2;
            return new AttributeModifier(UUID.fromString("4b61ab34-0b3e-4c67-81f6-84ed65c70d0e"), "Hybrid Damage modifier", giantMod + fishmanGen, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(ModAttributes.JUMP_HEIGHT.get(), entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double minkMod = genome.getOrDefault(ModValues.MINK, 0f) * 0.75;
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 2;
            return new AttributeModifier(UUID.fromString("4b61ac34-0d3e-4c67-81f6-84edf5c75d0e"), "Hybrid Jump modifier", minkMod + giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(ForgeMod.SWIM_SPEED.get(), entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double fishmanMod = genome.getOrDefault(ModValues.FISHMAN, 0f) * 3;
            return new AttributeModifier(UUID.fromString("4b31ab34-0bce-4c57-8136-84e565c70dfe"), "Hybrid Jump modifier", fishmanMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(Attributes.MOVEMENT_SPEED, entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double minkMod = genome.getOrDefault(ModValues.MINK, 0f) * 0.25;
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f);
            return new AttributeModifier(UUID.fromString("4b61ab34-0b3e-4c67-81f6-84ed65c70d0e"), "Hybrid Speed modifier", minkMod + giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(ModAttributes.FALL_RESISTANCE.get(), entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double minkMod = genome.getOrDefault(ModValues.MINK, 0f) * 1.5;
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 10;
            return new AttributeModifier(UUID.fromString("4b61ac34-0d3e-4c62-81f6-84edf5c75d0e"), "Hybrid Jump Resistance modifier", minkMod + giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(Attributes.ARMOR, entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 5;
            return new AttributeModifier(UUID.fromString("4b61ab34-0b3e-4c67-81f6-84ed65f71d0e"), "Hybrid Armor modifier", giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(ModAttributes.ATTACK_RANGE.get(), entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 25;
            return new AttributeModifier(UUID.fromString("4064ac34-0d3a-4c67-81f6-84edf5c75d0e"), "Hybrid Reach modifier", giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(ForgeMod.REACH_DISTANCE.get(), entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 25;
            return new AttributeModifier(UUID.fromString("4064ac34-0d3a-4c67-81f6-84edf5c75d0e"), "Hybrid Reach modifier", giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(ModAttributes.STEP_HEIGHT.get(), entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 1.5;
            return new AttributeModifier(UUID.fromString("4044fc34-0dda-4c67-81f6-84edf5a75d0e"), "Hybrid Step Height modifier", giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(Attributes.KNOCKBACK_RESISTANCE, entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f);
            return new AttributeModifier(UUID.fromString("4044fc34-0dda-4c67-81f6-84edf5a75d0e"), "Hybrid Knockback Resistance modifier", giantMod, AttributeModifier.Operation.ADDITION);
        });
        this.pushDynamicAttribute(ModAttributes.TOUGHNESS.get(), entity -> {
            Map<ResourceLocation, Float> genome = NonFruitDataCapability.get(entity).getGenome();
            double giantMod = genome.getOrDefault(ModRaces.GIANT.getId(), 0f) * 4;
            return new AttributeModifier(UUID.fromString("4044fa34-0ddf-4c67-8106-84cdf5a70d0e"), "Hybrid Toughness modifier", giantMod, AttributeModifier.Operation.ADDITION);
        });
    }

    @Override
    public Predicate<LivingEntity> getCheck() {
        return entity -> true;
    }

    private static boolean canUnlock(LivingEntity entity) {
        return EntityStatsCapability.get(entity).getRace().equals(ModRaces.HYBRID.getId());
    }
}
