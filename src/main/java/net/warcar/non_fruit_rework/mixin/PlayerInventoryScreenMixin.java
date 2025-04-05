package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraftforge.items.SlotItemHandler;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerContainer.class)
public abstract class PlayerInventoryScreenMixin extends RecipeBookContainer<CraftingInventory> {
    private PlayerInventoryScreenMixin() {
        super(null, 0);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void addSlots(PlayerInventory inventory, boolean active, PlayerEntity player, CallbackInfo ci) {
        INonFruitData data = NonFruitDataCapability.get(player);
        this.addSlot(new SlotItemHandler(data.getAdditionalInventory(), 0, 76, 8));
    }
}
