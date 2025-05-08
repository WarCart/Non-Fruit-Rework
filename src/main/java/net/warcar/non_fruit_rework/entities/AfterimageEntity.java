package net.warcar.non_fruit_rework.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;

import java.util.Optional;
import java.util.UUID;

public class AfterimageEntity extends OPEntity {
    public static final EntityType<AfterimageEntity> INSTANCE = EntityType.Builder.<AfterimageEntity>of(AfterimageEntity::new, EntityClassification.AMBIENT)
            .sized(0.1f, 0.1f).setUpdateInterval(1).clientTrackingRange(100000).noSummon()
            .setShouldReceiveVelocityUpdates(false).build("meh");

    protected static final DataParameter<Integer> MAX_LIFE = EntityDataManager.defineId(AfterimageEntity.class, DataSerializers.INT);
    protected static final DataParameter<Optional<UUID>> PLAYER = EntityDataManager.defineId(AfterimageEntity.class, DataSerializers.OPTIONAL_UUID);

    public AfterimageEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public AfterimageEntity(World level, int maxLife) {
        super(INSTANCE, level);
        this.setMaxLife(maxLife);
    }

    public void setMaxLife(int maxLife) {
        this.entityData.set(MAX_LIFE, maxLife);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MAX_LIFE, 10);
        this.entityData.define(PLAYER, Optional.empty());
    }

    @Override
    public void tick() {
        this.tickCount++;
        if (this.tickCount >= this.getMaxLife()) {
            this.remove();
        }
    }

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    @Override
    public boolean shouldRender(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_70112_1_) {
        return true;
    }

    public int getMaxLife() {
        return entityData.get(MAX_LIFE);
    }

    public PlayerEntity getPlayer() {
        Optional<UUID> uuid1 = this.entityData.get(PLAYER);
        return uuid1.map(value -> this.level.getPlayerByUUID(value)).orElse(null);
    }

    public void setPlayer(PlayerEntity player) {
        this.entityData.set(PLAYER, Optional.of(player.getUUID()));
    }
}
