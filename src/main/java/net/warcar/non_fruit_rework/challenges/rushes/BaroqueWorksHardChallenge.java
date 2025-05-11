package net.warcar.non_fruit_rework.challenges.rushes;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.AlabastaDesertSimpleArena;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr0Challenge;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

public class BaroqueWorksHardChallenge extends Challenge {
    private static final String TITLE = LangHelper.registerChallengeName("challenge.mineminenomi.baroque_works_hard", "Baroque Works (Hard)");
    public static final ResourceLocation REWARD = new ResourceLocation(NonFruitReworkMod.MOD_ID, "rewards/baroque_works");
    public static final ChallengeCore<BaroqueWorksHardChallenge> INSTANCE = new ChallengeCore.Builder("baroque_works_hard", TITLE, BaroqueWorksChallenge.OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), BaroqueWorksHardChallenge::new)
            .setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(10).addArena(ArenaStyle.SIMPLE, AlabastaDesertSimpleArena.INSTANCE, AlabastaDesertSimpleArena::getChallengerSpawnPos, BaroqueWorksChallenge::getEnemySpawnPos)
            .setEnemySpawns(BaroqueWorksChallenge::setEnemySpawns).setTargetShowcase(Mr0Challenge::createMr0Showcase, BaroqueWorksChallenge::createMr1Showcase, BaroqueWorksChallenge::createMr3Showcase, BaroqueWorksChallenge::createMr2Showcase).setTimeLimit(30).setRewards(REWARD).build();

    public BaroqueWorksHardChallenge(ChallengeCore core) {
        super(core);
    }
}