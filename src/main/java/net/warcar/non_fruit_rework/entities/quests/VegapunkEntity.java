package net.warcar.non_fruit_rework.entities.quests;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
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
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.init.ModQuests;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.server.SOpenVegapunkMenuPacket;
import xyz.pixelatedw.mineminenomi.api.entities.TrainerEntity;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.List;

public class VegapunkEntity extends TrainerEntity {
    public static final EntityType<VegapunkEntity> INSTANCE = WyRegistry.createEntityType(VegapunkEntity::new).build("");

    public VegapunkEntity(EntityType type, World world) {
        super(type, world);
        if (world != null && !world.isClientSide) {
            this.getEntityStats().setFaction("civilian");
            this.getEntityStats().setFightingStyle("doctor");
            this.getEntityStats().setRace("human");
            this.goalSelector.addGoal(1, new SwimGoal(this));
            this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.8));
            this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0F));
            this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        }
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 60.0).add(Attributes.MOVEMENT_SPEED, 0.24).add(Attributes.ATTACK_DAMAGE, 1).add(Attributes.MAX_HEALTH, 20.0);
    }

    @Override
    public List<QuestId> getAvailableQuests(PlayerEntity playerEntity) {
        return QuestHelper.getQuestsSorted(ModQuests.CYBORG_QUESTS, ModQuests.GEN_MODIFICATION_QUESTS);
    }

    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (hand != Hand.MAIN_HAND) {
            return ActionResultType.FAIL;
        } else {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.isEmpty() && stack.getItem() == Items.NAME_TAG) {
                return ActionResultType.FAIL;
            } else if (!player.level.isClientSide && !WyHelper.isInCombat(player)) {
                ModNetwork.sendTo(new SOpenVegapunkMenuPacket(this.getId()), player);
                return ActionResultType.PASS;
            } else {
                return ActionResultType.PASS;
            }
        }
    }
}
