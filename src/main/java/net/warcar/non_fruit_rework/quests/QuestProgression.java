package net.warcar.non_fruit_rework.quests;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.enums.Style;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;

public class QuestProgression {
    public static AbilityCore.ICanUnlock hasFinishedQuest(QuestId<?> quest) {
        return (entity) -> {
            if (entity instanceof PlayerEntity) {
                return QuestDataCapability.get((PlayerEntity) entity).hasFinishedQuest(quest);
            }
            return false;
        };
    }

    public static AbilityCore.ICanUnlock hasFinishedQuest(QuestId<?> quest, Style style) {
        return (entity) -> {
            if (entity instanceof PlayerEntity) {
                return QuestDataCapability.get((PlayerEntity) entity).hasFinishedQuest(quest) || (!CommonConfig.INSTANCE.isQuestProgressionEnabled() && Style.getFromName(EntityStatsCapability.get(entity).getFightingStyle()) == style);
            }
            return false;
        };
    }
}
