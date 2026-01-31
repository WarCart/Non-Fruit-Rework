package net.warcar.non_fruit_rework.entities.seraphim;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.Optional;
import java.util.UUID;

public class CustomSeraphimEntity extends SeraphimEntity {
    private static final DataParameter<Optional<UUID>> CLONE_OF = EntityDataManager.defineId(CustomSeraphimEntity.class, DataSerializers.OPTIONAL_UUID);
    public static final EntityType<CustomSeraphimEntity> INSTANCE = WyRegistry.<CustomSeraphimEntity>createEntityType(CustomSeraphimEntity::new).noSummon().build("");
    private ResourceLocation pristineRace;

    public CustomSeraphimEntity(EntityType<CustomSeraphimEntity> type, World world) {
        super(type, world);
    }

    public CustomSeraphimEntity(World world, ResourceLocation pristineRace, PlayerEntity player) {
        this(INSTANCE, world);
        this.pristineRace = pristineRace;
        this.setCloneOf(player);
    }

    public CustomSeraphimEntity(World world, ResourceLocation resourceLocation, UUID uuid) {
        this(INSTANCE, world);
        this.pristineRace = resourceLocation;
        this.setCloneOf(uuid);
    }

    public void setCloneOf(PlayerEntity player) {
        this.entityData.set(CLONE_OF, Optional.of(player.getUUID()));
    }

    public void setCloneOf(UUID uuid) {
        this.entityData.set(CLONE_OF, Optional.of(uuid));
    }
}
