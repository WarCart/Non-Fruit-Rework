package net.warcar.non_fruit_rework.data.entity.mastery;

import net.warcar.non_fruit_rework.init.ModConfig;

public class MasteryDataBase implements IMasteryData {
    private float mastery = 0;

    public float getMastery() {
        return mastery;
    }

    public void setMastery(float mastery) {
        this.mastery = mastery;
    }

    public void addMastery(float mastery) {
        if (this.mastery + mastery >= ModConfig.INSTANCE.getMaxMastery()) {
            this.mastery = ModConfig.INSTANCE.getMaxMastery();
        } else if (this.mastery + mastery <= 0) {
            this.mastery = 0;
        } else {
            this.mastery += mastery;
        }
    }
}
