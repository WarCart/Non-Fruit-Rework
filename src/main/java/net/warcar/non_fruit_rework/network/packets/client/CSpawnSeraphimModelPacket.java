package net.warcar.non_fruit_rework.network.packets.client;

import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.entities.seraphim.SeraphimEntity;
import net.warcar.non_fruit_rework.init.ModEntityTypes;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import java.util.function.Supplier;

public class CSpawnSeraphimModelPacket implements IPacket<CSpawnSeraphimModelPacket> {
    private int model;
    private long price;

    public CSpawnSeraphimModelPacket(int model, long price) {
        this.model = model;
        this.price = price;
    }

    public CSpawnSeraphimModelPacket() {}

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(model);
        buffer.writeLong(price);
    }

    public CSpawnSeraphimModelPacket decode(PacketBuffer buffer) {
        CSpawnSeraphimModelPacket packet = new CSpawnSeraphimModelPacket();
        packet.model = buffer.readInt();
        packet.price = buffer.readLong();
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                SeraphimEntity spawnedPacifista = ModEntityTypes.SERAPHIMS.get(this.model).spawn(player.getLevel(), null, null, null, player.blockPosition(), SpawnReason.MOB_SUMMONED, false, false);
                IEntityStats playerStats = EntityStatsCapability.get(player);
                IEntityStats pacifistaStats = spawnedPacifista.getEntityStats();
                pacifistaStats.setFaction(playerStats.getFaction());
                if (pacifistaStats.isPirate() && ExtendedWorldData.get().getCrewWithMember(player.getUUID()) != null) {
                    ExtendedWorldData.get().getCrewWithMember(player.getUUID()).addMember(spawnedPacifista, true);
                }
                spawnedPacifista.setCurrentCommand(player, NPCCommand.FOLLOW);
                player.level.addFreshEntity(spawnedPacifista);
                playerStats.alterBelly(-this.price, StatChangeSource.STORE);
                WyNetwork.sendTo(new SSyncEntityStatsPacket(player.getId(), playerStats), player);
                IAbilityData abilityData = AbilityDataCapability.get(player);
                abilityData.addUnlockedAbility(CommandAbility.INSTANCE, AbilityUnlock.PROGRESSION);
                WyNetwork.sendTo(new SSyncAbilityDataPacket(player.getId(), abilityData), player);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
