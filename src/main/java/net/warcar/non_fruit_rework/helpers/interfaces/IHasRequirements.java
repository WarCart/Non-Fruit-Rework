package net.warcar.non_fruit_rework.helpers.interfaces;

import net.minecraft.entity.LivingEntity;

public interface IHasRequirements {
    boolean canGet(LivingEntity player);
}
