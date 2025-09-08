package net.warcar.non_fruit_rework.events;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.experiments.ExperimentResult;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class NonFruitDataEvents {
    @SubscribeEvent
    public static void onTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        INonFruitData data = NonFruitDataCapability.get(entity);
        data.setEnergySteroidTicks(data.getEnergySteroidTicks() - 1);
        if (data.getEnergySteroidTicks() <= 0) {
            data.setEnergySteroidLevel(0);
            data.setEnergySteroidTicks(0);
        }
        data.setRumbleBallTicks(data.getRumbleBallTicks() - 1);
        if (data.getRumbleBallTicks() <= 0 && data.getRumbleBallLevel() != 0 && data.getRumbleBallRechargeTicks() == 0) {
            data.setRumbleBallTicks(0);
            data.setRumbleBallRechargeTicks(400);
        }
        data.setRumbleBallRechargeTicks(data.getRumbleBallRechargeTicks() - 1);
        if (data.getRumbleBallRechargeTicks() <= 0) {
            data.setRumbleBallRechargeTicks(0);
            data.setRumbleBallLevel(0);
            data.setRumbleBallTicks(0);
        }
        data.setSulongBallTicks(data.getSulongBallTicks() - 1);
        if (data.getSulongBallTicks() <= 0) {
            data.setSulongBallTicks(0);
        }

        if (!entity.level.isClientSide() && entity.isAlive()) {
            for (ExperimentResult result : new ArrayList<>(data.getExperiments())) {
                result.tick(entity);
                result.setTicks(result.getTicks() + 1);
            }
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.isEndConquered()) {
            INonFruitData data = NonFruitDataCapability.get(event.getPlayer());
            for (ExperimentResult result : data.getExperiments()) {
                result.remove(event.getPlayer());
            }
            data.getExperiments().clear();
        }
    }
}
