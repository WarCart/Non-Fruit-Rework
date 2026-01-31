package net.warcar.non_fruit_rework.init;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.MrMagicalCart.cartaddon.models.entities.mobs.humanoids.SHawkModel;
import net.MrMagicalCart.cartaddon.models.morphs.LunarianClosedWingsModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
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
import net.warcar.non_fruit_rework.config.CommonConfig;
import net.warcar.non_fruit_rework.entities.AfterimageEntity;
import net.warcar.non_fruit_rework.entities.bosses.HodyJonesBoss;
import net.warcar.non_fruit_rework.entities.bosses.InuarashiBoss;
import net.warcar.non_fruit_rework.entities.bosses.NekomamushiBoss;
import net.warcar.non_fruit_rework.entities.quests.CP9Trainer;
import net.warcar.non_fruit_rework.entities.quests.ElectroTrainer;
import net.warcar.non_fruit_rework.entities.quests.FishmanTrainer;
import net.warcar.non_fruit_rework.entities.quests.mads.CaesarEntity;
import net.warcar.non_fruit_rework.entities.quests.mads.JudgeEntity;
import net.warcar.non_fruit_rework.entities.quests.mads.QueenEntity;
import net.warcar.non_fruit_rework.entities.quests.mads.VegapunkEntity;
import net.warcar.non_fruit_rework.entities.seraphim.SHawkEntity;
import net.warcar.non_fruit_rework.entities.seraphim.SeraphimEntity;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import net.warcar.non_fruit_rework.models.boss.HodyJonesModel;
import net.warcar.non_fruit_rework.models.boss.InuarashiModel;
import net.warcar.non_fruit_rework.models.boss.NekomamushiModel;
import net.warcar.non_fruit_rework.models.trainer.CP9TrainerModel;
import net.warcar.non_fruit_rework.models.trainer.ElectroTrainerModel;
import net.warcar.non_fruit_rework.models.trainer.HackModel;
import net.warcar.non_fruit_rework.models.trainer.VegapunkModel;
import net.warcar.non_fruit_rework.renderers.AfterimageRenderer;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
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
        registerFactionlessWithSpawnEgg("Judge", JudgeEntity.INSTANCE);
        registerFactionlessWithSpawnEgg("Caesar Clown", CaesarEntity.INSTANCE);
        registerFactionlessWithSpawnEgg("Queen", QueenEntity.INSTANCE);
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
        if (CommonConfig.INSTANCE.seraphims()) {
            RegistryObject<EntityType<T>> reg = registerEntity(name, type);
            ModItems.registerSpawnEggItem(name, () -> new ForgeSpawnEggItem(reg, WyHelper.hexToRGB("#272727").getRGB(), WyHelper.hexToRGB("#ff0000").getRGB(), (new Item.Properties()).tab(ItemGroup.TAB_MISC)));
            SERAPHIMS.add(type);
        } else {
            LangHelper.registerLine(String.format("entity.%s.%s", NonFruitReworkMod.MOD_ID, WyHelper.getResourceName(name)), name);
        }
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
        event.put(JudgeEntity.INSTANCE, JudgeEntity.createAttributes().build());
        event.put(CaesarEntity.INSTANCE, CaesarEntity.createAttributes().build());
        event.put(QueenEntity.INSTANCE, QueenEntity.createAttributes().build());
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
        RenderingRegistry.registerEntityRenderingHandler(JudgeEntity.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));
        RenderingRegistry.registerEntityRenderingHandler(CaesarEntity.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));
        RenderingRegistry.registerEntityRenderingHandler(QueenEntity.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));
        RenderingRegistry.registerEntityRenderingHandler(CP9Trainer.INSTANCE, new HumanoidRenderer.Factory(new CP9TrainerModel(), 1));
        RenderingRegistry.registerEntityRenderingHandler(FishmanTrainer.INSTANCE, new HumanoidRenderer.Factory(new HackModel(), 1));
        RenderingRegistry.registerEntityRenderingHandler(ElectroTrainer.INSTANCE, new HumanoidRenderer.Factory(new ElectroTrainerModel(), 1));

        //Seraphims
        if (NonFruitReworkMod.isCartaddonLoaded()) {
            registerSeraphimsCarts();
        } else {
            registerSeraphimsFallback();
        }

        //Bosses
        RenderingRegistry.registerEntityRenderingHandler(NekomamushiBoss.INSTANCE, new HumanoidRenderer.Factory(new NekomamushiModel(), 1, "nekomamushi"));
        RenderingRegistry.registerEntityRenderingHandler(InuarashiBoss.INSTANCE, new HumanoidRenderer.Factory(new InuarashiModel(), 1, "inuarashi"));
        RenderingRegistry.registerEntityRenderingHandler(HodyJonesBoss.INSTANCE, new HumanoidRenderer.Factory(new HodyJonesModel(), 1, "hody_jones"));

        //Misc
        RenderingRegistry.registerEntityRenderingHandler(AfterimageEntity.INSTANCE, new AfterimageRenderer.Factory());
    }

    private static void registerSeraphimsFallback() {
        RenderingRegistry.registerEntityRenderingHandler(SHawkEntity.INSTANCE, new HumanoidRenderer.Factory(new HumanoidModel<>(), 1));
    }

    private static void registerSeraphimsCarts() {
        RenderingRegistry.registerEntityRenderingHandler(SHawkEntity.INSTANCE, manager -> new UpscaleStealerRenderer<>(manager, new SHawkModel<>()));
    }

    private static class UpscaleStealerRenderer<E extends MobEntity, M extends BipedModel<E>> extends HumanoidRenderer<E, M> {
        public UpscaleStealerRenderer(EntityRendererManager manager, M model) {
            super(manager, model, "s_hawk");
            this.addLayer(new SeraphimWingsLayer<>(this));
        }

        @Override
        public void render(E entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
            matrixStack.scale(1.1f, 1.1f, 1.1f);
            super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        }
    }

    private static class SeraphimWingsLayer<T extends LivingEntity, M extends BipedModel<T>> extends LayerRenderer<T, M> {
        private final LunarianClosedWingsModel<T> model = new LunarianClosedWingsModel<>();
        private static final ResourceLocation TEXTURE = new ResourceLocation("cartaddon", "textures/models/zoanmorph/lunarian_closed_wings.png");

        public SeraphimWingsLayer(IEntityRenderer<T, M> renderer) {
            super(renderer);
        }

        public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            RenderType renderType = ModRenderTypes.getZoanRenderType(TEXTURE);
            matrixStack.pushPose();
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.renderToBuffer(matrixStack, buffer.getBuffer(renderType), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.popPose();
        }
    }
}
