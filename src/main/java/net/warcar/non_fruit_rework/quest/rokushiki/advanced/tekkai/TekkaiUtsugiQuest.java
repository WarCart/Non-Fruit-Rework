package net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.TakeDamageObjective;
import net.warcar.non_fruit_rework.quest.rokushiki.TekkaiQuest;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.ObtainItemObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

public class TekkaiUtsugiQuest extends Quest implements IHasRequirements {
    public static final QuestId<TekkaiUtsugiQuest> INSTANCE = new QuestId.Builder<>("Trial: Tekkai Utsugi", TekkaiUtsugiQuest::new)
            .addRequirements(TekkaiQuest.INSTANCE).build();


    public TekkaiUtsugiQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 1500);
        this.addObjective(objective1);
        TakeDamageObjective objective2 = (TakeDamageObjective) new TakeDamageObjective("Take %s Damage", 200).addRequirement(objective1);
        this.addObjective(objective2);
        this.addObjective(new ObtainItemObjective<>("Obtain Thorns Armor Piece", 1, (itemStack) -> EnchantmentHelper.getItemEnchantmentLevel(Enchantments.THORNS, itemStack) > 0 && itemStack.getItem() instanceof ArmorItem).addRequirement(objective1));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return EntityHelper.canUseAdvancedRokushiki(player);
    }
}
