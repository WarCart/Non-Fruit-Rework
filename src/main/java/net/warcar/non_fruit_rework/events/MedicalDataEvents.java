package net.warcar.non_fruit_rework.events;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.entity.medical_data.IMedicalData;
import net.warcar.non_fruit_rework.data.entity.medical_data.MedicalDataCapability;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class MedicalDataEvents {
    @SubscribeEvent
    public static void onTick(LivingEvent.LivingUpdateEvent event) {
        IMedicalData data = MedicalDataCapability.get(event.getEntityLiving());
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
    }
}
