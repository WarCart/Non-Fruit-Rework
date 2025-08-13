package net.warcar.non_fruit_rework.screens.shop;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.ITextComponent;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.CSpawnEntityPacket;

public class EntityProduct extends Product {
    private final EntityType<?> type;

    public EntityProduct(int price, EntityType<?> type) {
        super(price);
        this.type = type;
    }

    @Override
    public ITextComponent getName() {
        return type.getDescription();
    }

    @Override
    protected void onBought() {
        ModNetwork.sendToServer(new CSpawnEntityPacket(type.getRegistryName()));
        Minecraft.getInstance().setScreen(null);
    }
}
