package net.warcar.non_fruit_rework.abilities.fishman;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModResources;

import java.util.UUID;
import java.util.function.Predicate;

public class FishmanPowerAbility extends PassiveStatBonusAbility {
    public static final AbilityCore<FishmanPowerAbility> INSTANCE = new AbilityCore.Builder<>("Fishman power", AbilityCategory.RACIAL, AbilityType.PASSIVE, FishmanPowerAbility::new).setUnlockCheck(FishmanPowerAbility::canUnlock)
            .setIcon(ModResources.PERK_ICON).addDescriptionLine(ChangeStatsComponent.getTooltip()).setHidden().build();

    public FishmanPowerAbility(AbilityCore<?> core) {
        super(core);
        this.pushDynamicAttribute(Attributes.ATTACK_DAMAGE, entity -> new AttributeModifier(UUID.fromString("c022147e-68a3-49ab-93a6-a27d6aa721d2"), "Fishman Damage Boost", getDamageBoost(entity), AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    private static float getDamageBoost(LivingEntity entity) {
        return 2 + NonFruitDataCapability.get(entity).getEnergySteroidLevel();
    }

    @Override
    public Predicate<LivingEntity> getCheck() {
        return FishmanPowerAbility::canUnlock;
    }

    private static boolean canUnlock(LivingEntity entity) {
        return EntityStatsCapability.get(entity).isFishman();
    }
}
