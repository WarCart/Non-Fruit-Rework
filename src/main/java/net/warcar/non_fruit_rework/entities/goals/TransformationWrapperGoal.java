package net.warcar.non_fruit_rework.entities.goals;

import net.minecraft.entity.MobEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.GoalUtil;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;

public class TransformationWrapperGoal<A extends Ability> extends AbilityWrapperGoal<MobEntity, A> {
    public TransformationWrapperGoal(MobEntity entity, AbilityCore<A> core) {
        super(entity, core);
    }

    @Override
    public boolean canUseWrapper() {
        return GoalUtil.hasAliveTarget(this.entity);
    }

    @Override
    public boolean canContinueToUseWrapper() {
        return true;
    }

    @Override
    public void startWrapper() {
    }

    @Override
    public void tickWrapper() {
    }

    @Override
    public void stopWrapper() {
        this.getAbility().getComponent(ModAbilityKeys.MORPH).ifPresent(comp -> comp.stopMorph(this.entity));
    }
}
