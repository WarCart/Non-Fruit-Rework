package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.config.CommonConfig;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.init.ModQuests;
import net.warcar.non_fruit_rework.init.ModRaces;
import net.warcar.non_fruit_rework.mixin.IReachDorikiMixin;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.DevilFruitHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.events.abilities.AbilityProgressionEvents;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class QuestHelper {
    private QuestHelper() {} //Don't initialize
    public static AbilityCore.ICanUnlock questFinished(QuestId quest) {
        return entity -> hasFinishedQuest(entity, quest);
    }

    public static boolean hasFinishedQuest(LivingEntity entity, QuestId quest) {
        try {
            if (entity instanceof PlayerEntity) {
                Quest questDefaultInstance = quest.createQuest();
                if (questDefaultInstance instanceof IHasRequirements) {
                    return ((IHasRequirements) questDefaultInstance).canGet(entity) && QuestDataCapability.get((PlayerEntity) entity).hasFinishedQuest(quest);
                }
                return QuestDataCapability.get((PlayerEntity) entity).hasFinishedQuest(quest);
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public static List<QuestId> getQuestsSorted(PlayerEntity player, List<QuestId<?>>... questss) {
        List<QuestId<?>> quests = new ArrayList<>();
        Arrays.stream(questss).forEach(quests::addAll);
        return new ArrayList<>(Arrays.asList(quests.stream()
                .filter(questId -> {
                    if (player == null) {
                        return true;
                    }
                    if (questId.createQuest() instanceof IHasRequirements) {
                        return ((IHasRequirements) questId.createQuest()).canGet(player);
                    } else {
                        return true;
                    }
                })
                .sorted(Comparator.comparingInt(quest -> {
                    return quest.createQuest().getObjectives().stream().filter(ReachDorikiObjective.class::isInstance)
                            .map((t) -> ((IReachDorikiMixin) t).getDoriki()).findFirst().orElse(0);
                }))
                .toArray(QuestId[]::new)));
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

    public static boolean canUseAdvancedRokushiki(LivingEntity entity) {
        return QuestHelper.isTrueRace(entity, ModValues.HUMAN);
    }

    public static class UnfinishedQuestException extends RuntimeException {
        public UnfinishedQuestException(QuestId<?> questId) {
            super("Unfinished quest: " + (questId == null ? null : questId.getRegistryName()));
        }
    }
}
