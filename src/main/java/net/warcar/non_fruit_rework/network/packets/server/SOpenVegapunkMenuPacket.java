package net.warcar.non_fruit_rework.network.packets.server;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.entities.quests.VegapunkEntity;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import net.warcar.non_fruit_rework.screens.VegapunkScreen;

import java.util.function.Supplier;

public class SOpenVegapunkMenuPacket implements IPacket<SOpenVegapunkMenuPacket> {
    private int id;

    public SOpenVegapunkMenuPacket(int id) {
        this.id = id;
    }

    public SOpenVegapunkMenuPacket() {
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(id);
    }

    public SOpenVegapunkMenuPacket decode(PacketBuffer buffer) {
        SOpenVegapunkMenuPacket packet = new SOpenVegapunkMenuPacket();
        packet.id = buffer.readInt();
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> ClientHandler.handle(this));
        }
        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler {
        @OnlyIn(Dist.CLIENT)
        public static void handle(SOpenVegapunkMenuPacket message) {
            PlayerEntity player = Minecraft.getInstance().player;
            VegapunkEntity questGiver = (VegapunkEntity) Minecraft.getInstance().level.getEntity(message.id);
            Minecraft.getInstance().setScreen(new VegapunkScreen(player, questGiver));
        }
    }
}
