package net.warcar.non_fruit_rework.entities.seraphim;

import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiEmissionWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiFullbodyHardeningWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.swordsman.*;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

public class SHawkEntity extends SeraphimEntity {
    public static final EntityType<SHawkEntity> INSTANCE = WyRegistry.createEntityType(SHawkEntity::new).fireImmune().build("");

    public SHawkEntity(EntityType<SHawkEntity> type, World world) {
        super(type, world);
        this.getEntityStats().setFaction(ModValues.MARINE);
        this.getEntityStats().setRace(ModValues.CYBORG);
        this.getEntityStats().setFightingStyle(ModValues.SWORDSMAN);
        this.getHakiData().setBusoshokuHakiExp(70);
        this.getHakiData().setKenbunshokuHakiExp(50);
        ItemStack yoru = new ItemStack(ModWeapons.YORU.get());
        yoru.getOrCreateTag().putBoolean("isClone", true);
        this.setItemSlot(EquipmentSlotType.MAINHAND, yoru);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        //Df
        MobsHelper.getDevilFruitAbilities(this, ModAbilities.SUPA_SUPA_NO_MI.getRegistryName()).ifPresent(abl -> abl.forEach(a -> goalSelector.addGoal(2, a)));
        //Haki
        this.goalSelector.addGoal(1, new BusoshokuHakiEmissionWrapperGoal(this));
        this.goalSelector.addGoal(1, new BusoshokuHakiFullbodyHardeningWrapperGoal(this));
        //Swordsman Abilities
        this.goalSelector.addGoal(2, new HiryuKaenWrapperGoal(this));
        this.goalSelector.addGoal(2, new ShiShishiSonsonWrapperGoal(this));
        this.goalSelector.addGoal(2, new YakkodoriWrapperGoal(this));
        this.goalSelector.addGoal(2, new OTatsumakiWrapperGoal(this));
        this.goalSelector.addGoal(2, new SanbyakurokujuPoundHoWrapperGoal(this));
    }
}
