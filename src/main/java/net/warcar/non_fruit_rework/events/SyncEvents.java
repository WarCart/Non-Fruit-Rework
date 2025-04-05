package net.warcar.non_fruit_rework.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.server.SSyncNonFruitDataPacket;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class SyncEvents {
    @SubscribeEvent
    public static void onPlayerChangeDimensions(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        INonFruitData entityStatsProps = NonFruitDataCapability.get(player);
        ModNetwork.sendToAllTrackingAndSelf(new SSyncNonFruitDataPacket(player.getId(), entityStatsProps), player);
    }
}
