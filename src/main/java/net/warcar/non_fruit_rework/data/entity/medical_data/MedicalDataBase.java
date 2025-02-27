package net.warcar.non_fruit_rework.data.entity.medical_data;

import net.minecraft.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class MedicalDataBase implements IMedicalData {
    private Map<String, Float> genome = new HashMap<>();

    private int energySteroidTicks;
    private int energySteroidLevel;

    private int rumbleBallTicks;
    private int rumbleBallLevel;

    private int sulongBallTicks;

    private LivingEntity dataOwner;

    @Override
    public Map<String, Float> getGenome() {
        return this.genome;
    }

    @Override
    public void setGenome(Map<String, Float> newGenome) {
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

    public LivingEntity getDataOwner() {
        return dataOwner;
    }

    public void setDataOwner(LivingEntity dataOwner) {
        this.dataOwner = dataOwner;
    }
}
