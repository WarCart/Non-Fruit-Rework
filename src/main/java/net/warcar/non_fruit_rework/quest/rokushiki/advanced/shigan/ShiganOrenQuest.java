package net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.rokushiki.ShiganQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class ShiganOrenQuest extends Quest implements IHasRequirements {
    public static final QuestId<ShiganOrenQuest> INSTANCE = new QuestId.Builder<>("Trial: Shigan Oren", ShiganOrenQuest::new)
            .addRequirements(ShiganQuest.INSTANCE).build();

    public ShiganOrenQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 2500);
        this.addObjective(objective);
        Objective objective1 = new KillEntityObjective("Kill %s enemies using your fists", 75, SharedKillChecks.HAS_EMPTY_HAND).addRequirement(objective);
        this.addObjective(objective1);
        this.addObjective(new KillEntityObjective("Kill %s enemies using Shigan", 25, SharedKillChecks.checkAbilitySource(ShiganAbility.INSTANCE)).addRequirements(objective, objective1));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return QuestHelper.canUseAdvancedRokushiki(player);
    }
}