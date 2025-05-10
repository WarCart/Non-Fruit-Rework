package net.warcar.non_fruit_rework.challenges.easy;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.entities.bosses.InuarashiBoss;
import net.warcar.non_fruit_rework.entities.bosses.NekomamushiBoss;
import xyz.pixelatedw.mineminenomi.api.challenges.*;
import xyz.pixelatedw.mineminenomi.challenges.arenas.JungleClearingSimpleArena;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModValues;

import java.util.HashSet;
import java.util.Set;

public class MinkDukesChallenge extends Challenge {
    public static final ChallengeCore INSTANCE = new ChallengeCore.Builder<>("mink_dukes", "Mink Dukes", "Defeat Nekomamushi and Inuarashi", ModNPCGroups.UNGROUPED, MinkDukesChallenge::new)
            .setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(6).addArena(ArenaStyle.SIMPLE, JungleClearingSimpleArena.INSTANCE, JungleClearingSimpleArena::getChallengerSpawnPos, JungleClearingSimpleArena::getEnemySpawnPos)
            .setTargetShowcase(MinkDukesChallenge::createNekomamushiShowcase, MinkDukesChallenge::createInuarashiShowcase).setEnemySpawns(MinkDukesChallenge::getEnemySpawns).build();

    public static InuarashiBoss createInuarashiShowcase(World world) {
        InuarashiBoss boss = InuarashiBoss.INSTANCE.create(world);
        IEntityStats stats = EntityStatsCapability.get(boss);
        stats.setRace(ModValues.MINK);
        stats.setSubRace(ModValues.MINK_DOG);
        return boss;
    }

    public static LivingEntity createNekomamushiShowcase(World world) {
        NekomamushiBoss boss = NekomamushiBoss.INSTANCE.create(world);
        IEntityStats stats = EntityStatsCapability.get(boss);
        stats.setRace(ModValues.MINK);
        stats.setSubRace(ModValues.MINK_LION);
        return boss;
    }

    public MinkDukesChallenge(ChallengeCore<MinkDukesChallenge> core) {
        super(core);
    }

    public static Set<ChallengeArena.EnemySpawn> getEnemySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawnPositions) {
        HashSet<ChallengeArena.EnemySpawn> spawns = new HashSet<>();
        spawns.add(new ChallengeArena.EnemySpawn(new NekomamushiBoss(challenge), spawnPositions[0]));
        spawns.add(new ChallengeArena.EnemySpawn(new InuarashiBoss(challenge), spawnPositions[1]));
        return spawns;
    }
}
