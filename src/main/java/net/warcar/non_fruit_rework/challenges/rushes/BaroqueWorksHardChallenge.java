package net.warcar.non_fruit_rework.challenges.rushes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.AlabastaDesertSimpleArena;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr0Challenge;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

public class BaroqueWorksHardChallenge extends Challenge {
    private static final String TITLE = LangHelper.registerChallengeName("challenge.mineminenomi.baroque_works_hard", "Baroque Works (Hard)");
    public static final ResourceLocation REWARD = new ResourceLocation(NonFruitReworkMod.MOD_ID, "rewards/baroque_works");
    public static final ChallengeCore<BaroqueWorksHardChallenge> INSTANCE = new ChallengeCore.Builder("baroque_works_hard", TITLE, BaroqueWorksChallenge.OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), BaroqueWorksHardChallenge::new)
            .setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(10).addArena(ArenaStyle.SIMPLE, AlabastaDesertSimpleArena.INSTANCE, AlabastaDesertSimpleArena::getChallengerSpawnPos, BaroqueWorksChallenge::getEnemySpawnPos)
            .setEnemySpawns(BaroqueWorksChallenge::setEnemySpawns).setTargetShowcase(Mr0Challenge::createMr0Showcase, BaroqueWorksChallenge::createMr1Showcase, BaroqueWorksChallenge::createMr3Showcase, BaroqueWorksChallenge::createMr4Showcase).setTimeLimit(30).setRewards(REWARD).build();

    private static LivingEntity createMr4Showcase(World world) {
        return ModEntities.MR4.get().create(world);
    }

    private static LivingEntity createMr3Showcase(World world) {
        return ModEntities.MR3.get().create(world);
    }

    public BaroqueWorksHardChallenge(ChallengeCore core) {
        super(core);
    }

    private static LivingEntity createMr1Showcase(World world) {
        return ModEntities.MR1.get().create(world);
    }
}