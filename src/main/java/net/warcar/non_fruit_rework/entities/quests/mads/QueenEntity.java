package net.warcar.non_fruit_rework.entities.quests.mads;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.init.ModQuests;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.List;

public class QueenEntity extends ScientistEntity {
    public static final EntityType<QueenEntity> INSTANCE = WyRegistry.createEntityType(QueenEntity::new).sized(1, 3).build("");

    public QueenEntity(EntityType type, World world) {
        super(type, world, "queen");
        if (world != null && !world.isClientSide) {
            MobsHelper.addBasicNPCGoals(this);
        }
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 60.0).add(Attributes.MOVEMENT_SPEED, 0.24).add(Attributes.ATTACK_DAMAGE, 7).add(Attributes.MAX_HEALTH, 250.0);
    }

    @Override
    public List<QuestId> getAvailableQuests(PlayerEntity playerEntity) {
        return QuestHelper.getQuestsSorted(playerEntity, ModQuests.CYBORG_QUESTS);
    }
}
