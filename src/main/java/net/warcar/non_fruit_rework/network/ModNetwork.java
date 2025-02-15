package net.warcar.non_fruit_rework.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.network.packets.IPacket;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModNetwork {
    private static int packet = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(NonFruitReworkMod.MOD_ID, "main_channel"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static <MSG> void registerPacket(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(packet++, messageType, encoder, decoder, messageConsumer);
    }

    public static <MSG extends IPacket<MSG>> void registerPacket(IPacket<MSG> packetInstance) {
        INSTANCE.registerMessage(packet++, packetInstance.getPacketClass(), IPacket::encode, packetInstance::decode, IPacket::handle);
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendTo(MSG msg, PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), msg);
        }
    }

    public static <MSG> void sendToAll(MSG msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static <MSG> void sendToAllTracking(MSG msg, Entity tracked) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> {
            return tracked;
        }), msg);
    }

    public static <MSG> void sendToAllTrackingAndSelf(MSG msg, Entity tracked) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> {
            return tracked;
        }), msg);
    }

    public static <MSG> void sendToAllAround(MSG msg, Entity sender) {
        sendToAllAroundDistance(msg, sender.level, sender.position(), 256);
    }

    public static <MSG> void sendToAllAroundDistance(MSG msg, World world, Vector3d pivot, int distance) {
        INSTANCE.send(PacketDistributor.NEAR.with(() -> {
            return new PacketDistributor.TargetPoint(pivot.x(), pivot.y(), pivot.z(), (double)distance, world.dimension());
        }), msg);
    }
}
