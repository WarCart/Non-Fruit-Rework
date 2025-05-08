package net.warcar.non_fruit_rework.challenges.easy;

import net.warcar.non_fruit_rework.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.api.challenges.*;
import xyz.pixelatedw.mineminenomi.challenges.arenas.CircusArena;

import java.util.HashSet;
import java.util.Set;

public class KingChallenge extends Challenge {
    public static final ChallengeCore INSTANCE = new ChallengeCore.Builder<>("king", "King", "Defeat King", ModNPCGroups.BEAST_PIRATES.getName(), KingChallenge::new)
            .setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(6).addArena(ArenaStyle.SIMPLE, CircusArena.INSTANCE, CircusArena::getChallengerSpawnPos, CircusArena::getEnemySpawnPos)
            .setEnemySpawns(KingChallenge::getEnemySpawns).build();

    public KingChallenge(ChallengeCore<KingChallenge> core) {
        super(core);
    }

    private static Set<ChallengeArena.EnemySpawn> getEnemySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawnPositions) {
        HashSet<ChallengeArena.EnemySpawn> spawns = new HashSet<>();
        return spawns;
    }
}
