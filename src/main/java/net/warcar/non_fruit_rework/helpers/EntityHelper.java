package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.abilities.GenesAbility;
import net.warcar.non_fruit_rework.config.CommonConfig;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.enums.ModifiableAttributes;
import net.warcar.non_fruit_rework.init.ModQuests;
import net.warcar.non_fruit_rework.init.ModRaces;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.DevilFruitHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.LeapWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.TakedownKickWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.brawler.HakaiHoWrapperGoal;
import xyz.pixelatedw.mineminenomi.events.abilities.AbilityProgressionEvents;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public final class EntityHelper {
    private EntityHelper() {} //Don't initialize

    public static void addDefaultBossGoals(CreatureEntity entity, ChallengeInfo challenge) {
        entity.goalSelector.addGoal(0, new DashDodgeProjectilesGoal(entity, 200.0F, 3.0F));
        entity.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(entity, 1.0F, true));
        entity.goalSelector.addGoal(0, new SprintTowardsTargetGoal(entity));
        entity.goalSelector.addGoal(3, new LeapWrapperGoal(entity));
        entity.goalSelector.addGoal(2, new TakedownKickWrapperGoal(entity));
        entity.goalSelector.addGoal(1, new JumpOutOfHoleGoal(entity));
        if (challenge.isDifficultyHard()) {
            entity.goalSelector.addGoal(3, new HakaiHoWrapperGoal(entity));
        }
        MobsHelper.addBasicNPCGoals(entity);
    }

    public static ResourceLocation[] getTexture(String name) {
        return new ResourceLocation[]{new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/entities/" + name + ".png")};
    }

    public static void restartPlayer(PlayerEntity player) {
        IQuestData data = QuestDataCapability.get(player);
        for (QuestId<?> questId : ModQuests.CYBORG_QUESTS) {
            data.removeFinishedQuest(questId);
        }
        IDevilFruit fruitData = DevilFruitCapability.get(player);
        if (!fruitData.getDevilFruitItem().is(ModTags.Items.ZOAN) || !CommonConfig.INSTANCE.isKeepZoan()) {
            fruitData.setAwakenedFruit(false);
            DevilFruitHelper.respawnDevilFruit(player, fruitData);
            fruitData.setDevilFruit((ResourceLocation) null);
            AbilityProgressionEvents.checkForDevilFruitUnlocks(player);
            WyNetwork.sendToAllTrackingAndSelf(new SSyncDevilFruitPacket(player.getId(), fruitData), player);
        }
        IEntityStats entityStats = EntityStatsCapability.get(player);
        entityStats.alterDoriki(-entityStats.getDoriki() * 0.9, StatChangeSource.DEATH);
    }

    public static boolean isHybridRace(LivingEntity entity, ResourceLocation race) {
        return NonFruitDataCapability.get(entity).getGenome().computeIfAbsent(race, s -> 0f) > 0.1 && EntityStatsCapability.get(entity).getRace().equals(ModRaces.HYBRID.getId());
    }

    public static boolean isTrueRace(LivingEntity entity, ResourceLocation race) {
        return EntityStatsCapability.get(entity).getRace().equals(race) ||
                (race.equals(ModValues.HUMAN) && DevilFruitCapability.get(entity).hasDevilFruit(ModAbilities.HITO_HITO_NO_MI));
    }

    public static boolean isAnyRace(LivingEntity entity, ResourceLocation race) {
        return isTrueRace(entity, race) || isHybridRace(entity, race);
    }

    public static boolean noGeneModifications(LivingEntity entity) {
        GenesAbility ability = AbilityDataCapability.get(entity).getPassiveAbility(GenesAbility.INSTANCE);
        for (ModifiableAttributes attribute : ModifiableAttributes.values()) {
            if (ability.getGenes().get(attribute) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean canUseAdvancedRokushiki(LivingEntity entity) {
        return isTrueRace(entity, ModValues.HUMAN) && noGeneModifications(entity);
    }
}
