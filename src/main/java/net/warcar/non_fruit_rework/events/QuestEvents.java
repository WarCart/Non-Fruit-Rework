package net.warcar.non_fruit_rework.events;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.quests.objectives.TakeDamageObjective;
import xyz.pixelatedw.mineminenomi.api.events.WyLivingHurtEvent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class QuestEvents {
    @SubscribeEvent
    public static void onEntityAttack(WyLivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity && !entity.level.isClientSide) {
            IQuestData questProps = QuestDataCapability.get((PlayerEntity) entity);
            for (Objective obj : questProps.getInProgressObjectives()) {
                if (obj instanceof TakeDamageObjective) {
                    obj.alterProgress(event.getAmount());
                    WyNetwork.sendTo(new SSyncQuestDataPacket(entity.getId(), questProps), (PlayerEntity) entity);
                }
            }
        }
    }
}
