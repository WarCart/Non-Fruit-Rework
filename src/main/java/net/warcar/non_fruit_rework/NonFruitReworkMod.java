package net.warcar.non_fruit_rework;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.warcar.non_fruit_rework.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        ModItems.register(bus);
        ModTexts.init();
        ModChallenges.register(bus);
        ModDamages.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModPackets.init();
        ModCapabilities.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void onLoadComplete(final FMLLoadCompleteEvent event) {
    }
}
