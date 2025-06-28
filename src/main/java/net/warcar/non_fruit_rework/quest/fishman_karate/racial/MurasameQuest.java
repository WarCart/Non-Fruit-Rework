package net.warcar.non_fruit_rework.quest.fishman_karate.racial;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.SwimObjective;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanKarateHelper;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedHitChecks;

public class MurasameQuest extends Quest implements IHasRequirements {
    public static final QuestId<MurasameQuest> INSTANCE = new QuestId.Builder<>("Trial: Murasame", MurasameQuest::new)
            .addRequirements(UchimizuQuest.INSTANCE).build();
    private static final HitEntityObjective.ICheckHit IN_WATER = (playerEntity, livingEntity, damageSource, amount) -> FishmanKarateHelper.isInWater(playerEntity);
    private static final KillEntityObjective.ICheckKill TARGET_IN_WATER = (playerEntity, livingEntity, damageSource) -> FishmanKarateHelper.isInWater(livingEntity);

    public MurasameQuest(QuestId<MurasameQuest> core) {
        super(core);
        ReachDorikiObjective dorikiObjective = new ReachDorikiObjective("Reach %s doriki", 650);
        this.addObjective(dorikiObjective);
        Objective objective = new SwimObjective(4800).addRequirement(dorikiObjective);
        this.addObjective(objective);
        this.addObjective(new HitEntityObjective("Hit %s entities while in water using Uchimizu", 25, IN_WATER.and(SharedHitChecks.checkAbilitySource(UchimizuAbility.INSTANCE))).addRequirement(objective));
        this.addObjective(new KillEntityObjective("Kill %s entities in water", 50, TARGET_IN_WATER).addRequirement(objective));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return EntityHelper.isAnyRace(player, ModValues.FISHMAN);
    }
}
