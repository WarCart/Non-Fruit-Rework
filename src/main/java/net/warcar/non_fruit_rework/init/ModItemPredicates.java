package net.warcar.non_fruit_rework.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.MiscHelper;

@Mod.EventBusSubscriber
public class ModItemPredicates {
    private static final IItemPropertyGetter BLOOD_TYPE = (stack, world, entity) -> {
        switch (stack.getOrCreateTagElement("genetics").getString("blood")) {
            case "human":
                return 1.0f;
            case "green":
                return 2.0f;
            case "failed":
                return 3.0f;
        }
        if (!MiscHelper.nullOrEmpty(stack.getTagElement("potion"))) {
            return 4.0f;
        }
        return 0.0f;
    };

    private static final IItemColor POTION_COLOR = (stack, index) -> {
        if (index == 1 && !MiscHelper.nullOrEmpty(stack.getTagElement("potion")) && stack.getOrCreateTagElement("genetics").getString("blood").isEmpty()) {
            return PotionUtils.getColor(PotionUtils.getAllEffects(stack.getOrCreateTagElement("potion")));
        }
        return -1;
    };

    public static void register() {
        ItemModelsProperties.register(ModItems.SYRINGE.get(), new ResourceLocation(NonFruitReworkMod.MOD_ID, "blood_type"), BLOOD_TYPE);
        ItemColors colors = Minecraft.getInstance().getItemColors();
        colors.register(POTION_COLOR, ModItems.SYRINGE.get());
    }
}
