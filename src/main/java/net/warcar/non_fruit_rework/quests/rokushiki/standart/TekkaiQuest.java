package net.warcar.non_fruit_rework.quests.rokushiki.standart;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.quests.objectives.TakeDamageObjective;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.quests.objectives.ObtainItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

public class TekkaiQuest extends Quest {
    public static final QuestId<TekkaiQuest> INSTANCE = new QuestId.Builder<>("Trial: Tekkai", TekkaiQuest::new).build();
    public static final QuestId<TekkaiQuest> DIFFICULT = new QuestId.Builder<>("Trial: Tekkai", TekkaiQuest::difficult).build();

    public TekkaiQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 525);
        this.addObjective(objective1);
        TakeDamageObjective objective2 = (TakeDamageObjective) new TakeDamageObjective("Take %s Damage", 100).addRequirement(objective1);
        this.addObjective(objective2);
        ObtainItemObjective<Item> objective3 = new ObtainItemObjective<>("Collect %s turtle scutes", 2, () -> Items.SCUTE);
        objective3.addRequirement(objective2);
        this.addObjective(objective3);
        this.addObjective(new ObtainItemObjective<>("Obtain Protection II Armor Piece", 1, (itemStack) -> EnchantmentHelper.getItemEnchantmentLevel(Enchantments.ALL_DAMAGE_PROTECTION, itemStack) > 1 && itemStack.getItem() instanceof ArmorItem).addRequirement(objective3));
        this.onStartEvent = this::giveReward;
    }

    public static TekkaiQuest difficult(QuestId core) {
        TekkaiQuest quest = new TekkaiQuest(core);
        quest.getObjectives().clear();
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 630);
        quest.addObjective(objective1);
        TakeDamageObjective objective2 = (TakeDamageObjective) new TakeDamageObjective("Take %s Damage", 120).addRequirement(objective1);
        quest.addObjective(objective2);
        ObtainItemObjective<Item> objective3 = new ObtainItemObjective<>("Collect %s turtle scutes", 3, () -> Items.SCUTE);
        objective3.addRequirement(objective2);
        quest.addObjective(objective3);
        quest.addObjective(new ObtainItemObjective<>("Obtain Protection III Armor Piece", 1, (itemStack) -> EnchantmentHelper.getItemEnchantmentLevel(Enchantments.ALL_DAMAGE_PROTECTION, itemStack) > 2 && itemStack.getItem() instanceof ArmorItem).addRequirement(objective3));
        return quest;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(TekkaiAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
