package net.warcar.non_fruit_rework.challenges.rushes;

import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.ArlongParkSimpleArena;
import xyz.pixelatedw.mineminenomi.challenges.arlongpark.ArlongChallenge;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

import java.util.function.Supplier;

public class ArlongParkHardChallenge extends Challenge {
    private static final String TITLE = LangHelper.registerChallengeName("challenge.non_fruit_rework.arlong_park_hard", "Arlong Park (Hard)");
    public static final ChallengeCore INSTANCE = new ChallengeCore.Builder("arlong_park_hard", TITLE, ArlongParkChallenge.OBJECTIVE, ModNPCGroups.ARLONG_PIRATES.getName(), ArlongChallenge::new)
            .setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(8).addArena(ArenaStyle.SIMPLE, ArlongParkSimpleArena.INSTANCE, ArlongParkSimpleArena::getChallengerSpawnPos, ArlongParkSimpleArena::getEnemySpawnPos)
            .setEnemySpawns(ArlongParkChallenge::setEnemeySpawns).setTargetShowcase(new Supplier[]{ModEntities.ARLONG, ModEntities.CHEW, ModEntities.KUROOBI})
            .setTimeLimit(10).build();

    public ArlongParkHardChallenge(ChallengeCore<?> core) {
        super(core);
    }
}
