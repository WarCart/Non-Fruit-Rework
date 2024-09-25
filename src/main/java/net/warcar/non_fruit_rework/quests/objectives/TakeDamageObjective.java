package net.warcar.non_fruit_rework.quests.objectives;

import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class TakeDamageObjective extends Objective {
    public TakeDamageObjective(String title, float damage) {
        super(title);
        this.setMaxProgress(damage);
    }
}
