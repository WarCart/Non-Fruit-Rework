package net.warcar.non_fruit_rework.network.packets.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import java.util.function.Supplier;

public class CSyncAbilityDataPacket implements IPacket<CSyncAbilityDataPacket> {
    private int entityId;
    private CompoundNBT data;

    public CSyncAbilityDataPacket() {
    }

    public CSyncAbilityDataPacket(int entityId, IAbilityData stats) {
        this.entityId = entityId;
        this.data = (CompoundNBT) AbilityDataCapability.INSTANCE.writeNBT(stats, null);
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(entityId);
        buffer.writeNbt(data);
    }

    @Override
    public CSyncAbilityDataPacket decode(PacketBuffer buffer) {
        CSyncAbilityDataPacket packet = new CSyncAbilityDataPacket();
        packet.entityId = buffer.readInt();
        packet.data = buffer.readNbt();
        return packet;
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                LivingEntity entity = (LivingEntity) ctx.get().getSender().level.getEntity(this.entityId);
                IAbilityData props = AbilityDataCapability.get(entity);
                AbilityDataCapability.INSTANCE.getStorage().readNBT(AbilityDataCapability.INSTANCE, props, null, this.data);
                WyNetwork.sendToAllTracking(new SSyncAbilityDataPacket(this.entityId, props), entity);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
