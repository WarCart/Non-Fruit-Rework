package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.soru.TekkaiDamaQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum SoruMode implements IHasQuestRequirement {
    SIMPLE(null),
    TEKKAI_DAMA(TekkaiDamaQuest.INSTANCE);

    private final QuestId<?> questId;
    SoruMode(QuestId questId) {
        this.questId = questId;
    }

    @Override
    public QuestId<?> getQuest() {
        return this.questId;
    }
}
