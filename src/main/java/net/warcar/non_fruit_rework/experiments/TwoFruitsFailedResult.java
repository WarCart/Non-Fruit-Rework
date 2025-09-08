package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;

public class TwoFruitsFailedResult extends ExperimentResult {
    @Override
    public void apply(LivingEntity entity) {
        entity.hurt(ModDamageSource.DEVILS_CURSE, Float.MAX_VALUE);
    }

    @Override
    public void tick(LivingEntity entity) {
        entity.hurt(ModDamageSource.DEVILS_CURSE, Float.MAX_VALUE);
    }

    @Override
    public void remove(LivingEntity entity) {
    }

    @Override
    public Type getType() {
        return Type.FATAL;
    }
}
