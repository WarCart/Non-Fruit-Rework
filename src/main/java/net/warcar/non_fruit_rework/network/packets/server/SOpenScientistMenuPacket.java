package net.warcar.non_fruit_rework.network.packets.server;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.entities.quests.mads.ScientistEntity;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import net.warcar.non_fruit_rework.screens.ScientistScreen;

import java.util.function.Supplier;

public class SOpenScientistMenuPacket implements IPacket<SOpenScientistMenuPacket> {
    private int id;

    public SOpenScientistMenuPacket(int id) {
        this.id = id;
    }

    public SOpenScientistMenuPacket() {
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(id);
    }

    public SOpenScientistMenuPacket decode(PacketBuffer buffer) {
        SOpenScientistMenuPacket packet = new SOpenScientistMenuPacket();
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
        public static void handle(SOpenScientistMenuPacket message) {
            PlayerEntity player = Minecraft.getInstance().player;
            ScientistEntity questGiver = (ScientistEntity) Minecraft.getInstance().level.getEntity(message.id);
            Minecraft.getInstance().setScreen(new ScientistScreen(player, questGiver));
        }
    }
}
