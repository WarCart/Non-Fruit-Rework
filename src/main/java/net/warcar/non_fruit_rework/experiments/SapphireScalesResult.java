package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.warcar.non_fruit_rework.init.ModDamages;
import net.warcar.non_fruit_rework.init.ModEntityEffects;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class SapphireScalesResult extends ExperimentResult {
    private float lightExposure = 0.0f;

    @Override
    public void apply(LivingEntity entity) {
    }

    @Override
    public void tick(LivingEntity entity) {
        if (entity.level.canSeeSky(entity.blockPosition()) && entity.level.isDay()) {
            lightExposure += 0.01f;
        } else if (entity.level.canSeeSkyFromBelowWater(entity.blockPosition())) {
            lightExposure += 0.001f;
        } else {
            lightExposure += 0.00005f;
        }
        if (lightExposure > 60.0f) {
            entity.hurt(ModDamages.SAPPHIRE_SCALES, 15);
            entity.addEffect(new EffectInstance(ModEffects.UNCONSCIOUS.get(), 10, 3));
            entity.addEffect(new EffectInstance(ModEntityEffects.SAPPHIRE_SCALES.get(), 10, 2, true, false));
        } else if (lightExposure > 45.0f) {
            entity.addEffect(new EffectInstance(Effects.WEAKNESS, 10, 4));
            entity.addEffect(new EffectInstance(ModEffects.FATIGUE_EFFECT.get(), 10, 3));
            entity.addEffect(new EffectInstance(ModEntityEffects.SAPPHIRE_SCALES.get(), 10, 2, true, false));
            if (entity.getRandom().nextFloat() < 0.001) {
                entity.addEffect(new EffectInstance(ModEffects.DIZZY.get(), 100, 1));
            }
        } else if (lightExposure > 30.0f) {
            entity.addEffect(new EffectInstance(ModEffects.FATIGUE_EFFECT.get(), 10, 1));
            entity.addEffect(new EffectInstance(ModEntityEffects.SAPPHIRE_SCALES.get(), 10, 1, true, false));
        } else if (lightExposure > 10.0f) {
            entity.addEffect(new EffectInstance(ModEntityEffects.SAPPHIRE_SCALES.get(), 10, 0, true, false));
        }
    }

    @Override
    public void remove(LivingEntity entity) {
    }

    @Override
    public Type getType() {
        return Type.FATAL;
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        CompoundNBT save = super.save(tag);
        save.putFloat("lightExposure", lightExposure);
        return save;
    }

    @Override
    public void load(CompoundNBT tag) {
        super.load(tag);
        lightExposure = tag.getFloat("lightExposure");
    }
}
