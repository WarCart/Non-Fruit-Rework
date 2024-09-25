package net.warcar.non_fruit_rework.chalenges;

import com.mojang.authlib.GameProfile;
import net.minecraft.command.arguments.UUIDArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.chalenges.arenas.TestArena;
import net.warcar.non_fruit_rework.mixins.IInProgressChallengeMixin;
import org.apache.logging.log4j.core.util.UuidUtil;
import xyz.pixelatedw.mineminenomi.api.challenges.*;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public class TestChallenge extends Challenge {
    private UUID[] targetGroup = new UUID[0];

    public static final ChallengeCore<TestChallenge> INSTANCE = new ChallengeCore.Builder<>("player_challenge", "Player Challenge", "Kill targeted player", ModNPCGroups.UNGROUPED, TestChallenge::new)
            .setDifficulty(ChallengeDifficulty.ULTIMATE).setDifficultyStars(8).addArena(ArenaStyle.SIMPLE, new TestArena(), TestChallenge::getChallengerSpawnPos, TestChallenge::getEnemySpawnPos).setEnemySpawns(TestChallenge::setEnemiesSpawns).setTargetShowcase(new Supplier[]{() -> EntityType.PLAYER})
            .setRewards(new ResourceLocation(NonFruitReworkMod.MOD_ID, "rewards/test")).build();

    public TestChallenge(ChallengeCore<TestChallenge> core) {
        super(core);
    }

    private static ChallengeArena.SpawnPosition getChallengerSpawnPos(int posId, InProgressChallenge challenge) {
        BlockPos pos = new BlockPos(challenge.getArenaPos().getX() + (Math.sin(Math.toRadians(posId)) * 52), challenge.getArenaPos().getY() + 1, challenge.getArenaPos().getZ() + (Math.cos(Math.toRadians(posId)) * 52));
        return new ChallengeArena.SpawnPosition(pos, posId, 0.0F);
    }

    public static ChallengeArena.SpawnPosition[] getEnemySpawnPos(InProgressChallenge challenge) {
        TestChallenge self = (TestChallenge) ((IInProgressChallengeMixin) challenge).getChallenge();
        ChallengeArena.SpawnPosition[] positions = new ChallengeArena.SpawnPosition[self.targetGroup.length];
        for (int i = 0; i < self.targetGroup.length; i++) {
            positions[i] = new ChallengeArena.SpawnPosition(new BlockPos(challenge.getArenaPos().getX() - (Math.sin(Math.toRadians(i)) * 52), challenge.getArenaPos().getY() + 1, challenge.getArenaPos().getZ() - (Math.cos(Math.toRadians(i)) * 52)), 180, 0);
        }
        return positions;
    }

    public static Set<ChallengeArena.EnemySpawn> setEnemiesSpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
        Set<ChallengeArena.EnemySpawn> set = new HashSet<>();
        TestChallenge self = (TestChallenge) ((IInProgressChallengeMixin) challenge).getChallenge();
        for (int i = 0; i < self.targetGroup.length; i++) {
            PlayerEntity player = challenge.getShard().getPlayerByUUID(self.targetGroup[i]);
            if (player != null) {
                set.add(new ChallengeArena.EnemySpawn(player, spawns[i]));
            }
        }
        return set;
    }

    public UUID[] getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(UUID... targetGroup) {
        this.targetGroup = targetGroup;
    }

    public void setTargetGroup(PlayerEntity... targetGroup) {
        this.targetGroup = new UUID[targetGroup.length];
        for (int i = 0; i < targetGroup.length; i++) {
            this.targetGroup[i] = targetGroup[i].getUUID();
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ListNBT listNBT = new ListNBT();
        for (UUID uuid : this.targetGroup) {
            listNBT.add(NBTUtil.createUUID(uuid));
        }
        nbt.put("targets", listNBT);
        return nbt;
    }

    @Override
    public void load(CompoundNBT nbt) {
        super.load(nbt);
        if (nbt.contains("targets")) {
            ListNBT listNBT = nbt.getList("targets", 1);
            this.targetGroup = new UUID[listNBT.size()];
            for (int i = 0; i < listNBT.size(); i++) {
                targetGroup[i] = NBTUtil.loadUUID(listNBT.get(i));
            }
        }
    }
}
