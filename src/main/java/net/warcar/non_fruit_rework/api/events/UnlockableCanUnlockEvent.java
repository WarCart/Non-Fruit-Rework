package net.warcar.non_fruit_rework.api.events;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;

@Event.HasResult
public abstract class UnlockableCanUnlockEvent<T> extends LivingEvent {
    protected final T unlockable;
    public UnlockableCanUnlockEvent(LivingEntity entity, T unlockable) {
        super(entity);
        this.unlockable = unlockable;
    }

    public T getUnlockable() {
        return unlockable;
    }
}
