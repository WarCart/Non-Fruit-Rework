package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;

public final class QuestHelper {
    private QuestHelper() {} //Don't initialize
    public static AbilityCore.ICanUnlock questFinnished(QuestId quest) {
        return entity -> hasFinishedQuest(entity, quest);
    }

    public static boolean hasFinishedQuest(LivingEntity entity, QuestId quest) {
        if (entity instanceof PlayerEntity) {
            return QuestDataCapability.get((PlayerEntity) entity).hasFinishedQuest(quest);
        }
        return false;
    }
}
