package net.warcar.non_fruit_rework.challenges;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.entities.bosses.HodyJonesBoss;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.*;
import xyz.pixelatedw.mineminenomi.challenges.arenas.ArlongParkSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

import java.util.HashSet;
import java.util.Set;

public class HodyJonesChallenge extends Challenge {
    private static final String TITLE = LangHelper.registerLine("challenge.mineminenomi.hody_jones", "Hody Jones").getKey();
    public static final String OBJECTIVE = LangHelper.registerLine("challenge.mineminenomi.hody_jones.objective", "Defeat Hody Jones").getKey();
    public static final ResourceLocation REWARD = new ResourceLocation(NonFruitReworkMod.MOD_ID, "rewards/hody_jones");
    public static final ChallengeCore INSTANCE = new ChallengeCore.Builder<>("hody_jones", TITLE, OBJECTIVE, ModNPCGroups.ARLONG_PIRATES.getName(), HodyJonesChallenge::new)
            .setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(7).addArena(ArenaStyle.SIMPLE, ArlongParkSimpleArena.INSTANCE, ArlongParkSimpleArena::getChallengerSpawnPos, ArlongParkSimpleArena::getEnemySpawnPos)
            .setEnemySpawns(HodyJonesChallenge::setEnemeySpawns).setTimeLimit(10).setTargetShowcase(HodyJonesBoss.INSTANCE::create).build();

    public HodyJonesChallenge(ChallengeCore<HodyJonesChallenge> core) {
        super(core);
    }

    public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
        Set<ChallengeArena.EnemySpawn> set = new HashSet<>();
        set.add(new ChallengeArena.EnemySpawn(new HodyJonesBoss(challenge), spawns[0]));
        return set;
    }

}