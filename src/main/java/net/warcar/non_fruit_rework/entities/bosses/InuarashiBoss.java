package net.warcar.non_fruit_rework.entities.bosses;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.entities.IHasImplantedSword;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.swordsman.SanbyakurokujuPoundHoWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.swordsman.ShiShishiSonsonWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.swordsman.YakkodoriWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

public class InuarashiBoss extends MinkDukeBoss<InuarashiBoss> implements IHasImplantedSword {
    public static final EntityType<InuarashiBoss> INSTANCE = WyRegistry.createEntityType(InuarashiBoss::new).build("");

    public InuarashiBoss(EntityType type, World world) {
        super(type, world);
    }

    public InuarashiBoss(InProgressChallenge inProgressChallenge) {
        super(INSTANCE, inProgressChallenge);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MinkDukeBoss.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 8.0);
    }

    @Override
    public void preInit() {
        this.entityStats.setRace(ModValues.MINK);
        this.entityStats.setSubRace(ModValues.MINK_DOG);
    }

    @Override
    public void initBoss() {
        super.initBoss();
        //Swordsman
        this.sulongPhase.addGoal(2, new ShiShishiSonsonWrapperGoal(this));
        this.finalPhase.addGoal(2, new ShiShishiSonsonWrapperGoal(this));
        this.sulongPhase.addGoal(2, new YakkodoriWrapperGoal(this));
        this.finalPhase.addGoal(2, new YakkodoriWrapperGoal(this));
        this.finalPhase.addGoal(2, new SanbyakurokujuPoundHoWrapperGoal(this));
    }

    protected void startFinalPhase() {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModWeapons.CUTLASS.get()));
    }

    @Override
    public void tick() {
        super.tick();
        float hpPercentage = this.getHealth() / this.getMaxHealth();
        if (hpPercentage < 0.25) {
            this.getPhaseManager().setPhase(finalPhase);
        } else if (hpPercentage < 0.5) {
            this.getPhaseManager().setPhase(sulongPhase);
        }
    }
}
