package net.warcar.non_fruit_rework.helpers;

import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.supa.AtomicRushWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.supa.SparClawWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.supa.SparklingDaisyWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.supa.SpiralHollowWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public final class DFHelper {
    private DFHelper() {} //Don't initialize

    public static void addDfMoves(OPEntity entity, AkumaNoMiItem fruit) {
        if (fruit == ModAbilities.SUPA_SUPA_NO_MI) {
            entity.goalSelector.addGoal(1, new SparClawWrapperGoal(entity));
            entity.goalSelector.addGoal(2, new SpiralHollowWrapperGoal(entity));
            entity.goalSelector.addGoal(2, new SparklingDaisyWrapperGoal(entity));
            entity.goalSelector.addGoal(3, new AtomicRushWrapperGoal(entity));
        }
    }
}
