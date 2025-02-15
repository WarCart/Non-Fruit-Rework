package net.warcar.non_fruit_rework.entities.goals.mink;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalShowerAbility;
import xyz.pixelatedw.mineminenomi.api.entities.GoalUtil;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;

public class ElectricalShowerWrapperGoal extends AbilityWrapperGoal<MobEntity, ElectricalShowerAbility> {
    private LivingEntity target;
    private double distance = 5.0;

    public ElectricalShowerWrapperGoal(MobEntity entity) {
        super(entity, ElectricalShowerAbility.INSTANCE);
    }

    public boolean canUseWrapper() {
        if (!GoalUtil.hasAliveTarget(this.entity)) {
            return false;
        } else {
            this.target = this.entity.getTarget();
            return !GoalUtil.isOutsideDistance(this.entity, this.target, distance);
        }
    }

    public boolean canContinueToUseWrapper() {
        return true;
    }

    public void startWrapper() {
        GoalUtil.lookAtEntity(this.entity, this.target);
    }

    public void tickWrapper() {
        GoalUtil.lookAtEntity(this.entity, this.target);
    }

    public void stopWrapper() {
    }
}