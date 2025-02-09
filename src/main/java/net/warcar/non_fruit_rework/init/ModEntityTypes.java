package net.warcar.non_fruit_rework.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.entities.quests.CP9Trainer;
import net.warcar.non_fruit_rework.entities.quests.VegapunkEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.humanoids.HumanoidModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.HumanoidRenderer;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, NonFruitReworkMod.MOD_ID);

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
        registerFactionlessWithSpawnEgg("Vegapunk", VegapunkEntity.INSTANCE);
        registerFactionlessWithSpawnEgg("CP9 Trainer", CP9Trainer.INSTANCE);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerFactionlessWithSpawnEgg(String name, EntityType<T> type) {
        RegistryObject<EntityType<T>> reg = ENTITIES.register(WyHelper.getResourceName(name), () -> type);
        WyRegistry.registerSpawnEggItem(name, () -> new ForgeSpawnEggItem(reg, WyHelper.hexToRGB("#fbbf4c").getRGB(), WyHelper.hexToRGB("#F7F7F7").getRGB(), (new Item.Properties()).tab(ItemGroup.TAB_MISC)));
        return reg;
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(VegapunkEntity.INSTANCE, VegapunkEntity.createAttributes().build());
        event.put(CP9Trainer.INSTANCE, CP9Trainer.createAttributes().build());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(VegapunkEntity.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));
        RenderingRegistry.registerEntityRenderingHandler(CP9Trainer.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));
    }
}
