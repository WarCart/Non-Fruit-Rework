package net.warcar.non_fruit_rework.entities.goals.mink;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalMissileAbility;
import xyz.pixelatedw.mineminenomi.api.entities.GoalUtil;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;

public class ElectricalMissileWrapperGoal extends AbilityWrapperGoal<MobEntity, ElectricalMissileAbility> {
    private LivingEntity target;

    public ElectricalMissileWrapperGoal(MobEntity entity) {
        super(entity, ElectricalMissileAbility.INSTANCE);
    }

    public boolean canUseWrapper() {
        return GoalUtil.hasAliveTarget(this.entity);
    }

    public boolean canContinueToUseWrapper() {
        if (!GoalUtil.hasAliveTarget(this.entity)) {
            return false;
        } else {
            this.target = this.entity.getTarget();
            return !GoalUtil.isOutsideDistance(this.entity, this.target, 10);
        }
    }

    public void startWrapper() {
        GoalUtil.lookAtEntity(this.entity, this.target);
    }

    public void tickWrapper() {
        GoalUtil.lookAtEntity(this.entity, this.target);
    }

    public void stopWrapper() {
        this.getAbility().getComponent(ModAbilityKeys.CONTINUOUS).ifPresent((comp) -> {
            comp.stopContinuity(this.entity);
        });
    }
}