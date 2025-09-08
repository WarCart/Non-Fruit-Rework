package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;

public class NothingHappenedResult extends ExperimentResult {
    @Override
    public void apply(LivingEntity entity) {
    }

    @Override
    public void tick(LivingEntity entity) {
        INonFruitData data = NonFruitDataCapability.get(entity);
        data.removeExperiment(this);
    }

    @Override
    public void remove(LivingEntity entity) {
    }

    @Override
    public Type getType() {
        return Type.NEUTRAL;
    }
}
