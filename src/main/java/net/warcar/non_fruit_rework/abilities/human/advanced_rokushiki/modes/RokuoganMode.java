package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.rokuogan.SaiDaiRinRokuoganQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum RokuoganMode implements IHasQuestRequirement {
    SIMPLE(null),
    SAI_DAI_RIN(SaiDaiRinRokuoganQuest.INSTANCE);

    private final QuestId<?> quest;
    RokuoganMode(QuestId<?> questId) {
        this.quest = questId;
    }

    @Override
    public QuestId<?> getQuest() {
        return quest;
    }
}
