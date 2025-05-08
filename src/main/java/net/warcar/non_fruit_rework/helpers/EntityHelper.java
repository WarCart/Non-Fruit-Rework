package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.CreatureEntity;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.LeapWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.TakedownKickWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.brawler.HakaiHoWrapperGoal;

public final class EntityHelper {
    private EntityHelper() {} //Don't initialize

    public static void addDefaultBossGoals(CreatureEntity entity, ChallengeInfo challenge) {
        entity.goalSelector.addGoal(0, new DashDodgeProjectilesGoal(entity, 200.0F, 3.0F));
        entity.goalSelector.addGoal(1, new DashDodgeTargetGoal(entity, 100.0F, 3.0F));
        entity.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(entity, 1.0F, true));
        entity.goalSelector.addGoal(0, new SprintTowardsTargetGoal(entity));
        entity.goalSelector.addGoal(3, new LeapWrapperGoal(entity));
        entity.goalSelector.addGoal(2, new TakedownKickWrapperGoal(entity));
        if (challenge.isDifficultyHard()) {
            entity.goalSelector.addGoal(3, new HakaiHoWrapperGoal(entity));
        }
        MobsHelper.addBasicNPCGoals(entity);
    }
}
