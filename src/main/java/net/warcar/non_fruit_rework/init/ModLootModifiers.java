package net.warcar.non_fruit_rework.init;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.loot_modifier.AdditionalItemModifier;

public class ModLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, NonFruitReworkMod.MOD_ID);

    public static final RegistryObject<GlobalLootModifierSerializer<?>> oneItem = LOOT_MODIFIER_SERIALIZERS.register("single_item", AdditionalItemModifier.Serializer::new);
}
