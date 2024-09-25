package net.warcar.non_fruit_rework.data.world.marine;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.init.ModConfig;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MarineInfo extends WorldSavedData {
    public static final String ID = NonFruitReworkMod.MOD_ID + "_marine_data";
    protected List<UUID> admirals;
    protected List<UUID> fleetAdmirals;


    public MarineInfo() {
        super(ID);
        admirals = NonNullList.withSize(ModConfig.INSTANCE.getMaxAdmirals(), Util.NIL_UUID);
        fleetAdmirals = NonNullList.withSize(ModConfig.INSTANCE.getMaxFleetAdmirals(), Util.NIL_UUID);
    }

    public static MarineInfo get(IWorld level) {
        ServerWorld serverWorld;
        if (level instanceof ServerWorld) {
            serverWorld = ((ServerWorld) level).getServer().getLevel(World.OVERWORLD);
        } else {
            serverWorld = ServerLifecycleHooks.getCurrentServer().getLevel(World.OVERWORLD);
        }
        return serverWorld.getDataStorage().computeIfAbsent(MarineInfo::new, ID);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void load(CompoundNBT nbt) {
        ListNBT admiralsList = (ListNBT) nbt.get("admirals");
        this.admirals = NonNullList.withSize(ModConfig.INSTANCE.getMaxAdmirals(), Util.NIL_UUID);
        for (int i = 0; i < ModConfig.INSTANCE.getMaxAdmirals(); i++) {
            admirals.set(i, NBTUtil.loadUUID(admiralsList.get(i)));
        }
        ListNBT fleetAdmiralsList = (ListNBT) nbt.get("fleet_admirals");
        this.fleetAdmirals = NonNullList.withSize(ModConfig.INSTANCE.getMaxFleetAdmirals(), Util.NIL_UUID);
        for (int i = 0; i < ModConfig.INSTANCE.getMaxFleetAdmirals(); i++) {
            fleetAdmirals.set(i, NBTUtil.loadUUID(fleetAdmiralsList.get(i)));
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public CompoundNBT save(CompoundNBT nbt) {
        ListNBT admiralsList = new ListNBT();
        for (UUID uuid : admirals) {
            admiralsList.add(NBTUtil.createUUID(uuid));
        }
        nbt.put("admirals", admiralsList);

        ListNBT fleetAdmiralsList = new ListNBT();
        for (UUID uuid : fleetAdmirals) {
            fleetAdmiralsList.add(NBTUtil.createUUID(uuid));
        }
        nbt.put("fleet_admirals", fleetAdmiralsList);
        return nbt;
    }

    public boolean isAdmiral(PlayerEntity entity) {
        /*for (UUID uuid : this.admirals) {
            if (uuid.getLeastSignificantBits() == entity.getUUID().getLeastSignificantBits() && uuid.getMostSignificantBits() == entity.getUUID().getMostSignificantBits())
                return true;
        }*/
        return false;
    }

    public void addAdmiral(PlayerEntity player) {
        if (this.admirals.contains(Util.NIL_UUID)) {
            this.admirals.set(this.admirals.indexOf(Util.NIL_UUID), player.getUUID());
            this.setDirty();
        } else {
            NonFruitReworkMod.LOGGER.info("all oqupied");
        }
    }

    public void replaceAdmiral(PlayerEntity admiral, PlayerEntity player) {
        if (this.isAdmiral(admiral) && !this.isAdmiral(player)) {
            this.admirals.set(this.admirals.indexOf(admiral.getUUID()), player.getUUID());
            this.setDirty();
        }
    }


    public boolean isFleetAdmiral(PlayerEntity entity) {
        /*for (UUID uuid : this.fleetAdmirals) {
            if (uuid.getLeastSignificantBits() == entity.getUUID().getLeastSignificantBits() && uuid.getMostSignificantBits() == entity.getUUID().getMostSignificantBits())
                return true;
        }*/
        return false;
    }

    public void addFleetAdmiral(PlayerEntity player) {
        if (this.fleetAdmirals.contains(Util.NIL_UUID)) {
            this.fleetAdmirals.set(this.fleetAdmirals.indexOf(Util.NIL_UUID), player.getUUID());
            this.setDirty();
        }
    }

    public void replaceFleetAdmiral(PlayerEntity admiral, PlayerEntity player) {
        if (this.isAdmiral(admiral)) {
            this.fleetAdmirals.set(this.fleetAdmirals.indexOf(admiral.getUUID()), player.getUUID());
            this.setDirty();
        }
    }
}
