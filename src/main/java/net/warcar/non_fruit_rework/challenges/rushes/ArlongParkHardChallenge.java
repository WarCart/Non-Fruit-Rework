package net.warcar.non_fruit_rework.challenges.rushes;

import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.*;
import xyz.pixelatedw.mineminenomi.challenges.arenas.ArlongParkSimpleArena;
import xyz.pixelatedw.mineminenomi.challenges.arlongpark.ArlongChallenge;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ArlongEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ChewEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.KuroobiEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ArlongParkHardChallenge extends Challenge {
    private static final String TITLE = LangHelper.registerChallengeName("challenge.non_fruit_rework.arlong_park_hard", "Arlong Park (Hard)");
    public static final ChallengeCore INSTANCE = new ChallengeCore.Builder("arlong_park_hard", TITLE, ArlongParkChallenge.OBJECTIVE, ModNPCGroups.ARLONG_PIRATES.getName(), ArlongChallenge::new)
            .setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(8).addArena(ArenaStyle.SIMPLE, ArlongParkSimpleArena.INSTANCE, ArlongParkSimpleArena::getChallengerSpawnPos, ArlongParkSimpleArena::getEnemySpawnPos)
            .setEnemySpawns(ArlongParkHardChallenge::setEnemeySpawns).setTargetShowcase(new Supplier[]{ModEntities.ARLONG, ModEntities.CHEW, ModEntities.KUROOBI})
            .setTimeLimit(10).build();

    public ArlongParkHardChallenge(ChallengeCore<?> core) {
        super(core);
    }

    public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
        Set<ChallengeArena.EnemySpawn> set = new HashSet();
        ArlongEntity arlong = new ArlongEntity(challenge);
        set.add(new ChallengeArena.EnemySpawn(arlong, spawns[0]));
        ChewEntity chew = new ChewEntity(challenge);
        set.add(new ChallengeArena.EnemySpawn(chew, spawns[1]));
        KuroobiEntity kuroobi = new KuroobiEntity(challenge);
        set.add(new ChallengeArena.EnemySpawn(kuroobi, spawns[2]));
        return set;
    }
}
