package net.warcar.non_fruit_rework.events.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public class QuestFinishedEvent extends PlayerEvent {
    private final QuestId<?> id;

    public QuestFinishedEvent(PlayerEntity player, QuestId<?> id) {
        super(player);
        this.id = id;
    }

    public QuestId<?> getQuest() {
        return this.id;
    }
}
