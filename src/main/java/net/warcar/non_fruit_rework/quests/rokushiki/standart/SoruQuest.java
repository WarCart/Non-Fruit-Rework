package net.warcar.non_fruit_rework.quests.rokushiki.standart;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.TimedKillEntityObjective;

public class SoruQuest extends Quest {
    public static final QuestId<SoruQuest> INSTANCE = new QuestId.Builder<>("Trial: Soru", SoruQuest::new).build();
    public static final QuestId<SoruQuest> DIFFICULT = new QuestId.Builder<>("Trial: Soru", SoruQuest::difficult).build();

    public SoruQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 500);
        this.addObjective(objective1);
        this.addObjective(new TimedKillEntityObjective("Enemies", 3, 5).addRequirement(objective1));
        this.onStartEvent = this::giveReward;
    }

    public static SoruQuest difficult(QuestId core) {
        SoruQuest quest = new SoruQuest(core);
        quest.getObjectives().clear();
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 600);
        quest.addObjective(objective1);
        quest.addObjective(new TimedKillEntityObjective("Enemies", 4, 6).addRequirement(objective1));
        return quest;
    }

    public boolean giveReward(PlayerEntity player) {
        IAbilityData props = AbilityDataCapability.get(player);
        props.addUnlockedAbility(SoruAbility.INSTANCE, AbilityUnlock.PROGRESSION);
        return true;
    }
}
