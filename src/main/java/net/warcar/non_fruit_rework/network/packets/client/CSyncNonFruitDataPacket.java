package net.warcar.non_fruit_rework.network.packets.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import net.warcar.non_fruit_rework.network.packets.server.SSyncNonFruitDataPacket;
import xyz.pixelatedw.mineminenomi.events.abilities.AbilityProgressionEvents;

import java.util.function.Supplier;

public class CSyncNonFruitDataPacket implements IPacket<CSyncNonFruitDataPacket> {
    private int entityId;
    private CompoundNBT data;

    public CSyncNonFruitDataPacket() {
    }

    public CSyncNonFruitDataPacket(int entityId, INonFruitData stats) {
        this.entityId = entityId;
        this.data = (CompoundNBT) NonFruitDataCapability.INSTANCE.writeNBT(stats, null);
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(entityId);
        buffer.writeNbt(this.data);
    }

    @Override
    public CSyncNonFruitDataPacket decode(PacketBuffer buffer) {
        CSyncNonFruitDataPacket packet = new CSyncNonFruitDataPacket();
        packet.entityId = buffer.readInt();
        packet.data = buffer.readAnySizeNbt();
        return packet;
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                LivingEntity entity = (LivingEntity) ctx.get().getSender().level.getEntity(this.entityId);
                INonFruitData props = NonFruitDataCapability.get(entity);
                NonFruitDataCapability.INSTANCE.readNBT(props, null, this.data);
                ModNetwork.sendToAllTracking(new SSyncNonFruitDataPacket(this.entityId, props), entity);
                if (entity instanceof PlayerEntity) {
                    AbilityProgressionEvents.checkAllForNewUnlocks((PlayerEntity) entity);
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
