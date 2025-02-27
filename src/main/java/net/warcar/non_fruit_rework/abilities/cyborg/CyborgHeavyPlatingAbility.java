package net.warcar.non_fruit_rework.abilities.cyborg;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.cyborg.HeavyArmorQuest;
import xyz.pixelatedw.mineminenomi.api.abilities.*;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModResources;

import java.util.UUID;
import java.util.function.Predicate;

public class CyborgHeavyPlatingAbility  extends PassiveStatBonusAbility {
    private static final AttributeModifier CYBORG_ARMOR;
    private static final AttributeModifier CYBORG_ARMOR_TOUGHNESS;
    private static final AttributeModifier CYBORG_DAMAGE;
    public static final AbilityCore<CyborgHeavyPlatingAbility> INSTANCE = new AbilityCore.Builder<>("Cyborg Heavy Armor Passive Bonuses", AbilityCategory.RACIAL, AbilityType.PASSIVE, CyborgHeavyPlatingAbility::new)
            .setIcon(ModResources.PERK_ICON).addDescriptionLine(ChangeStatsComponent.getTooltip()).setUnlockCheck(CyborgHeavyPlatingAbility::canUnlock).build();

    public CyborgHeavyPlatingAbility(AbilityCore<CyborgHeavyPlatingAbility> core) {
        super(core);
        this.pushStaticAttribute(Attributes.ARMOR, CYBORG_ARMOR);
        this.pushStaticAttribute(Attributes.ARMOR_TOUGHNESS, CYBORG_ARMOR_TOUGHNESS);
        this.pushStaticAttribute(ModAttributes.PUNCH_DAMAGE.get(), CYBORG_DAMAGE);
    }

    public Predicate<LivingEntity> getCheck() {
        return CyborgHeavyPlatingAbility::canUnlock;
    }

    private static boolean canUnlock(LivingEntity entity) {
        return QuestHelper.hasFinishedQuest(entity, HeavyArmorQuest.INSTANCE) && EntityStatsCapability.get(entity).isCyborg();
    }

    static {
        CYBORG_ARMOR = new AttributeModifier(UUID.fromString("01344b52-e45e-44a3-9895-6fca1c10fc2a"), "Cyborg Heavy Armor Bonus", 10.0, AttributeModifier.Operation.ADDITION);
        CYBORG_ARMOR_TOUGHNESS = new AttributeModifier(UUID.fromString("12443845-6f63-4916-b57e-a6805cfa47ae"), "Cyborg Heavy Armor Toughness Bonus", 6.0, AttributeModifier.Operation.ADDITION);
        CYBORG_DAMAGE = new AttributeModifier(UUID.fromString("81e15243-46e7-4b03-bf0d-f5d8afa870df"), "Cyborg Heavy Damage Bonus", 2.0, AttributeModifier.Operation.ADDITION);
    }
}
