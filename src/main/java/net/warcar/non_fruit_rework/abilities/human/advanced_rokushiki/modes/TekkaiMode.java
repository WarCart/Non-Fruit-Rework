package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.TekkaiGoQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.TekkaiKenpoQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.tekkai.TekkaiUtsugiQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

import javax.annotation.Nullable;

public enum TekkaiMode implements IHasQuestRequirement {
    SIMPLE(null),
    TEKKAI_KENPO(TekkaiKenpoQuest.INSTANCE),
    TEKKAI_GO(TekkaiGoQuest.INSTANCE),
    UTSUGI(TekkaiUtsugiQuest.INSTANCE),
    ;

    private final QuestId<?> questId;

    TekkaiMode(@Nullable QuestId<?> questId) {
        this.questId = questId;
    }


    @Override
    public QuestId<?> getQuest() {
        return questId;
    }
}
