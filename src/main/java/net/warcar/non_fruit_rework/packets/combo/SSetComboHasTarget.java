package net.warcar.non_fruit_rework.packets.combo;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.warcar.non_fruit_rework.init.ReworkedComponents;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

import java.util.function.Supplier;

public class SSetComboHasTarget {
    private int entityId;
    private int abilityId;
    private boolean state;

    public SSetComboHasTarget() {
    }

    public SSetComboHasTarget(LivingEntity entity, IAbility ability, boolean state) {
        this.entityId = entity.getId();
        this.abilityId = AbilityDataCapability.get(entity).getEquippedAbilitySlot(ability);
        this.state = state;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.abilityId);
        buffer.writeBoolean(this.state);
    }

    public static SSetComboHasTarget decode(PacketBuffer buffer) {
        SSetComboHasTarget packet = new SSetComboHasTarget();
        packet.entityId = buffer.readInt();
        packet.abilityId = buffer.readInt();
        packet.state = buffer.readBoolean();
        return packet;
    }

    public static void handle(SSetComboHasTarget message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> SSetComboHasTarget.ClientHandler.handle(message));
        }

        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler {
        public ClientHandler() {
        }

        @OnlyIn(Dist.CLIENT)
        public static void handle(SSetComboHasTarget message) {
            Entity target = Minecraft.getInstance().level.getEntity(message.entityId);
            if (target instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity)target;
                IAbilityData props = AbilityDataCapability.get(entity);
                IAbility abl = props.getEquippedAbility(message.abilityId);
                if (abl != null) {
                    abl.getComponent(ReworkedComponents.COMBO).ifPresent((c) -> {
                        c.setClientHaveTarget(message.state);
                    });
                }
            }
        }
    }
}
