package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PoisonToleranceResult extends ExperimentResult {
    @Override
    public void apply(LivingEntity entity) {
    }

    @Override
    public void tick(LivingEntity entity) {
        if (entity.hasEffect(ModEffects.DOKU_POISON.get()) && entity.getEffect(ModEffects.DOKU_POISON.get()).getAmplifier() < 4) {
            entity.removeEffect(ModEffects.DOKU_POISON.get());
        }
        entity.removeEffect(Effects.POISON);
    }

    @Override
    public void remove(LivingEntity entity) {
    }

    @Override
    public Type getType() {
        return Type.POSITIVE;
    }
}
