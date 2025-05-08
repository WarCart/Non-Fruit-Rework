package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.RankyakuHakuraiQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku.RankyakuRanQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum RankyakuMode implements IHasQuestRequirement {
    SIMPLE(null),
    HAKURAI(RankyakuHakuraiQuest.INSTANCE),
    RAN(RankyakuRanQuest.INSTANCE),
    ;

    private final QuestId<?> questId;

    RankyakuMode(QuestId<?> questId) {
        this.questId = questId;
    }

    @Override
    public QuestId<?> getQuest() {
        return questId;
    }
}
