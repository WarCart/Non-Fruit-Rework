package net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.CustomObtainItemObjective;
import net.warcar.non_fruit_rework.quest.objectives.TakeDamageObjective;
import net.warcar.non_fruit_rework.quest.rokushiki.TekkaiQuest;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.ObtainItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

public class TekkaiGoQuest extends Quest implements IHasRequirements {
    public static final QuestId<TekkaiGoQuest> INSTANCE = new QuestId.Builder<>("Trial: Tekkai Go", TekkaiGoQuest::new)
            .addRequirements(TekkaiQuest.INSTANCE).build();

    private final CustomObtainItemObjective<Item> collectObjective;

    public TekkaiGoQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 1200);
        this.addObjective(objective1);
        TakeDamageObjective objective2 = (TakeDamageObjective) new TakeDamageObjective("Take %s Damage", 400).addRequirement(objective1);
        this.addObjective(objective2);
        collectObjective = new CustomObtainItemObjective<>(5, () -> Items.SCUTE);
        collectObjective.addRequirement(objective2);
        this.addObjective(collectObjective);
        this.addObjective(new ObtainItemObjective<>("Obtain Protection IV Armor Piece", 1, (itemStack) -> EnchantmentHelper.getItemEnchantmentLevel(Enchantments.ALL_DAMAGE_PROTECTION, itemStack) > 3 && itemStack.getItem() instanceof ArmorItem).addRequirement(collectObjective));
        this.onCompleteEvent = this::giveReward;
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return EntityHelper.canUseAdvancedRokushiki(player);
    }

    public boolean giveReward(PlayerEntity player) {
        return this.removeQuestItem(player, collectObjective.getItemTarget().get(), collectObjective.getItemsNeeded());
    }
}
