package net.warcar.non_fruit_rework.init;

import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.structures.CP9TentStructure;

import static xyz.pixelatedw.mineminenomi.init.ModStructures.setupMapSpacingAndLand;

public class ModStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, NonFruitReworkMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }

    public static final RegistryObject<CP9TentStructure> CP_9_TENT = STRUCTURES.register("cp_9_tent", CP9TentStructure::new);

    public static void setupStructures() {
        setupMapSpacingAndLand(CP_9_TENT.get().configured(IFeatureConfig.NONE), new StructureSeparationSettings(48, 16, 798136332), true);
    }
}
