package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.kamie.ZanshinQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum KamieMode implements IHasQuestRequirement {
    SIMPLE(null),
    ZANSHIN(ZanshinQuest.INSTANCE);

    private final QuestId<?> questId;
    KamieMode(QuestId questId) {
        this.questId = questId;
    }

    @Override
    public QuestId<?> getQuest() {
        return this.questId;
    }
}
