package net.warcar.non_fruit_rework.network.packets.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.data.entity.medical_data.IMedicalData;
import net.warcar.non_fruit_rework.data.entity.medical_data.MedicalDataCapability;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import net.warcar.non_fruit_rework.network.packets.server.SSyncMedicalDataPacket;
import xyz.pixelatedw.mineminenomi.events.abilities.AbilityProgressionEvents;

import java.util.function.Supplier;

public class CSyncMedicalDataPacket implements IPacket<CSyncMedicalDataPacket> {
    private int entityId;
    private CompoundNBT data;

    public CSyncMedicalDataPacket() {
    }

    public CSyncMedicalDataPacket(int entityId, IMedicalData stats) {
        this.entityId = entityId;
        this.data = (CompoundNBT) MedicalDataCapability.INSTANCE.writeNBT(stats, null);
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(entityId);
        buffer.writeNbt(this.data);
    }

    @Override
    public CSyncMedicalDataPacket decode(PacketBuffer buffer) {
        CSyncMedicalDataPacket packet = new CSyncMedicalDataPacket();
        packet.entityId = buffer.readInt();
        packet.data = buffer.readAnySizeNbt();
        return packet;
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                LivingEntity entity = (LivingEntity) ctx.get().getSender().level.getEntity(this.entityId);
                IMedicalData props = MedicalDataCapability.get(entity);
                MedicalDataCapability.INSTANCE.readNBT(props, null, this.data);
                ModNetwork.sendToAllTracking(new SSyncMedicalDataPacket(this.entityId, props), entity);
                if (entity instanceof PlayerEntity) {
                    AbilityProgressionEvents.checkAllForNewUnlocks((PlayerEntity) entity);
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
