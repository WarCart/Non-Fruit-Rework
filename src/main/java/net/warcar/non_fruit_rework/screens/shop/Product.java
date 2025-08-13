package net.warcar.non_fruit_rework.screens.shop;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;

public abstract class Product {
    private final long price;

    public Product(int price) {
        this.price = price;
    }

    public abstract ITextComponent getName();

    public void drawIcon(MatrixStack matrixStack, int x, int y) {
    }

    public long getPrice() {
        return price;
    }

    protected abstract void onBought();

    public boolean buy(IEntityStats stats) {
        if (stats.getBelly() >= price) {
            stats.alterBelly(-price, StatChangeSource.STORE);
            onBought();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Product [price=" + price + ", name="+ getName().getString() + "]";
    }
}
