package net.warcar.non_fruit_rework.quests.rokushiki.standart;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RokuoganAbility;
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

public class RokuoganQuest extends Quest {
    public static final QuestId<RokuoganQuest> INSTANCE = new QuestId.Builder<>("Trial: Rokuogan", RokuoganQuest::new)
            .addRequirements(SoruQuest.INSTANCE, TekkaiQuest.INSTANCE, GeppoQuest.INSTANCE, KamieQuest.INSTANCE, ShiganQuest.INSTANCE, RankyakuQuest.INSTANCE).build();


    public static final QuestId<RokuoganQuest> DIFFICULT = new QuestId.Builder<>("Trial: Rokuogan difficult", RokuoganQuest::difficult)
            .build();

    public RokuoganQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 3500);
        this.addObjective(objective);
        Objective objective1 = new KillEntityObjective("Kill %s enemies using Shigan", 50, SharedKillChecks.checkAbilitySource(ShiganAbility.INSTANCE)).addRequirement(objective);
        this.addObjective(objective1);
        Objective objective2 = new KillEntityObjective("Kill %s enemies using Rankyaku", 35, SharedKillChecks.checkAbilitySource(RankyakuAbility.INSTANCE)).addRequirement(objective);
        this.addObjective(objective2);
        this.addObjective(new KillEntityObjective("Kill %s enemies", 350).addRequirements(objective1, objective2, objective));
        this.onCompleteEvent = this::giveReward;
    }

    public static RokuoganQuest difficult(QuestId core) {
        RokuoganQuest quest = new RokuoganQuest(core);
        quest.getObjectives().clear();
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 4000);
        quest.addObjective(objective);
        Objective objective1 = new KillEntityObjective("Kill %s enemies using Shigan", 60, SharedKillChecks.checkAbilitySource(ShiganAbility.INSTANCE)).addRequirement(objective);
        quest.addObjective(objective1);
        Objective objective2 = new KillEntityObjective("Kill %s enemies using Rankyaku", 40, SharedKillChecks.checkAbilitySource(RankyakuAbility.INSTANCE)).addRequirement(objective);
        quest.addObjective(objective2);
        quest.addObjective(new KillEntityObjective("Kill %s enemies", 400).addRequirements(objective1, objective2, objective));
        return quest;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(RokuoganAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
