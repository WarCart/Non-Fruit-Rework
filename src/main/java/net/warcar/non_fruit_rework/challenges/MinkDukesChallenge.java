package net.warcar.non_fruit_rework.challenges;

import net.warcar.non_fruit_rework.entities.bosses.InuarashiBoss;
import net.warcar.non_fruit_rework.entities.bosses.NekomamushiBoss;
import xyz.pixelatedw.mineminenomi.api.challenges.*;
import xyz.pixelatedw.mineminenomi.challenges.arenas.CircusArena;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

import java.util.HashSet;
import java.util.Set;

public class MinkDukesChallenge extends Challenge {
    public static final ChallengeCore INSTANCE = new ChallengeCore.Builder<>("mink_dukes", "Mink Dukes", "Defeat Nekomamushi and Inuarashi", ModNPCGroups.UNGROUPED, MinkDukesChallenge::new)
            .setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(6).addArena(ArenaStyle.SIMPLE, CircusArena.INSTANCE, CircusArena::getChallengerSpawnPos, CircusArena::getEnemySpawnPos)
            .setTargetShowcase(NekomamushiBoss.INSTANCE::create, InuarashiBoss.INSTANCE::create).setEnemySpawns(MinkDukesChallenge::getEnemySpawns).build();

    public MinkDukesChallenge(ChallengeCore<MinkDukesChallenge> core) {
        super(core);
    }

    private static Set<ChallengeArena.EnemySpawn> getEnemySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawnPositions) {
        HashSet<ChallengeArena.EnemySpawn> spawns = new HashSet<>();
        spawns.add(new ChallengeArena.EnemySpawn(new NekomamushiBoss(challenge), spawnPositions[0]));
        spawns.add(new ChallengeArena.EnemySpawn(new InuarashiBoss(challenge), spawnPositions[1]));
        return spawns;
    }
}
