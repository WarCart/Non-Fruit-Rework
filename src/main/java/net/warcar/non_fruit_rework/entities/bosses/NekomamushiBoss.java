package net.warcar.non_fruit_rework.entities.bosses;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.entities.mobs.goals.abilities.rokushiki.SoruWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

public class NekomamushiBoss extends MinkDukeBoss<NekomamushiBoss> {
    public static final EntityType<NekomamushiBoss> INSTANCE = WyRegistry.createEntityType(NekomamushiBoss::new).build("");

    public NekomamushiBoss(EntityType type, World world) {
        super(type, world);
    }

    public NekomamushiBoss(InProgressChallenge inProgressChallenge) {
        super(INSTANCE, inProgressChallenge);
    }

    @Override
    public void preInit() {
        this.entityStats.setRace(ModValues.MINK);
        this.entityStats.setSubRace(ModValues.MINK_LION);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MinkDukeBoss.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    protected void startFinalPhase() {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModWeapons.BISENTO.get()));
    }

    @Override
    public void initBoss() {
        super.initBoss();
        //Generics
        this.goalSelector.addGoal(3, new SoruWrapperGoal(this));
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
