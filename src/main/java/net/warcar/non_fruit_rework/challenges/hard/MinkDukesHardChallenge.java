package net.warcar.non_fruit_rework.challenges.hard;

import net.warcar.non_fruit_rework.challenges.easy.MinkDukesChallenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.JungleClearingSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

public class MinkDukesHardChallenge extends Challenge {
    public static final ChallengeCore INSTANCE = new ChallengeCore.Builder<>("mink_dukes_hard", "Mink Dukes (Hard)", "Defeat Nekomamushi and Inuarashi", ModNPCGroups.UNGROUPED, MinkDukesHardChallenge::new)
            .setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(6).addArena(ArenaStyle.SIMPLE, JungleClearingSimpleArena.INSTANCE, JungleClearingSimpleArena::getChallengerSpawnPos, JungleClearingSimpleArena::getEnemySpawnPos)
            .setTargetShowcase(MinkDukesChallenge::createNekomamushiShowcase, MinkDukesChallenge::createInuarashiShowcase).setEnemySpawns(MinkDukesChallenge::getEnemySpawns).build();


    public MinkDukesHardChallenge(ChallengeCore<MinkDukesHardChallenge> core) {
        super(core);
    }
}
