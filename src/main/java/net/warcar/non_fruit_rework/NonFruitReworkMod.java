package net.warcar.non_fruit_rework;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.warcar.non_fruit_rework.config.CommonConfig;
import net.warcar.non_fruit_rework.init.*;
import net.warcar.non_fruit_rework.renderers.layers.HeadLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.pixelatedw.mineminenomi.mixins.RangedAttributeMixin;

import java.util.Map;

/**
 * Not finished Stuff:
 * <p>
 * Seraphims
 * <p>
 * Models/textures
 * <p>
 * Berserk for humans
 * <p>
 * Genetic Modification and cloning
 */
@Mod(NonFruitReworkMod.MOD_ID)
public class NonFruitReworkMod {
    public static final String MOD_ID = "non_fruit_rework";
    public static final Logger LOGGER = LogManager.getLogger();

    public NonFruitReworkMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::onLoadComplete);
        bus.addListener(this::clientSetup);
        ReworkedUnlockRequirements.init();
        ModQuests.register(bus);
        ModAbilities.register(bus);
        ModEntityTypes.register(bus);
        ModParticles.register(bus);
        ModItems.register(bus);
        ModTexts.init();
        ModChallenges.register(bus);
        ModDamages.init();
        ModEntityAttributes.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModPackets.init();
        ModCapabilities.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            for (Map.Entry<EntityType<?>, EntityRenderer<?>> entry : mc.getEntityRenderDispatcher().renderers.entrySet()) {
                EntityRenderer entityRenderer = entry.getValue();
                if (entityRenderer instanceof LivingRenderer) {
                    LivingRenderer renderer = (LivingRenderer) entityRenderer;
                    renderer.addLayer(new HeadLayer<>(renderer));
                }
            }
            for (Map.Entry<String, PlayerRenderer> entry : mc.getEntityRenderDispatcher().getSkinMap().entrySet()) {
                PlayerRenderer renderer = entry.getValue();
                renderer.addLayer(new HeadLayer<>(renderer));
            }
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void onLoadComplete(final FMLLoadCompleteEvent event) {
        ((RangedAttributeMixin) Attributes.ARMOR).setMaxValue(2000);
    }
}
