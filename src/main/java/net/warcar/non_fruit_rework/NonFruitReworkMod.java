package net.warcar.non_fruit_rework;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.warcar.non_fruit_rework.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(NonFruitReworkMod.MOD_ID)
public class NonFruitReworkMod {
    public static final String MOD_ID = "non_fruit_rework";
    public static final Logger LOGGER = LogManager.getLogger();

    public NonFruitReworkMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);
        bus.addListener(this::clientSetup);
        ModRaidSuits.register();
        ReworkedAbilities.init(bus);
        AbilityUpdate.init();
        ReworkedAttributes.init();
        ReworkedQuests.init();
        ReworkedComponents.init();
        if (ModConfig.INSTANCE.isPlayerChallenges()) ReworkedChallenges.init();
        ReworkedLootTypes.init();
        ModLootModifiers.LOOT_MODIFIER_SERIALIZERS.register(bus);
        ModLoadingContext.get().registerConfig(Type.COMMON, ModConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModCapabilities.init();
        ModNetwork.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ReworkedAnimations::clientInit);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void processIMC(final InterModProcessEvent event) {}
}
