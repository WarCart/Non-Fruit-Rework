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
import net.warcar.non_fruit_rework.entities.AfterimageEntity;
import net.warcar.non_fruit_rework.entities.bosses.HodyJonesBoss;
import net.warcar.non_fruit_rework.entities.bosses.InuarashiBoss;
import net.warcar.non_fruit_rework.entities.bosses.NekomamushiBoss;
import net.warcar.non_fruit_rework.entities.quests.CP9Trainer;
import net.warcar.non_fruit_rework.entities.quests.ElectroTrainer;
import net.warcar.non_fruit_rework.entities.quests.FishmanTrainer;
import net.warcar.non_fruit_rework.entities.quests.VegapunkEntity;
import net.warcar.non_fruit_rework.entities.seraphim.SHawkEntity;
import net.warcar.non_fruit_rework.entities.seraphim.SeraphimEntity;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import net.warcar.non_fruit_rework.models.HackModel;
import net.warcar.non_fruit_rework.models.VegapunkModel;
import net.warcar.non_fruit_rework.renderers.AfterimageRenderer;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.humanoids.HumanoidModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.HumanoidRenderer;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, NonFruitReworkMod.MOD_ID);

    public static final List<EntityType<? extends SeraphimEntity>> SERAPHIMS = new ArrayList<>();

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);

        //Quest givers
        registerFactionlessWithSpawnEgg("Vegapunk", VegapunkEntity.INSTANCE);
        registerFactionlessWithSpawnEgg("CP9 Trainer", CP9Trainer.INSTANCE);
        registerFactionlessWithSpawnEgg("Fishman Trainer", FishmanTrainer.INSTANCE);
        registerFactionlessWithSpawnEgg("Electro Trainer", ElectroTrainer.INSTANCE);

        //Seraphims
        registerSeraphim("S-Hawk", SHawkEntity.INSTANCE);

        //Bosses
        registerEntity("Nekomamushi", NekomamushiBoss.INSTANCE);
        registerEntity("Inuarashi", InuarashiBoss.INSTANCE);
        registerEntity("Hody Jones", HodyJonesBoss.INSTANCE);

        //Misc
        registerEntity("Afterimage", AfterimageEntity.INSTANCE);
    }

    private static <T extends SeraphimEntity> void registerSeraphim(String name, EntityType<T> type) {
        /*RegistryObject<EntityType<T>> reg = registerEntity(name, type);
        ModItems.registerSpawnEggItem(name, () -> new ForgeSpawnEggItem(reg, WyHelper.hexToRGB("#272727").getRGB(), WyHelper.hexToRGB("#ff0000").getRGB(), (new Item.Properties()).tab(ItemGroup.TAB_MISC)));
        SERAPHIMS.add(type);*/
    }

    public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType<T> type) {
        RegistryObject<EntityType<T>> reg = ENTITIES.register(WyHelper.getResourceName(name), () -> type);
        LangHelper.registerLine(String.format("entity.%s.%s", reg.getId().getNamespace(), reg.getId().getPath()), name);
        return reg;
    }

    private static <T extends Entity> void registerFactionlessWithSpawnEgg(String name, EntityType<T> type) {
        RegistryObject<EntityType<T>> reg = registerEntity(name, type);
        ModItems.registerSpawnEggItem(name, () -> new ForgeSpawnEggItem(reg, WyHelper.hexToRGB("#5bffdc").getRGB(), WyHelper.hexToRGB("#F7F7F7").getRGB(), (new Item.Properties()).tab(ItemGroup.TAB_MISC)));
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        //Quest givers
        event.put(VegapunkEntity.INSTANCE, VegapunkEntity.createAttributes().build());
        event.put(CP9Trainer.INSTANCE, CP9Trainer.createAttributes().build());
        event.put(FishmanTrainer.INSTANCE, FishmanTrainer.createAttributes().build());
        event.put(ElectroTrainer.INSTANCE, ElectroTrainer.createAttributes().build());

        //Seraphims
        event.put(SHawkEntity.INSTANCE, SeraphimEntity.createAttributes().build());

        //Bosses
        event.put(NekomamushiBoss.INSTANCE, NekomamushiBoss.createAttributes().build());
        event.put(InuarashiBoss.INSTANCE, InuarashiBoss.createAttributes().build());
        event.put(HodyJonesBoss.INSTANCE, HodyJonesBoss.createAttributes().build());

        //Misc
        event.put(AfterimageEntity.INSTANCE, OPEntity.createAttributes().build());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event) {
        //Quest givers
        RenderingRegistry.registerEntityRenderingHandler(VegapunkEntity.INSTANCE, new HumanoidRenderer.Factory(new VegapunkModel(), 1));
        RenderingRegistry.registerEntityRenderingHandler(CP9Trainer.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));
        RenderingRegistry.registerEntityRenderingHandler(FishmanTrainer.INSTANCE, new HumanoidRenderer.Factory(new HackModel(), 1));
        RenderingRegistry.registerEntityRenderingHandler(ElectroTrainer.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));

        //Seraphims
        RenderingRegistry.registerEntityRenderingHandler(SHawkEntity.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));

        //Bosses
        RenderingRegistry.registerEntityRenderingHandler(NekomamushiBoss.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1, "nekomamushi"));
        RenderingRegistry.registerEntityRenderingHandler(InuarashiBoss.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1, "inuarashi"));
        RenderingRegistry.registerEntityRenderingHandler(HodyJonesBoss.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1, "hody_jones"));

        //Misc
        RenderingRegistry.registerEntityRenderingHandler(AfterimageEntity.INSTANCE, new AfterimageRenderer.Factory());
    }
}
