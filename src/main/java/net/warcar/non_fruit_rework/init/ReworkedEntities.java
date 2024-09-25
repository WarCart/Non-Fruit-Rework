package net.warcar.non_fruit_rework.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.warcar.non_fruit_rework.entities.mobs.trainers.CP9Trainer;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.humanoids.HumanoidModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.HumanoidRenderer;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.function.Supplier;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReworkedEntities {
    public static final RegistryObject<EntityType<CP9Trainer>> ROKUSHIKI_TRAINER = registerFactionlessWithSpawnEgg("Rokushiki Trainer", () -> WyRegistry.createEntityType(CP9Trainer::new).build("mineminenomi:rokushiki_trainer"));

    private static <T extends Entity> RegistryObject<EntityType<T>> registerFactionlessWithSpawnEgg(String name, Supplier<EntityType<T>> supp) {
        RegistryObject<EntityType<T>> reg = WyRegistry.registerEntityType(name, supp);
        WyRegistry.registerSpawnEggItem(name, () -> new ForgeSpawnEggItem(reg, WyHelper.hexToRGB("#fbbf4c").getRGB(), WyHelper.hexToRGB("#F7F7F7").getRGB(), (new Item.Properties()).tab(ItemGroup.TAB_MISC)));
        return reg;
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ROKUSHIKI_TRAINER.get(), CP9Trainer.createAttributes().build());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ROKUSHIKI_TRAINER.get(), new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));
    }
}
