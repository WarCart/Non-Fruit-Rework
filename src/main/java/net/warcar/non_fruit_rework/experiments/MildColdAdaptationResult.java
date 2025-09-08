package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class MildColdAdaptationResult extends ExperimentResult {
    @Override
    public void apply(LivingEntity entity) {
    }

    @Override
    public void tick(LivingEntity entity) {
        entity.removeEffect(ModEffects.FROSTBITE.get());
        float temperature = entity.level.getBiome(entity.blockPosition()).getTemperature(entity.blockPosition());
        if (temperature < 0) {
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 10));
        } else if (temperature > 1) {
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10));
        }
    }

    @Override
    public void remove(LivingEntity entity) {
    }

    @Override
    public Type getType() {
        return Type.NEUTRAL;
    }
}
