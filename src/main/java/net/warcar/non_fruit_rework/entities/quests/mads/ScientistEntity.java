package net.warcar.non_fruit_rework.entities.quests.mads;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.server.SOpenScientistMenuPacket;
import net.warcar.non_fruit_rework.network.packets.server.SSyncNonFruitDataPacket;
import xyz.pixelatedw.mineminenomi.api.entities.TrainerEntity;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public abstract class ScientistEntity extends TrainerEntity {
    public ScientistEntity(EntityType type, World world, String name) {
        super(type, world, EntityHelper.getTexture(name));
        if (world != null && !world.isClientSide) {
            this.getEntityStats().setFaction(ModValues.CIVILIAN);
            this.getEntityStats().setFightingStyle(ModValues.DOCTOR);
            this.getEntityStats().setRace(ModValues.HUMAN);
            this.goalSelector.addGoal(1, new SwimGoal(this));
            this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.8));
            this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        }
    }

    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (hand != Hand.MAIN_HAND) {
            return ActionResultType.FAIL;
        } else {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.isEmpty() && stack.getItem() == Items.NAME_TAG) {
                return ActionResultType.FAIL;
            } else if (!player.level.isClientSide && !WyHelper.isInCombat(player)) {
                WyNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(player.getId(), EntityStatsCapability.get(player)), player);
                ModNetwork.sendToAllTrackingAndSelf(new SSyncNonFruitDataPacket(player.getId(), NonFruitDataCapability.get(player)), player);
                ModNetwork.sendTo(new SOpenScientistMenuPacket(this.getId()), player);
                return ActionResultType.PASS;
            } else {
                return ActionResultType.PASS;
            }
        }
    }
}
