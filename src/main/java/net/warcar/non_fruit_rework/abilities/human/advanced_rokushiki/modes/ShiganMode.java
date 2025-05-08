package net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes;

import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.JushiganQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.ShiganOrenQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan.TobuShiganQuest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public enum ShiganMode implements IHasQuestRequirement {
    SIMPLE(null),
    TOBU(TobuShiganQuest.INSTANCE),
    OREN(ShiganOrenQuest.INSTANCE),
    JUSHIGAN(JushiganQuest.INSTANCE),
    ;

    private final QuestId<?> questId;
    ShiganMode(QuestId<?> questId) {
        this.questId = questId;
    }

    @Override
    public QuestId<?> getQuest() {
        return questId;
    }
}
