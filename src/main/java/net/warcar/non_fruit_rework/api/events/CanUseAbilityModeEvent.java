package net.warcar.non_fruit_rework.api.events;

import net.minecraft.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class CanUseAbilityModeEvent extends UnlockableCanUnlockEvent<Enum<?>> {
    private final AbilityCore<?> core;

    public CanUseAbilityModeEvent(LivingEntity entity, Enum<?> unlockable, AbilityCore<?> core) {
        super(entity, unlockable);
        this.core = core;
    }

    public AbilityCore<?> getCore() {
        return core;
    }
}
