package net.warcar.non_fruit_rework.init;

import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.*;
import net.warcar.non_fruit_rework.network.packets.server.SOpenScientistMenuPacket;
import net.warcar.non_fruit_rework.network.packets.server.SSyncNonFruitDataPacket;

public class ModPackets {
    public static void init() {
        ModNetwork.registerPacket(new SOpenScientistMenuPacket());
        ModNetwork.registerPacket(new CSpawnPacifistaModelPacket());
        ModNetwork.registerPacket(new CSpawnEntityPacket());
        ModNetwork.registerPacket(new CSyncEntityStatsPacket());
        ModNetwork.registerPacket(new CSyncNonFruitDataPacket());
        ModNetwork.registerPacket(new SSyncNonFruitDataPacket());
        ModNetwork.registerPacket(new CUpdatePassiveAbilityDataPacket());
        ModNetwork.registerPacket(new CRestartPlayerPacket());
    }
}
