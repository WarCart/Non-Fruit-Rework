package net.warcar.non_fruit_rework.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.structures.CP9TentStructure;
import net.warcar.non_fruit_rework.structures.pieces.cp9.CP9TentPiece;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import static xyz.pixelatedw.mineminenomi.init.ModStructures.setupMapSpacingAndLand;

public class ModStructures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, NonFruitReworkMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }

    public static final RegistryObject<CP9TentStructure> CP_9_TENT = STRUCTURES.register("cp_9_tent", CP9TentStructure::new);

    public static void setupStructures() {
        setupMapSpacingAndLand(CP_9_TENT.get().configured(IFeatureConfig.NONE), new StructureSeparationSettings(48, 16, 798136332), true);

        Pieces.setupStructurePieces();
    }

    public static class Pieces {
        public static final IStructurePieceType CP_9_TENT_PIECE = CP9TentPiece::new;

        public static void setupStructurePieces() {
            registerStructurePiece(CP_9_TENT_PIECE, "cp_9_tent");
        }

        static void registerStructurePiece(IStructurePieceType structurePiece, String name) {
            Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(NonFruitReworkMod.MOD_ID, WyHelper.getResourceName(name)), structurePiece);
        }
    }
}
