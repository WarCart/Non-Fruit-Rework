package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.LivingEntity;

public interface IHasRequirements {
    boolean canGet(LivingEntity player);
}
