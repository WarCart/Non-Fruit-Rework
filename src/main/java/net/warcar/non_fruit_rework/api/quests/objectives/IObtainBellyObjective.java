package net.warcar.non_fruit_rework.api.quests.objectives;

import net.minecraft.entity.player.PlayerEntity;

public interface IObtainBellyObjective {
    boolean checkBelly(PlayerEntity player);
}
