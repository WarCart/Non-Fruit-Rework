package net.warcar.non_fruit_rework.quest.fishman_karate.racial;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.SwimObjective;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanKarateHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective;

public class UchimizuQuest extends Quest implements IHasRequirements {
    public static final QuestId<UchimizuQuest> INSTANCE = new QuestId.Builder<>("Trial: Uchimizu", UchimizuQuest::new).build();
    private static final HitEntityObjective.ICheckHit IN_WATER = (playerEntity, livingEntity, damageSource, amount) -> FishmanKarateHelper.isInWater(playerEntity);

    public UchimizuQuest(QuestId<UchimizuQuest> core) {
        super(core);
        SwimObjective objective = new SwimObjective(2400);
        this.addObjective(objective);
        this.addObjective(new HitEntityObjective("Hit %s entities while in water", 10, IN_WATER).addRequirement(objective));
    }

    @Override
    public boolean canGet(PlayerEntity player) {
        return QuestHelper.isAnyRace(player, "fishman");
    }
}
