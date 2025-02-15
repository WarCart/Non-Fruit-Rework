package net.warcar.non_fruit_rework.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ISurviveObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class SwimObjective extends Objective implements ISurviveObjective {
    public SwimObjective(int swimTicks) {
        super("Swim for %s seconds");
        this.setMaxProgress(swimTicks);
    }

    @Override
    public boolean checkTime(PlayerEntity playerEntity) {
        return playerEntity.isSprinting() && playerEntity.isSwimming();
    }

    @Override
    public String getLocalizedTitle() {
        return new TranslationTextComponent("quest.objective.mineminenomi.swim_for_%s_seconds", (int) this.getMaxProgress() / 20).getString();
    }
}
