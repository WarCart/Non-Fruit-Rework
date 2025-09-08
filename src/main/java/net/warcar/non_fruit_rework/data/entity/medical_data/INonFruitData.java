package net.warcar.non_fruit_rework.data.entity.medical_data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.experiments.ExperimentResult;
import net.warcar.non_fruit_rework.init.ModDamages;

import java.util.List;
import java.util.Map;

public interface INonFruitData {
    Map<ResourceLocation, Float> getGenome();
    void setGenome(Map<ResourceLocation, Float> newGenome);

    int getEnergySteroidTicks();
    void setEnergySteroidTicks(int time);
    int getEnergySteroidLevel();
    void setEnergySteroidLevel(int level);
    default void popEnergySteroids(int amount) {
        this.setEnergySteroidLevel(this.getEnergySteroidLevel() + amount);
        this.setEnergySteroidTicks(this.getEnergySteroidTicks() + amount * 400);
        if (this.getEnergySteroidLevel() > 100) {
            this.getDataOwner().hurt(ModDamages.OVERDOSE, Float.MAX_VALUE);
        }
    }
    default void popEnergySteroids() {
        this.popEnergySteroids(1);
    }

    int getSulongBallTicks();
    void setSulongBallTicks(int time);
    default void popSulongBall(int amount) {
        this.setSulongBallTicks(this.getSulongBallTicks() + amount * 400);
    }
    default void popSulongBall() {
        this.popSulongBall(1);
    }

    int getRumbleBallTicks();
    void setRumbleBallTicks(int time);
    int getRumbleBallRechargeTicks();
    void setRumbleBallRechargeTicks(int time);
    int getRumbleBallLevel();
    void setRumbleBallLevel(int level);
    default boolean popRumbleBall(int amount) {
        if (this.getRumbleBallLevel() + amount <= 3) {
            this.setRumbleBallTicks(this.getRumbleBallTicks() + amount * 400);
            this.setRumbleBallLevel(this.getRumbleBallLevel() + amount);
            return true;
        }
        return false;
    }
    default boolean popRumbleBall() {
        return this.popRumbleBall(1);
    }

    List<ExperimentResult> getExperiments();

    void addExperiment(ExperimentResult experiment);

    void removeExperiment(ExperimentResult experiment);

    LivingEntity getDataOwner();

    void setDataOwner(LivingEntity dataOwner);
}
