package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class BoozeAddictionResult extends ExperimentResult {
    @Override
    public void apply(LivingEntity entity) {
    }

    @Override
    public void tick(LivingEntity entity) {
        EffectInstance effect = entity.getEffect(ModEffects.DRUNK.get());
        if (effect != null) {
            if (effect.getAmplifier() >= 2) {
                entity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 10, effect.getAmplifier() - 2));
            }
        } else {
            entity.addEffect(new EffectInstance(Effects.WEAKNESS, 10));
        }
    }

    @Override
    public void remove(LivingEntity entity) {
    }

    @Override
    public Type getType() {
        return Type.NEGATIVE;
    }
}
