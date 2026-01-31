package net.warcar.non_fruit_rework.init;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.blocks.GeneCentrifugeBlock;
import net.warcar.non_fruit_rework.blocks.GeneSamplerBlock;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, NonFruitReworkMod.MOD_ID);

    public static final RegistryObject<GeneCentrifugeBlock> GENE_CENTRIFUGE = register("Gene Centrifuge", GeneCentrifugeBlock::new);
    public static final RegistryObject<GeneSamplerBlock> GENE_SAMPLER = register("Gene Sampler", GeneSamplerBlock::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static <B extends Block> RegistryObject<B> register(String name, Supplier<B> block) {
        String resourceName = WyHelper.getResourceName(name);
        RegistryObject<B> register = BLOCKS.register(resourceName, block);
        ResourceLocation id = register.getId();
        LangHelper.registerLine(String.format("block.%s.%s", id.getNamespace(), id.getPath()), name);
        ModItems.registerItem(name, () -> new BlockItem(register.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
        return register;
    }
}
