package net.warcar.non_fruit_rework.init;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, NonFruitReworkMod.MOD_ID);

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
