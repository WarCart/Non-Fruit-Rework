package net.warcar.non_fruit_rework.entities.goals.mink;

import net.minecraft.entity.MobEntity;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalLunaAbility;
import xyz.pixelatedw.mineminenomi.api.entities.GoalUtil;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;

public class ElectricalLunaWrapperGoal extends AbilityWrapperGoal<MobEntity, ElectricalLunaAbility> {
    public ElectricalLunaWrapperGoal(MobEntity entity) {
        super(entity, ElectricalLunaAbility.INSTANCE);
    }

    public boolean canUseWrapper() {
        return GoalUtil.hasAliveTarget(this.entity);
    }

    public boolean canContinueToUseWrapper() {
        return this.getAbility().isCharging();
    }

    public void startWrapper() {
    }

    public void tickWrapper() {
    }

    public void stopWrapper() {
        this.getAbility().getComponent(ModAbilityKeys.SWING_TRIGGER).ifPresent(component -> component.swing(this.entity));
    }
}