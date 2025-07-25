package net.warcar.non_fruit_rework.api.events;

import net.minecraft.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public class CanUnlockQuestEvent extends UnlockableCanUnlockEvent<QuestId<?>> {
    public CanUnlockQuestEvent(LivingEntity entity, QuestId<?> unlockable) {
        super(entity, unlockable);
    }
}
