package net.warcar.non_fruit_rework.screens.shop;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.warcar.non_fruit_rework.enums.PacifistaModel;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.client.CSpawnPacifistaModelPacket;

public class PacifistaProduct extends Product {
    private final PacifistaModel model;

    public PacifistaProduct(PacifistaModel model) {
        super(model.getPrice());
        this.model = model;
    }

    @Override
    public ITextComponent getName() {
        return model.getLocalizedName();
    }

    @Override
    protected void onBought() {
        ModNetwork.sendToServer(new CSpawnPacifistaModelPacket(model));
        Minecraft.getInstance().setScreen(null);
    }
}
