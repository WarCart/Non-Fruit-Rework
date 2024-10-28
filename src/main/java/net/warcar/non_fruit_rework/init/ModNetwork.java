package net.warcar.non_fruit_rework.init;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.packets.combo.SComboActivateAnim;
import net.warcar.non_fruit_rework.packets.combo.SSetComboHasCaller;
import net.warcar.non_fruit_rework.packets.combo.SSetComboHasTarget;

public class ModNetwork {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(NonFruitReworkMod.MOD_ID, "main_channel"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
        INSTANCE.registerMessage(0, SSetComboHasTarget.class, SSetComboHasTarget::encode, SSetComboHasTarget::decode, SSetComboHasTarget::handle);
        INSTANCE.registerMessage(1, SSetComboHasCaller.class, SSetComboHasCaller::encode, SSetComboHasCaller::decode, SSetComboHasCaller::handle);
        INSTANCE.registerMessage(2, SComboActivateAnim.class, SComboActivateAnim::encode, SComboActivateAnim::decode, SComboActivateAnim::handle);
    }

    public static <MSG> void sendTo(MSG msg, PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), msg);
        }
    }

    public static <MSG> void sendToAllTrackingAndSelf(MSG msg, LivingEntity tracked) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> {
            return tracked;
        }), msg);
    }
}
