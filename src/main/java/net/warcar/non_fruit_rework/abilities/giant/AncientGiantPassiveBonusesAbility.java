package net.warcar.non_fruit_rework.abilities.giant;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.init.ModEntityAttributes;
import net.warcar.non_fruit_rework.init.ModRaces;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

import java.util.UUID;
import java.util.function.Predicate;

public class AncientGiantPassiveBonusesAbility extends PassiveStatBonusAbility {
    public static final AbilityCore<AncientGiantPassiveBonusesAbility> INSTANCE = new AbilityCore.Builder<>("Ancient Giant passive bonuses", AbilityCategory.RACIAL, AbilityType.PASSIVE, AncientGiantPassiveBonusesAbility::new)
            .setHidden().setUnlockCheck(AncientGiantPassiveBonusesAbility::canUnlock).build();

    public AncientGiantPassiveBonusesAbility(AbilityCore<AncientGiantPassiveBonusesAbility> core) {
        super(core);
        this.pushStaticAttribute(ModEntityAttributes.SIZE, new AttributeModifier(UUID.fromString("4b60ab34-0a3e-4c67-8166-84ed65c70d0e"), "Ancient Giant Height modifier", 11, AttributeModifier.Operation.ADDITION));
        this.pushStaticAttribute(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("4b61ab34-0b3e-4c67-81f6-84ed65c70d0e"), "Ancient Giant Damage modifier", 45, AttributeModifier.Operation.ADDITION));
        this.pushStaticAttribute(ModAttributes.JUMP_HEIGHT.get(), new AttributeModifier(UUID.fromString("4b61ac34-0d3e-4c67-81f6-84edf5c75d0e"), "Ancient Giant Jump modifier", 6, AttributeModifier.Operation.ADDITION));
        this.pushStaticAttribute(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("4b61ab34-0b3e-4c67-81f6-84ed65c70d0e"), "Ancient Giant Speed modifier", 3, AttributeModifier.Operation.MULTIPLY_BASE));
        this.pushStaticAttribute(ModAttributes.FALL_RESISTANCE.get(), new AttributeModifier(UUID.fromString("4b61ac34-0d3e-4c62-81f6-84edf5c75d0e"), "Ancient Giant Jump Resistance modifier", 30, AttributeModifier.Operation.ADDITION));
        this.pushStaticAttribute(Attributes.ARMOR, new AttributeModifier(UUID.fromString("4b61ab34-0b3e-4c67-81f6-84ed65f71d0e"), "Ancient Giant Armor modifier", 15, AttributeModifier.Operation.ADDITION));
        AttributeModifier reachModifier = new AttributeModifier(UUID.fromString("4064ac34-0d3a-4c67-81f6-84edf5c75d0e"), "Ancient Giant Reach modifier", 15, AttributeModifier.Operation.ADDITION);
        this.pushStaticAttribute(ModAttributes.ATTACK_RANGE.get(), reachModifier);
        this.pushStaticAttribute(ForgeMod.REACH_DISTANCE.get(), reachModifier);
        this.pushStaticAttribute(ModAttributes.STEP_HEIGHT.get(), new AttributeModifier(UUID.fromString("4044fc34-0dda-4c67-81f6-84edf5a75d0e"), "Ancient Giant Step Height modifier", 4.5, AttributeModifier.Operation.ADDITION));
        this.pushStaticAttribute(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("4044fc34-0dda-4c67-81f6-84edf5a75d0e"), "Ancient Giant Knockback Resistance modifier", 3, AttributeModifier.Operation.ADDITION));
        this.pushStaticAttribute(ModAttributes.TOUGHNESS.get(), new AttributeModifier(UUID.fromString("4044fa34-0ddf-4c67-8106-84cdf5a70d0e"), "Ancient Giant Toughness modifier", 8, AttributeModifier.Operation.ADDITION));
    }

    private static boolean canUnlock(LivingEntity entity) {
        return QuestHelper.isTrueRace(entity, ModRaces.ANCIENT_GIANT.getId());
    }

    @Override
    public Predicate<LivingEntity> getCheck() {
        return entity -> true;
    }
}
