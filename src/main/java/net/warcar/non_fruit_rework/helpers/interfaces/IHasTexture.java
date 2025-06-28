package net.warcar.non_fruit_rework.helpers.interfaces;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.NonFruitReworkMod;

public interface IHasTexture {
    ResourceLocation getTexture();

    static ResourceLocation getAbilityTexture(String name) {
        if (name == null) {
            return null;
        }
        return new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/abilities/" + name + ".png");
    }
}
