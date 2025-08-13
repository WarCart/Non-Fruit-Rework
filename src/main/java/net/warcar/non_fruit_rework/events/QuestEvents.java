package net.warcar.non_fruit_rework.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.api.quests.objectives.IObtainBellyObjective;
import net.warcar.non_fruit_rework.quest.objectives.TakeDamageObjective;
import xyz.pixelatedw.mineminenomi.api.events.WyLivingHurtEvent;
import xyz.pixelatedw.mineminenomi.api.events.stats.CurrencyEvent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class QuestEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityAttack(WyLivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity && !entity.level.isClientSide) {
            IQuestData questProps = QuestDataCapability.get((PlayerEntity) entity);
            for (Objective obj : questProps.getInProgressObjectives()) {
                if (obj instanceof TakeDamageObjective && ((TakeDamageObjective) obj).getCheck().valid((PlayerEntity) entity, event.getAmount(), event.getSource())) {
                    obj.alterProgress((PlayerEntity) event.getEntityLiving(), event.getAmount(), false);
                    WyNetwork.sendTo(new SSyncQuestDataPacket(entity.getId(), questProps), (PlayerEntity) entity);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBellyGain(CurrencyEvent.Pre event) {
        PlayerEntity entity = event.getPlayer();
        if (event.getAmount() != 0 && !entity.level.isClientSide) {
            IQuestData questProps = QuestDataCapability.get(entity);
            for (Objective obj : questProps.getInProgressObjectives()) {
                if (obj instanceof IObtainBellyObjective && ((IObtainBellyObjective) obj).checkBelly(entity)) {
                    obj.alterProgress(entity, 1);
                    WyNetwork.sendTo(new SSyncQuestDataPacket(entity.getId(), questProps), entity);
                }
            }
        }
    }
}