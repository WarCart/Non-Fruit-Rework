package net.warcar.non_fruit_rework.init;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.abilities.components.ComboAbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;

public class ReworkedComponents {
    public static final AbilityComponentKey<ComboAbilityComponent> COMBO = register("combo");

    public static void init() {
    }

    private static <C extends AbilityComponent<?>> AbilityComponentKey<C> register(String name) {
        return AbilityComponentKey.key(new ResourceLocation(NonFruitReworkMod.MOD_ID, name));
    }
}
