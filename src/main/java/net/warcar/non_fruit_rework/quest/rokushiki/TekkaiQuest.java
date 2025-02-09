package net.warcar.non_fruit_rework.quest.rokushiki;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import net.warcar.non_fruit_rework.quest.objectives.TakeDamageObjective;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.ObtainItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class TekkaiQuest extends Quest {
    public static final QuestId<TekkaiQuest> INSTANCE = new QuestId.Builder<>("Trial: Tekkai", TekkaiQuest::new).build();
    private final CustomObtainItemObjective<Item> collectObjective;

    public TekkaiQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 525);
        this.addObjective(objective1);
        TakeDamageObjective objective2 = (TakeDamageObjective) new TakeDamageObjective("Take %s Damage", 100).addRequirement(objective1);
        this.addObjective(objective2);
        collectObjective = new CustomObtainItemObjective<>(2, () -> Items.SCUTE);
        collectObjective.addRequirement(objective2);
        this.addObjective(collectObjective);
        this.addObjective(new ObtainItemObjective<>("Obtain Protection II Armor Piece", 1, (itemStack) -> EnchantmentHelper.getItemEnchantmentLevel(Enchantments.ALL_DAMAGE_PROTECTION, itemStack) > 1 && itemStack.getItem() instanceof ArmorItem).addRequirement(collectObjective));
        this.onCompleteEvent = this::giveReward;
    }

    public boolean giveReward(PlayerEntity player) {
        if (!this.removeQuestItem(player, collectObjective.getItemTarget().get(), collectObjective.getItemsNeeded())) {
            return false;
        }
        IAbilityData props = AbilityDataCapability.get(player);

        props.addUnlockedAbility(TekkaiAbility.INSTANCE, AbilityUnlock.PROGRESSION);

        WyNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player.getId(), props), player);
        return true;
    }
}