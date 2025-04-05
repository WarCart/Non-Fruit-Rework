package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.init.ModQuests;
import net.warcar.non_fruit_rework.mixin.IReachDorikiMixin;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

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
                    return ((IHasRequirements) questDefaultInstance).canGet((PlayerEntity) entity) && QuestDataCapability.get((PlayerEntity) entity).hasFinishedQuest(quest);
                }
                return QuestDataCapability.get((PlayerEntity) entity).hasFinishedQuest(quest);
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public static List<QuestId> getQuestsSorted(PlayerEntity player, List<QuestId<?>>... questss) {
        List<QuestId> finalList = new ArrayList<>();
        for (List<QuestId<?>> quests : questss) {
            finalList.addAll(Arrays.asList(quests.stream()
                    .filter(questId -> {
                        if (questId.createQuest() instanceof IHasRequirements) {
                            return ((IHasRequirements) questId.createQuest()).canGet(player);
                        } else {
                            return true;
                        }
                    })
                    .sorted(Comparator.comparingInt(quest -> {
                        return quest.createQuest().getObjectives().stream().filter(ReachDorikiObjective.class::isInstance)
                                .map((t) -> ((IReachDorikiMixin) t).getDoriki()).findFirst().orElse(0);}))
                    .toArray(QuestId[]::new)));
        }
        return finalList;
    }

    public static void restartPlayer(PlayerEntity player) {
        IQuestData data = QuestDataCapability.get(player);
        for (QuestId<?> questId : ModQuests.CYBORG_QUESTS) {
            data.removeFinishedQuest(questId);
        }
    }

    public static boolean isHybridRace(LivingEntity entity, String race) {
        return NonFruitDataCapability.get(entity).getGenome().computeIfAbsent(race, s -> 0f) > 0.1 && EntityStatsCapability.get(entity).getRace().equalsIgnoreCase("hybrid");
    }

    public static boolean isTrueRace(LivingEntity entity, String race) {
        return EntityStatsCapability.get(entity).getRace().equalsIgnoreCase(race);
    }

    public static boolean isAnyRace(LivingEntity entity, String race) {
        return isTrueRace(entity, race) || isHybridRace(entity, race);
    }
}
