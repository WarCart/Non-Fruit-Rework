package net.warcar.non_fruit_rework.screens.shop;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class BasicItemProduct extends Product {
    private Supplier<ItemStack> item;

    public BasicItemProduct(int price, Supplier<ItemStack> itemStack) {
        super(price);
        this.item = itemStack;
    }

    public BasicItemProduct(int price, ItemStack itemStack) {
        this(price, () -> itemStack);
    }

    public BasicItemProduct(int price, RegistryObject<? extends Item> item) {
        this(price, () -> new ItemStack(item.get()));
    }

    public BasicItemProduct(int price, Item item) {
        this(price, () -> new ItemStack(item));
    }

    @Override
    public ITextComponent getName() {
        return item.get().getHoverName();
    }

    @Override
    protected void onBought() {
        Minecraft.getInstance().player.inventory.add(getStack());
    }

    protected ItemStack getStack() {
        return this.item.get();
    }

    @Override
    public void drawIcon(MatrixStack matrixStack, int x, int y) {
        ItemStack itemStack = getStack();
        Minecraft.getInstance().getItemRenderer().renderGuiItem(itemStack, x, y);
    }
}
