package net.warcar.non_fruit_rework.network.packets.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import java.util.function.Supplier;

public class CSyncEntityStatsPacket implements IPacket<CSyncEntityStatsPacket> {
    private int entityId;
    private CompoundNBT data;

    public CSyncEntityStatsPacket() {
    }

    public CSyncEntityStatsPacket(int entityId, IEntityStats stats) {
        this.entityId = entityId;
        this.data = (CompoundNBT) EntityStatsCapability.INSTANCE.writeNBT(stats, null);
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(entityId);
        buffer.writeNbt(data);
    }

    @Override
    public CSyncEntityStatsPacket decode(PacketBuffer buffer) {
        CSyncEntityStatsPacket packet = new CSyncEntityStatsPacket();
        packet.entityId = buffer.readInt();
        packet.data = buffer.readNbt();
        return packet;
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                LivingEntity entity = (LivingEntity) ctx.get().getSender().level.getEntity(this.entityId);
                IEntityStats props = EntityStatsCapability.get(entity);
                EntityStatsCapability.INSTANCE.getStorage().readNBT(EntityStatsCapability.INSTANCE, props, null, this.data);
                WyNetwork.sendToAllTracking(new SSyncEntityStatsPacket(this.entityId, props), entity);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
