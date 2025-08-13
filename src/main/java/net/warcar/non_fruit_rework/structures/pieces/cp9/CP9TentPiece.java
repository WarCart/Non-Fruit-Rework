package net.warcar.non_fruit_rework.structures.pieces.cp9;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import xyz.pixelatedw.mineminenomi.blocks.tileentities.CustomSpawnerTileEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModStructures;

import java.util.Random;

public class CP9TentPiece extends TemplateStructurePiece
{
	private ResourceLocation resourceLocation;
	private Rotation rotation;

	public CP9TentPiece(TemplateManager template, CompoundNBT nbt)
	{
		super(ModStructures.Pieces.SWORDSMAN_DOJO_PIECE, nbt);
        this.resourceLocation = new ResourceLocation(nbt.getString("Template"));
        this.rotation = Rotation.valueOf(nbt.getString("Rot"));
        this.build(template);
	}
	
	public CP9TentPiece(TemplateManager template, BlockPos pos, Rotation rot)
	{
		super(ModStructures.Pieces.SWORDSMAN_DOJO_PIECE, 0);
		this.templatePosition = pos;
		this.rotation = rot;
		this.resourceLocation = new ResourceLocation(NonFruitReworkMod.MOD_ID, "cp9/small_tent");
		this.build(template);
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt)
	{
		super.addAdditionalSaveData(nbt);
		nbt.putString("Template", this.resourceLocation.toString());
		nbt.putString("Rot", this.rotation.name());
	}
	
	private void build(TemplateManager templateManager)
	{
		Template template = templateManager.getOrCreate(this.resourceLocation);
		PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
		this.setup(template, this.templatePosition, placementsettings);		
	}
	
	@Override
	protected void handleDataMarker(String function, BlockPos pos, IServerWorld world, Random rand, MutableBoundingBox sbb)
	{
		if (function.equals("trainer_spawn"))
		{
			world.setBlock(pos, ModBlocks.CUSTOM_SPAWNER.get().defaultBlockState(), 3);
			TileEntity spawner = world.getBlockEntity(pos);
			if (spawner instanceof CustomSpawnerTileEntity)
			{
				((CustomSpawnerTileEntity) spawner).setSpawnerLimit(1);
				((CustomSpawnerTileEntity) spawner).setSpawnerMob(ModEntities.SWORDSMAN_TRAINER.get());
			}
		}
	}
	
	@Override
	public boolean postProcess(ISeedReader pLevel, StructureManager pStructureManager, ChunkGenerator pChunkGenerator, Random pRandom, MutableBoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos)
	{
//		BlockPos ogPos = this.templatePosition;
//		this.templatePosition = this.templatePosition.offset(0, -2, 0);
		boolean flag = super.postProcess(pLevel, pStructureManager, pChunkGenerator, pRandom, pBox, pChunkPos, pPos);
//		this.templatePosition = ogPos;
		return flag;
	}
}