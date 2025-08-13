package net.warcar.non_fruit_rework.world.spawners;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.server.ServerWorld;
import net.warcar.non_fruit_rework.entities.quests.ElectroTrainer;
import net.warcar.non_fruit_rework.entities.quests.FishmanTrainer;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.wypi.WyDebug;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class RaceTrainerSpawner {
    private static final Predicate<Entity> TRAINER_CHECK = (target) -> target instanceof ITrainer;
    private final Random random = new Random();
    private static final EntityType[] TRAINERS = new EntityType[] {ElectroTrainer.INSTANCE, FishmanTrainer.INSTANCE};
    private int cooldown = CommonConfig.INSTANCE.getTimeBetweenTrainerSpawns() / 2 * 3;

    public void tick(ServerWorld world) {
        world.getProfiler().push("racialTrainerSpawnerTick");
        if (--this.cooldown <= 0) {
            this.cooldown = CommonConfig.INSTANCE.getTimeBetweenTrainerSpawns();
            if (this.random.nextInt(100) <= CommonConfig.INSTANCE.getChanceForTrainerSpawn()) {
                this.spawn(world);
            }
        }

        world.getProfiler().pop();
    }

    private void spawn(ServerWorld world) {
        world.getProfiler().push("racialTrainerSpawnerSpawn");
        int listSize = MathHelper.clamp(world.players().size() / 3, 1, 10);
        PlayerEntity[] cachedPlayers = new PlayerEntity[listSize];

        for(int i = 0; i < cachedPlayers.length; ++i) {
            PlayerEntity player = world.getRandomPlayer();
            if (player != null) {
                boolean alreadyCached = Arrays.stream(cachedPlayers).anyMatch((target) -> target == player);
                if (!alreadyCached) {
                    cachedPlayers[i] = player;
                    EntityType entityType = TRAINERS[random.nextInt(TRAINERS.length)];
                    BlockPos targetPos = player.blockPosition();
                    Biome biome = world.getBiome(targetPos);

                    BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, entityType, targetPos, 20);
                    if (spawnPos == null) {
                        return;
                    }

                    List<LivingEntity> trainers = WyHelper.getNearbyEntities(new Vector3d(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()), world, 100.0F, TRAINER_CHECK, LivingEntity.class);
                    boolean canSpawnInBiome = biome.getBiomeCategory() != Category.OCEAN;
                    if (trainers.size() < 2 && canSpawnInBiome) {
                        entityType.spawn(world, null, null, null, spawnPos, SpawnReason.EVENT, false, false);
                        WyDebug.debug("Trainer (" + entityType + ") spawned at: " + spawnPos);
                    }
                }
            }
        }

        world.getProfiler().pop();
    }
}
