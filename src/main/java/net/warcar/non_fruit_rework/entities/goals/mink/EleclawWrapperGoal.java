package net.warcar.non_fruit_rework.entities.goals.mink;

import net.minecraft.entity.MobEntity;
import xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility;
import xyz.pixelatedw.mineminenomi.api.entities.GoalUtil;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;

public class EleclawWrapperGoal extends AbilityWrapperGoal<MobEntity, EleclawAbility> {
    public EleclawWrapperGoal(MobEntity entity) {
        super(entity, EleclawAbility.INSTANCE);
    }

    public boolean canUseWrapper() {
        return GoalUtil.hasAliveTarget(this.entity);
    }

    public boolean canContinueToUseWrapper() {
        return true;
    }

    public void startWrapper() {
    }

    public void tickWrapper() {
    }

    public void stopWrapper() {
        this.getAbility().getComponent(ModAbilityKeys.CONTINUOUS).ifPresent((comp) -> {
            comp.stopContinuity(this.entity);
        });
    }
}