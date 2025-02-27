package net.warcar.non_fruit_rework.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import net.warcar.non_fruit_rework.items.EnergySteroidBatchItem;
import net.warcar.non_fruit_rework.items.SimplePillItem;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, NonFruitReworkMod.MOD_ID);

    public static final RegistryObject<SimplePillItem> SULONG_BALL = registerItem("Sulong Ball", () -> new SimplePillItem(new Item.Properties().tab(ItemGroup.TAB_BREWING), SimplePillItem.PillEffect.SULONG_BALL));
    public static final RegistryObject<SimplePillItem> ENERGY_STEROID = registerItem("Energy Steroid", () -> new SimplePillItem(new Item.Properties().tab(ItemGroup.TAB_BREWING), SimplePillItem.PillEffect.ENERGY_STEROID));
    public static final RegistryObject<EnergySteroidBatchItem> ENERGY_STEROID_BATCH = registerItem("Energy Steroid Batch", EnergySteroidBatchItem::new);
    public static final RegistryObject<SimplePillItem> RUMBLE_BALL = registerItem("Rumble Ball", () -> new SimplePillItem(new Item.Properties().tab(ItemGroup.TAB_BREWING), SimplePillItem.PillEffect.RUMBLE_BALL));

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        String resName = WyHelper.getResourceName(name);
        RegistryObject<T> reg = ITEMS.register(resName, item);
        ResourceLocation id = reg.getId();
        LangHelper.registerLine(String.format("item.%s.%s", id.getNamespace(), id.getPath()), name);
        return reg;
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static RegistryObject<ForgeSpawnEggItem> registerSpawnEggItem(String localizedEntityName, Supplier<ForgeSpawnEggItem> supp) {
        String resourceName = WyHelper.getResourceName(localizedEntityName) + "_spawn_egg";
        String localizedName = "Spawn " + localizedEntityName;
        RegistryObject<ForgeSpawnEggItem> register = ITEMS.register(resourceName, supp);
        ResourceLocation id = register.getId();
        LangHelper.registerLine(String.format("item.%s.%s", id.getNamespace(), id.getPath()), localizedName);
        return register;
    }
}
