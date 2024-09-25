package net.warcar.non_fruit_rework.init;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.functions.RemoveSelfFunction;

public class ReworkedLootTypes {
    public static final LootFunctionType RESET = registerFunction("self_remove_challenge", new RemoveSelfFunction.Serializer());

    public static void init() {

    }

    private static LootFunctionType registerFunction(String id, ILootSerializer<? extends ILootFunction> serializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(NonFruitReworkMod.MOD_ID, id), new LootFunctionType(serializer));
    }
}
