package net.warcar.non_fruit_rework.network.packets.server;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.network.packets.IPacket;

import java.util.function.Supplier;

public class SSyncNonFruitDataPacket implements IPacket<SSyncNonFruitDataPacket> {
    private int entityId;
    private CompoundNBT data;

    public SSyncNonFruitDataPacket() {
    }

    public SSyncNonFruitDataPacket(int entityId, INonFruitData stats) {
        this.entityId = entityId;
        this.data = (CompoundNBT) NonFruitDataCapability.INSTANCE.writeNBT(stats, null);
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeNbt(this.data);
    }

    public SSyncNonFruitDataPacket decode(PacketBuffer buffer) {
        SSyncNonFruitDataPacket msg = new SSyncNonFruitDataPacket();
        msg.entityId = buffer.readInt();
        msg.data = buffer.readNbt();
        return msg;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> ClientHandler.handle(this));
        }

        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler {
        public ClientHandler() {
        }

        @OnlyIn(Dist.CLIENT)
        public static void handle(SSyncNonFruitDataPacket message) {
            Entity target = Minecraft.getInstance().level.getEntity(message.entityId);
            if (target != null && target instanceof LivingEntity) {
                INonFruitData props = NonFruitDataCapability.get((LivingEntity)target);
                NonFruitDataCapability.INSTANCE.readNBT(props, null, message.data);
            }
        }
    }
}