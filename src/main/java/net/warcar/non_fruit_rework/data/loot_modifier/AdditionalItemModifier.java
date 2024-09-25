package net.warcar.non_fruit_rework.data.loot_modifier;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class AdditionalItemModifier extends LootModifier {
    private final ItemStack additionalItem;

    public AdditionalItemModifier(ILootCondition[] conditions, ItemStack additionalItem) {
        super(conditions);
        this.additionalItem = additionalItem;
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(additionalItem.copy());
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AdditionalItemModifier> {
        @Override
        public AdditionalItemModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            JsonObject itemObject = JSONUtils.getAsJsonObject(object, "item");

            Item additionalItem = JSONUtils.getAsItem(itemObject, "name");
            ItemStack itemStack = new ItemStack(additionalItem);
            if (itemObject.has("nbt")) {
                try {
                    CompoundNBT nbt = JsonToNBT.parseTag(JSONUtils.convertToString(itemObject.get("nbt"), "nbt"));
                    itemStack.setTag(nbt);
                } catch (CommandSyntaxException commandsyntaxexception) {
                    throw new JsonSyntaxException("Invalid nbt tag: " + commandsyntaxexception.getMessage());
                }
            }

            return new AdditionalItemModifier(conditions, itemStack);
        }

        @Override
        public JsonObject write(AdditionalItemModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            JsonObject itemObject = new JsonObject();

            itemObject.addProperty("name", ForgeRegistries.ITEMS.getKey(instance.additionalItem.getItem()).toString());
            if (instance.additionalItem.hasTag()) {
                itemObject.addProperty("nbt", instance.additionalItem.getTag().toString());
            }

            json.add("item", itemObject);
            return json;
        }

    }
}
