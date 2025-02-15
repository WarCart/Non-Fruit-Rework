package net.warcar.non_fruit_rework.entities.goals.lunarian;

import net.minecraft.entity.MobEntity;
import net.warcar.non_fruit_rework.abilities.lunarian.DisasterFlamesAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;

public class DisasterFlamesWrapperGoal extends AbilityWrapperGoal<MobEntity, DisasterFlamesAbility> {
    public DisasterFlamesWrapperGoal(MobEntity entity) {
        super(entity, DisasterFlamesAbility.INSTANCE);
    }

    public boolean canUseWrapper() {
        return true;
    }

    public boolean canContinueToUseWrapper() {
        return true;
    }

    public void startWrapper() {
    }

    public void tickWrapper() {
    }

    public void stopWrapper() {
        this.getAbility().use(this.entity);
    }
}
