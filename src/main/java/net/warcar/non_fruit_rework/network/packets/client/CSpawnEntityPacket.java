package net.warcar.non_fruit_rework.network.packets.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.warcar.non_fruit_rework.network.packets.IPacket;
import xyz.pixelatedw.mineminenomi.abilities.CommandAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.entities.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import java.util.function.Supplier;

public class CSpawnEntityPacket implements IPacket<CSpawnEntityPacket> {
    private ResourceLocation type;

    public CSpawnEntityPacket(ResourceLocation type) {
        this.type = type;
    }

    public CSpawnEntityPacket() {}

    public void encode(PacketBuffer buffer) {
        buffer.writeResourceLocation(type);
    }

    public CSpawnEntityPacket decode(PacketBuffer buffer) {
        CSpawnEntityPacket packet = new CSpawnEntityPacket();
        packet.type = buffer.readResourceLocation();
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                Entity spawnedPacifista = ForgeRegistries.ENTITIES.getValue(type).spawn(player.getLevel(), null, null, null, player.blockPosition(), SpawnReason.MOB_SUMMONED, false, false);
                if (spawnedPacifista instanceof LivingEntity) {
                    IEntityStats playerStats = EntityStatsCapability.get(player);
                    IEntityStats pacifistaStats = EntityStatsCapability.get((LivingEntity) spawnedPacifista);
                    pacifistaStats.setFaction(playerStats.getFaction());
                    if (pacifistaStats.isPirate() && ExtendedWorldData.get().getCrewWithMember(player.getUUID()) != null) {
                        ExtendedWorldData.get().getCrewWithMember(player.getUUID()).addMember((LivingEntity) spawnedPacifista, true);
                    }
                }
                player.level.addFreshEntity(spawnedPacifista);
                if (spawnedPacifista instanceof ICommandReceiver) {
                    ((ICommandReceiver) spawnedPacifista).setCurrentCommand(player, NPCCommand.FOLLOW);
                    IAbilityData abilityData = AbilityDataCapability.get(player);
                    abilityData.addUnlockedAbility(CommandAbility.INSTANCE, AbilityUnlock.PROGRESSION);
                    WyNetwork.sendTo(new SSyncAbilityDataPacket(player.getId(), abilityData), player);
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
