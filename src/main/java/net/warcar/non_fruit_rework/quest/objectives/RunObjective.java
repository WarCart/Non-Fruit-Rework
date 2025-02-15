package net.warcar.non_fruit_rework.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ISurviveObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class RunObjective extends Objective implements ISurviveObjective {
    public RunObjective(int runTicks) {
        super("Run for %s seconds");
        this.setMaxProgress(runTicks);
    }

    @Override
    public boolean checkTime(PlayerEntity playerEntity) {
        return playerEntity.isSprinting();
    }

    @Override
    public String getLocalizedTitle() {
        return new TranslationTextComponent("quest.objective.mineminenomi.run_for_%s_seconds", (int) this.getMaxProgress() / 20).getString();
    }
}
