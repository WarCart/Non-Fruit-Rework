package net.warcar.non_fruit_rework.quest.fishman_karate.racial;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanKarateHelper;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MurasameAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class YarinamiQuest extends Quest implements IHasRequirements {
    public static final QuestId<YarinamiQuest> INSTANCE = new QuestId.Builder<>("Trial: Yarinami", YarinamiQuest::new)
            .addRequirements(MurasameQuest.INSTANCE).build();
    private static final KillEntityObjective.ICheckKill IN_WATER = (playerEntity, livingEntity, damageSource) -> FishmanKarateHelper.isInWater(playerEntity);

    public YarinamiQuest(QuestId<YarinamiQuest> core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Reach %s doriki", 2000);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s entities while in water using Murasame", 50, IN_WATER.and(SharedKillChecks.checkAbilitySource(MurasameAbility.INSTANCE))).addRequirement(objective));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return QuestHelper.isAnyRace(player, "fishman");
    }
}
