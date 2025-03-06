package net.warcar.non_fruit_rework.network.packets.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdatePassiveAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import java.util.function.Supplier;

public class CUpdatePassiveAbilityDataPacket implements IPacket<CUpdatePassiveAbilityDataPacket> {
    private int entityId;
    private ResourceLocation abilityId;
    private CompoundNBT nbtData;

    public CUpdatePassiveAbilityDataPacket() {
    }

    public CUpdatePassiveAbilityDataPacket(LivingEntity entity, IAbility ability) {
        this.entityId = entity.getId();
        this.abilityId = ability.getCore().getRegistryName();
        this.nbtData = ability.save(new CompoundNBT());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeResourceLocation(this.abilityId);
        buffer.writeNbt(this.nbtData);
    }

    public CUpdatePassiveAbilityDataPacket decode(PacketBuffer buffer) {
        CUpdatePassiveAbilityDataPacket msg = new CUpdatePassiveAbilityDataPacket();
        msg.entityId = buffer.readInt();
        msg.abilityId = buffer.readResourceLocation();
        msg.nbtData = buffer.readNbt();
        return msg;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                LivingEntity entity = (LivingEntity) ctx.get().getSender().level.getEntity(this.entityId);
                IAbility ability = AbilityDataCapability.get(entity).getPassiveAbility(ModRegistries.ABILITIES.getValue(this.abilityId));
                if (ability != null) {
                    ability.load(this.nbtData);
                    WyNetwork.sendToAllTracking(new SUpdatePassiveAbilityDataPacket(entity, ability), entity);
                }
            });
        }

        ctx.get().setPacketHandled(true);
    }
}