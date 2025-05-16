package net.warcar.non_fruit_rework.init;

import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.*;
import net.warcar.non_fruit_rework.network.packets.server.SOpenVegapunkMenuPacket;
import net.warcar.non_fruit_rework.network.packets.server.SSyncNonFruitDataPacket;

public class ModPackets {
    public static void init() {
        ModNetwork.registerPacket(new SOpenVegapunkMenuPacket());
        ModNetwork.registerPacket(new CSpawnPacifistaModelPacket());
        ModNetwork.registerPacket(new CSpawnSeraphimModelPacket());
        ModNetwork.registerPacket(new CSyncEntityStatsPacket());
        ModNetwork.registerPacket(new CSyncNonFruitDataPacket());
        ModNetwork.registerPacket(new SSyncNonFruitDataPacket());
        ModNetwork.registerPacket(new CUpdatePassiveAbilityDataPacket());
        ModNetwork.registerPacket(new CRestartPlayerPacket());
    }
}
