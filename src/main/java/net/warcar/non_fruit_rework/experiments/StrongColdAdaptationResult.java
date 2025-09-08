package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class StrongColdAdaptationResult extends ExperimentResult {
    @Override
    public void apply(LivingEntity entity) {
    }

    @Override
    public void tick(LivingEntity entity) {
        entity.removeEffect(ModEffects.FROSTBITE.get());
        entity.removeEffect(ModEffects.FROZEN.get());
        float temperature = entity.level.getBiome(entity.blockPosition()).getTemperature(entity.blockPosition());
        if (temperature < 0) {
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 10, 2));
        } else if (temperature > 0.6) {
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10, 2));
        }
        if (entity.level.dimension() == World.NETHER) {
            entity.setSecondsOnFire(1);
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
