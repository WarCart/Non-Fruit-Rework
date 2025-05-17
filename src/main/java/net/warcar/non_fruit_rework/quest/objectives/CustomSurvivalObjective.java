package net.warcar.non_fruit_rework.quest.objectives;

import xyz.pixelatedw.mineminenomi.quests.objectives.TimedSurvivalObjective;

public class CustomSurvivalObjective extends TimedSurvivalObjective {
    public CustomSurvivalObjective(int seconds) {
        super("Survive for %s seconds without getting hit", seconds);
        this.setMaxProgress(seconds * 20);
    }

    @Override
    public String getLocalizedTitle() {
        double max = getMaxProgress();
        this.setMaxProgress(max / 20);
        String title = super.getLocalizedTitle();
        this.setMaxProgress(max);
        return title;
    }
}
