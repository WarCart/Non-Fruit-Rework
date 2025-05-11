package net.warcar.non_fruit_rework.challenges.rushes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.*;
import xyz.pixelatedw.mineminenomi.challenges.arenas.AlabastaDesertSimpleArena;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr0Challenge;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.*;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

import java.util.HashSet;
import java.util.Set;

public class BaroqueWorksChallenge extends Challenge implements IHasRequirements {
    private static final String TITLE = LangHelper.registerChallengeName("challenge.mineminenomi.baroque_works", "Baroque Works");
    public static final String OBJECTIVE = LangHelper.registerChallengeName("challenge.mineminenomi.baroque_works.objective", "Defeat Baroque Works");
    public static final ResourceLocation REWARD = new ResourceLocation(NonFruitReworkMod.MOD_ID, "rewards/baroque_works");
    public static final ChallengeCore<BaroqueWorksChallenge> INSTANCE = new ChallengeCore.Builder("baroque_works", TITLE, OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), BaroqueWorksChallenge::new)
            .setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(10).addArena(ArenaStyle.SIMPLE, AlabastaDesertSimpleArena.INSTANCE, AlabastaDesertSimpleArena::getChallengerSpawnPos, BaroqueWorksChallenge::getEnemySpawnPos)
            .setEnemySpawns(BaroqueWorksChallenge::setEnemySpawns).setTargetShowcase(Mr0Challenge::createMr0Showcase, BaroqueWorksChallenge::createMr1Showcase, BaroqueWorksChallenge::createMr2Showcase, BaroqueWorksChallenge::createMr3Showcase).setTimeLimit(30).setRewards(REWARD).build();

    public static LivingEntity createMr2Showcase(World world) {
        return ModEntities.MR4.get().create(world);
    }

    public static LivingEntity createMr3Showcase(World world) {
        return ModEntities.MR3.get().create(world);
    }

    public static ChallengeArena.SpawnPosition[] getEnemySpawnPos(InProgressChallenge challenge) {
        BlockPos pos = new BlockPos(challenge.getArenaPos().getX() - 15, challenge.getArenaPos().getY() - 30, challenge.getArenaPos().getZ() - 15);
        ChallengeArena.SpawnPosition pos1 = new ChallengeArena.SpawnPosition(pos, 180f, 0.0F);
        ChallengeArena.SpawnPosition pos2 = new ChallengeArena.SpawnPosition(pos.offset(-1, 0, 0), 180f, 0.0F);
        ChallengeArena.SpawnPosition pos3 = new ChallengeArena.SpawnPosition(pos.offset(0, 0, -1), 180f, 0.0F);
        ChallengeArena.SpawnPosition pos4 = new ChallengeArena.SpawnPosition(pos.offset(0, 0, 1), 180f, 0.0F);
        ChallengeArena.SpawnPosition pos5 = new ChallengeArena.SpawnPosition(pos.offset(1, 0, 0), 180f, 0.0F);
        ChallengeArena.SpawnPosition pos6 = new ChallengeArena.SpawnPosition(pos.offset(-1, 0, -1), 180f, 0.0F);
        ChallengeArena.SpawnPosition pos7 = new ChallengeArena.SpawnPosition(pos.offset(1, 0, -1), 180f, 0.0F);
        ChallengeArena.SpawnPosition pos8 = new ChallengeArena.SpawnPosition(pos.offset(-1, 0, 1), 180f, 0.0F);
        ChallengeArena.SpawnPosition pos9 = new ChallengeArena.SpawnPosition(pos.offset(1, 0, 1), 180f, 0.0F);
        return new ChallengeArena.SpawnPosition[]{pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9};
    }

    public BaroqueWorksChallenge(ChallengeCore core) {
        super(core);
    }

    public static Set<ChallengeArena.EnemySpawn> setEnemySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
        Set<ChallengeArena.EnemySpawn> set = new HashSet<>();
        set.add(new ChallengeArena.EnemySpawn(new Mr0Entity(challenge), spawns[0]));
        set.add(new ChallengeArena.EnemySpawn(new Mr1Entity(challenge), spawns[1]));
        // Sadly no Mr2
        set.add(new ChallengeArena.EnemySpawn(new Mr3Entity(challenge), spawns[2]));
        // WIP by Wynd
//        set.add(new ChallengeArena.EnemySpawn(new Mr4Entity(challenge), spawns[3]));
//        set.add(new ChallengeArena.EnemySpawn(new MissMerryChristmasEntity(challenge), spawns[4]));
        set.add(new ChallengeArena.EnemySpawn(new Mr5Entity(challenge), spawns[5]));
        set.add(new ChallengeArena.EnemySpawn(new MissValentineEntity(challenge), spawns[6]));
        return set;
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return false;
    }

    public static LivingEntity createMr1Showcase(World world) {
        return ModEntities.MR1.get().create(world);
    }
}