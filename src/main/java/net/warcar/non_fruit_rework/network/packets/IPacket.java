package net.warcar.non_fruit_rework.network.packets;

import com.google.common.reflect.TypeToken;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket<SELF extends IPacket<SELF>> {
    default Class<SELF> getPacketClass() {
        return (Class<SELF>) TypeToken.of(this.getClass()).getRawType();
    }

    void encode(PacketBuffer buffer);
    //Should be treated as static
    SELF decode(PacketBuffer buffer);

    void handle(Supplier<NetworkEvent.Context> ctx);
}
