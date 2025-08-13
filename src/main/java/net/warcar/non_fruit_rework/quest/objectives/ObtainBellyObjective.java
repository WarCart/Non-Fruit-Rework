package net.warcar.non_fruit_rework.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.api.quests.objectives.IObtainBellyObjective;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;

public class ObtainBellyObjective extends Objective implements IObtainBellyObjective {
    private final long cost;

    public ObtainBellyObjective(String title, long cost) {
        super(title);
        this.cost = cost;
    }

    public long getCost() {
        return cost;
    }

    @Override
    public boolean checkBelly(PlayerEntity player) {
        return EntityStatsCapability.get(player).getBelly() > cost;
    }

    @Override
    public String getLocalizedTitle()
    {
        String objectiveKey = String.format("quest.objective." + ModMain.PROJECT_ID + ".%s", this.getId());
        return new TranslationTextComponent(objectiveKey, this.cost).getString();
    }
}
