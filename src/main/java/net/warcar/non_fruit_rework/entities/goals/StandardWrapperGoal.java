package net.warcar.non_fruit_rework.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import xyz.pixelatedw.mineminenomi.abilities.supa.AtomicRushAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.GoalUtil;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;

public class StandardWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<MobEntity, A> {
    private LivingEntity target;
    private final double maxDistance;
    public StandardWrapperGoal(MobEntity entity, AbilityCore<A> core, double maxDistance) {
        super(entity, core);
        this.maxDistance = maxDistance;
    }

    public boolean canUseWrapper() {
        if (!GoalUtil.hasAliveTarget(this.entity)) {
            return false;
        } else {
            this.target = this.entity.getTarget();
            if (!GoalUtil.canSee(this.entity, this.target)) {
                return false;
            } else return !GoalUtil.isOutsideDistance(this.entity, this.target, this.maxDistance);
        }
    }

    public boolean canContinueToUseWrapper() {
        return GoalUtil.isWithinDistance(this.entity, this.target, this.maxDistance * 1.5D);
    }

    public void startWrapper() {
        if (this.getAbility().hasComponent(ModAbilityKeys.PROJECTILE)) {
            GoalUtil.lookAtEntity(this.entity, this.target);
        }
    }

    public void tickWrapper() {
    }

    public void stopWrapper() {
        this.getAbility().getComponent(ModAbilityKeys.CONTINUOUS).ifPresent((comp) -> comp.stopContinuity(this.entity));
    }
}
