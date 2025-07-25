package net.warcar.non_fruit_rework.helpers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.warcar.non_fruit_rework.api.events.CanUnlockQuestEvent;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import net.warcar.non_fruit_rework.mixin.IReachDorikiMixin;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
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
                    CanUnlockQuestEvent event = new CanUnlockQuestEvent(entity, quest);
                    MinecraftForge.EVENT_BUS.post(event);
                    if (event.getResult() == CanUnlockQuestEvent.Result.DENY || (event.getResult() == CanUnlockQuestEvent.Result.DEFAULT && !((IHasRequirements) questDefaultInstance).canGet(entity))) {
                        return false;
                    }
                }
                if (!GeneralConfig.ENABLE_STYLES_PROGRESSION.get()) {
                    return false;
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
                        CanUnlockQuestEvent event = new CanUnlockQuestEvent(player, questId);
                        MinecraftForge.EVENT_BUS.post(event);
                        if (event.getResult() == CanUnlockQuestEvent.Result.ALLOW) {
                            return true;
                        } else if (event.getResult() == CanUnlockQuestEvent.Result.DENY) {
                            return false;
                        }
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

    public static class UnfinishedQuestException extends RuntimeException {
        public UnfinishedQuestException(QuestId<?> questId) {
            super("Unfinished quest: " + (questId == null ? null : questId.getRegistryName()));
        }
    }
}
