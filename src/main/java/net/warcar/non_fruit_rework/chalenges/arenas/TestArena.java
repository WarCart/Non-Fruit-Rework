package net.warcar.non_fruit_rework.chalenges.arenas;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Features;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.challenges.arenas.parts.SimpleBasePart;
import xyz.pixelatedw.mineminenomi.challenges.arenas.parts.TreePart;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.Set;

public class TestArena extends ChallengeArena {
    public TestArena() {
        super(ArenaStyle.SIMPLE);
    }

    public Set<BlockPos> buildArena(InProgressChallenge challenge) {
        Set<BlockPos> blocks = Sets.newHashSet();
        blocks.addAll(new SimpleBasePart(challenge).buildPart(challenge.getShard(), challenge.getArenaPos()));
        for (int i = 0; i < 39; i++) {
            createCenteredFilledCircle(challenge.getShard(), challenge.getArenaPos().immutable().below(i), 73 + i, blocks, challenge.getArenaPos().getY() - 3);
        }
        populateTrees(challenge, challenge.getArenaPos(), blocks);
        blocks.addAll(StructuresHelper.fillCube(challenge.getShard(), challenge.getArenaPos().immutable().north(100).east(100).below(38), challenge.getArenaPos().immutable().south(100).west(100).below(3), Blocks.WATER.defaultBlockState(), 0, new BlockProtectionRule.Builder().addApprovedBlocks(Blocks.AIR).build()));
        return blocks;
    }

    public MutableBoundingBox getArenaLimits() {
        return new MutableBoundingBox(-100, -40, -100, 100, 40, 100);
    }

    /*private static void ring(World level, BlockPos position, int radius, int thickness, Set<BlockPos> blocks, int waterLevel) {
        for (int i = 0; i < 360; i++) {
            BlockPos pos = position.immutable().west((int) (Math.sin(Math.toRadians(i)) * radius)).south((int) (Math.cos(Math.toRadians(i)) * radius));
            createCenteredFilledCircle(level, pos, thickness, blocks, waterLevel);
        }
    }*/

    private void populateTrees(InProgressChallenge challenge, BlockPos pos, Set<BlockPos> blocks) {
        BlockPos.Mutable mutpos = new BlockPos.Mutable();

        for(int i = 0; i < 100; ++i) {
            int offsetX = (int) WyHelper.randomWithRange(challenge.getRNG(), -73, 73);
            int offsetZ = (int)WyHelper.randomWithRange(challenge.getRNG(), -73, 73);
            mutpos.set(pos.offset(offsetX, 0, offsetZ));
            if (challenge.getShard().getBlockState(mutpos).getBlock() == Blocks.GRASS_BLOCK) {
                float rand = challenge.getRNG().nextFloat();
                if (rand > 0.9f) {
                    blocks.addAll((new TreePart(challenge, Features.OAK, challenge.getRNG().nextInt(10))).buildPart(challenge.getShard(), mutpos.immutable()));
                } else if (rand > 0.2f) {
                    blocks.addAll((new TreePart(challenge, Features.JUNGLE_TREE, challenge.getRNG().nextInt(15))).buildPart(challenge.getShard(), mutpos.immutable()));
                } else {
                    blocks.addAll((new TreePart(challenge, Features.MEGA_JUNGLE_TREE, challenge.getRNG().nextInt(40))).buildPart(challenge.getShard(), mutpos.immutable()));
                }
            }
        }
    }

    private static void createCenteredFilledCircle(World world, BlockPos origin, int radiusXZ, Set<BlockPos> blocks, int waterLevel) {
        int x0 = origin.getX();
        int y0 = origin.getY();
        int z0 = origin.getZ();
        BlockPos.Mutable mutpos = new BlockPos.Mutable();

        for(double y = 0.0D; y < (double) 1; ++y) {
            for(double x = x0 - radiusXZ; x < (double)(x0 + radiusXZ); ++x) {
                for(double z = z0 - radiusXZ; z < (double)(z0 + radiusXZ); ++z) {
                    double distance = ((double)x0 - x) * ((double)x0 - x) + ((double)z0 - z) * ((double)z0 - z);
                    if (distance < (double)(radiusXZ * radiusXZ)) {
                        mutpos.set(x, (double)y0 + y, z);
                        BlockState block;
                        if (world.getBlockState(mutpos.immutable().above()).getBlock() == Blocks.AIR && mutpos.getY() > waterLevel) {
                            block = Blocks.GRASS_BLOCK.defaultBlockState();
                        } else if (world.getBlockState(mutpos.immutable().above()).getBlock() == Blocks.AIR) {
                            block = Blocks.SAND.defaultBlockState();
                        } else if (world.getBlockState(mutpos.immutable().above(4)).getBlock() == Blocks.AIR && mutpos.getY() + 3 > waterLevel) {
                            block = Blocks.DIRT.defaultBlockState();
                        } else {
                            block = Blocks.STONE.defaultBlockState();
                        }
                        if (!blocks.contains(mutpos) && AbilityHelper.placeBlockIfAllowed(world, mutpos, block, 0, null)) {
                            blocks.add(mutpos.immutable());
                        }
                    }
                }
            }
        }
    }
}
