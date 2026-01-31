package net.warcar.non_fruit_rework.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class GeneCentrifugeBlock extends Block {
    public GeneCentrifugeBlock() {
        super(Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(3).requiresCorrectToolForDrops());
    }
}
