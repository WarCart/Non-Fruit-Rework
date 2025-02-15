package net.warcar.non_fruit_rework.entities.seraphim;

import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.helpers.DFHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiEmissionWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.haki.BusoshokuHakiImbuingWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.swordsman.*;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

public class SHawkEntity extends SeraphimEntity {
    public static final EntityType<SHawkEntity> INSTANCE = WyRegistry.createEntityType(SHawkEntity::new).fireImmune().build("");

    public SHawkEntity(EntityType<SHawkEntity> type, World world) {
        super(type, world);
        this.getEntityStats().setFaction("marine");
        this.getEntityStats().setRace("human");
        this.getEntityStats().setFightingStyle("swordsman");
        //this.getDevilFruit().setDevilFruit(ModAbilities.SUPA_SUPA_NO_MI);
        this.getHakiData().setBusoshokuHakiExp(70);
        this.getHakiData().setKenbunshokuHakiExp(50);
        //registerGoals();
        ItemStack yoru = new ItemStack(ModWeapons.YORU.get());
        yoru.getOrCreateTag().putBoolean("isClone", true);
        this.setItemSlot(EquipmentSlotType.MAINHAND, yoru);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        //Df
        DFHelper.addDfMoves(this, ModAbilities.SUPA_SUPA_NO_MI);
        //Haki
        this.goalSelector.addGoal(1, new BusoshokuHakiEmissionWrapperGoal(this));
        this.goalSelector.addGoal(1, new BusoshokuHakiImbuingWrapperGoal(this));
        //Swordsman Abilities
        this.goalSelector.addGoal(2, new HiryuKaenWrapperGoal(this));
        this.goalSelector.addGoal(2, new ShiShishiSonsonWrapperGoal(this));
        this.goalSelector.addGoal(2, new YakkodoriWrapperGoal(this));
        this.goalSelector.addGoal(2, new OTatsumakiWrapperGoal(this));
        this.goalSelector.addGoal(2, new SanbyakurokujuPoundHoWrapperGoal(this));
    }
}
