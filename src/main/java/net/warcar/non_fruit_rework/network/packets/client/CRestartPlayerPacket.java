package net.warcar.non_fruit_rework.network.packets.client;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.network.packets.IPacket;

import java.util.function.Supplier;

public class CRestartPlayerPacket implements IPacket<CRestartPlayerPacket> {
    @Override
    public void encode(PacketBuffer buffer) {
    }

    @Override
    public CRestartPlayerPacket decode(PacketBuffer buffer) {
        return new CRestartPlayerPacket();
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            QuestHelper.restartPlayer(ctx.get().getSender());
        });
    }
}
