package net.warcar.non_fruit_rework.structures;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.warcar.non_fruit_rework.structures.pieces.cp9.CP9TentPiece;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.world.features.structures.OPStructure;
import xyz.pixelatedw.mineminenomi.wypi.WyDebug;

public class CP9TentStructure extends OPStructure<NoFeatureConfig> {
    public CP9TentStructure() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public boolean biomeCheck(BiomeLoadingEvent biomeLoadingEvent) {
        return biomeLoadingEvent.getCategory() != Biome.Category.OCEAN;
    }

    @Override
    public StructuresHelper.StructureFaction getFaction() {
        return StructuresHelper.StructureFaction.MARINE;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ,
                     MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator,
                                   TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn,
                                   NoFeatureConfig config) {
            Rotation rotation = Rotation.getRandom(this.random);
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;
            int surfaceY = chunkGenerator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos blockpos = new BlockPos(x, surfaceY, z);

            this.pieces.add(new CP9TentPiece(templateManagerIn, blockpos, rotation));

            this.calculateBoundingBox();

            StructuresHelper.SPAWNED_STRUCTURES.add(blockpos);
            WyDebug.debug("CP9 Tent spawned at: /tp " + this.pieces.get(0).getBoundingBox().x0 + " ~ " + this.pieces.get(0).getBoundingBox().z0);
        }
    }
}
