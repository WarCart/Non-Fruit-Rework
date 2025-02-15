package net.warcar.non_fruit_rework.init;

import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.CSpawnPacifistaModelPacket;
import net.warcar.non_fruit_rework.network.packets.client.CSpawnSeraphimModelPacket;
import net.warcar.non_fruit_rework.network.packets.server.SOpenVegapunkMenuPacket;

public class ModPackets {
    public static void init() {
        ModNetwork.registerPacket(new SOpenVegapunkMenuPacket());
        ModNetwork.registerPacket(new CSpawnPacifistaModelPacket());
        ModNetwork.registerPacket(new CSpawnSeraphimModelPacket());
    }
}
