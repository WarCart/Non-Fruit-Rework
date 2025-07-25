package net.warcar.non_fruit_rework.api.events;

import net.minecraft.entity.LivingEntity;

public class CanUseAbilityModeEvent extends UnlockableCanUnlockEvent<Enum<?>> {
    public CanUseAbilityModeEvent(LivingEntity entity, Enum<?> unlockable) {
        super(entity, unlockable);
    }
}
