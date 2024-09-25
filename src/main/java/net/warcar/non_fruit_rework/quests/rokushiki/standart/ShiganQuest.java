package net.warcar.non_fruit_rework.quests.rokushiki.standart;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class ShiganQuest extends Quest {
    public static final QuestId<ShiganQuest> INSTANCE = new QuestId.Builder<>("Trial: Shigan", ShiganQuest::new).build();
    public static final QuestId<ShiganQuest> DIFFICULT = new QuestId.Builder<>("Trial: Shigan difficult", ShiganQuest::difficult).build();

    public ShiganQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 550);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s enemies using your fists", 100, SharedKillChecks.HAS_EMPTY_HAND).addRequirement(objective));
        this.onCompleteEvent = this::giveReward;
    }

    public static ShiganQuest difficult(QuestId core) {
        ShiganQuest quest = new ShiganQuest(core);
        quest.getObjectives().clear();
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 660);
        quest.addObjective(objective);
        quest.addObjective(new KillEntityObjective("Kill %s enemies using your fists", 120, SharedKillChecks.HAS_EMPTY_HAND).addRequirement(objective));
        return quest;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(ShiganAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
