package net.warcar.non_fruit_rework.experiments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class CantSwimResult extends ExperimentResult {
    @Override
    public void apply(LivingEntity entity) {
    }

    @Override
    public void tick(LivingEntity entity) {
        boolean isCreative = entity instanceof PlayerEntity && ((PlayerEntity)entity).isCreative();
        if (AbilityHelper.isAffectedByWater(entity) && !isCreative) {
            if (entity.isVisuallySwimming()) {
                AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x, entity.getDeltaMovement().y - 0.15, entity.getDeltaMovement().z);
            } else {
                AbilityHelper.setDeltaMovement(entity, entity.getDeltaMovement().x, entity.getDeltaMovement().y - 0.1, entity.getDeltaMovement().z);
            }
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
