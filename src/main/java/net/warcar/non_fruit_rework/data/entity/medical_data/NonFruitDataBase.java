package net.warcar.non_fruit_rework.data.entity.medical_data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.experiments.ExperimentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NonFruitDataBase implements INonFruitData {
    private Map<ResourceLocation, Float> genome = new HashMap<>();

    private int energySteroidTicks;
    private int energySteroidLevel;

    private int rumbleBallTicks;
    private int rumbleBallLevel;

    private int sulongBallTicks;

    private final List<ExperimentResult> experiments = new ArrayList<>();

    private LivingEntity dataOwner;

    @Override
    public Map<ResourceLocation, Float> getGenome() {
        return this.genome;
    }

    @Override
    public void setGenome(Map<ResourceLocation, Float> newGenome) {
        this.genome = newGenome;
    }

    @Override
    public int getEnergySteroidTicks() {
        return this.energySteroidTicks;
    }

    @Override
    public void setEnergySteroidTicks(int time) {
        this.energySteroidTicks = time;
    }

    @Override
    public int getEnergySteroidLevel() {
        return this.energySteroidLevel;
    }

    @Override
    public void setEnergySteroidLevel(int level) {
        this.energySteroidLevel = level;
    }

    @Override
    public int getSulongBallTicks() {
        return this.sulongBallTicks;
    }

    @Override
    public void setSulongBallTicks(int time) {
        this.sulongBallTicks = time;
    }

    @Override
    public int getRumbleBallTicks() {
        return this.rumbleBallTicks;
    }

    @Override
    public void setRumbleBallTicks(int time) {
        this.rumbleBallTicks = time;
    }

    @Override
    public int getRumbleBallRechargeTicks() {
        return this.rumbleBallTicks;
    }

    @Override
    public void setRumbleBallRechargeTicks(int time) {
        this.rumbleBallTicks = time;
    }

    @Override
    public int getRumbleBallLevel() {
        return this.rumbleBallLevel;
    }

    @Override
    public void setRumbleBallLevel(int level) {
        this.rumbleBallLevel = level;
    }

    @Override
    public List<ExperimentResult> getExperiments() {
        return this.experiments;
    }

    public void addExperiment(ExperimentResult experiment) {
        for (ExperimentResult experimentResult : this.experiments) {
            if (experimentResult.getRegistryName().equals(experiment.getRegistryName())) {
                return;
            }
        }
        this.experiments.add(experiment);
        experiment.apply(this.dataOwner);
    }

    public void removeExperiment(ExperimentResult experiment) {
        this.experiments.remove(experiment);
        experiment.remove(this.dataOwner);
    }

    public LivingEntity getDataOwner() {
        return dataOwner;
    }

    public void setDataOwner(LivingEntity dataOwner) {
        this.dataOwner = dataOwner;
    }

    @Override
    public boolean hasExperiment(ExperimentResult result) {
        return false;
    }
}
