package net.warcar.non_fruit_rework.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RokuoganAbility;
import xyz.pixelatedw.mineminenomi.api.entities.GoalUtil;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;

public class RokuoganWrapperGoal extends AbilityWrapperGoal<MobEntity, RokuoganAbility> {
    private float distance = 5.0F;

    public RokuoganWrapperGoal(MobEntity entity) {
        super(entity, RokuoganAbility.INSTANCE);
    }

    public boolean canUseWrapper() {
        if (!GoalUtil.hasAliveTarget(this.entity)) {
            return false;
        } else if (!this.entity.getMainHandItem().isEmpty()) {
            return false;
        } else {
            LivingEntity target = this.entity.getTarget();
            return !GoalUtil.isOutsideDistance(this.entity, target, this.distance);
        }
    }

    public boolean canContinueToUseWrapper() {
        return true;
    }

    public void startWrapper() {
    }

    public void tickWrapper() {
    }

    public void stopWrapper() {
    }
}