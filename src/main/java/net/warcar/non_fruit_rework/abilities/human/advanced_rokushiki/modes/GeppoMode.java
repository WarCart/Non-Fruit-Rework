package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.geppo.KamisoriQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum GeppoMode implements IHasQuestRequirement {
    SIMPLE(null),
    KAMISORI(KamisoriQuest.INSTANCE);

    private final QuestId<?> questId;
    GeppoMode(QuestId questId) {
        this.questId = questId;
    }

    @Override
    public QuestId<?> getQuest() {
        return this.questId;
    }
}
