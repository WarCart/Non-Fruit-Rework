package net.warcar.non_fruit_rework.init;

import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.*;
import net.warcar.non_fruit_rework.network.packets.server.SOpenVegapunkMenuPacket;
import net.warcar.non_fruit_rework.network.packets.server.SSyncMedicalDataPacket;

public class ModPackets {
    public static void init() {
        ModNetwork.registerPacket(new SOpenVegapunkMenuPacket());
        ModNetwork.registerPacket(new CSpawnPacifistaModelPacket());
        ModNetwork.registerPacket(new CSpawnSeraphimModelPacket());
        ModNetwork.registerPacket(new CSyncEntityStatsPacket());
        ModNetwork.registerPacket(new CSyncMedicalDataPacket());
        ModNetwork.registerPacket(new SSyncMedicalDataPacket());
        ModNetwork.registerPacket(new CUpdatePassiveAbilityDataPacket());
    }
}
