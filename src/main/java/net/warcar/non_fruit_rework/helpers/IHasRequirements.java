package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.player.PlayerEntity;

public interface IHasRequirements {
    boolean canGet(PlayerEntity player);
}
