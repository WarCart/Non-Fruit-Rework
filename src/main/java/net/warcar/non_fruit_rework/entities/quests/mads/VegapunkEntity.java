package net.warcar.non_fruit_rework.entities.quests.mads;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.List;

public class VegapunkEntity extends ScientistEntity {
    public static final EntityType<VegapunkEntity> INSTANCE = WyRegistry.createEntityType(VegapunkEntity::new).sized(1, 3).build("");

    public VegapunkEntity(EntityType type, World world) {
        super(type, world, "vegapunk");
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE, 60.0).add(Attributes.MOVEMENT_SPEED, 0.24).add(Attributes.ATTACK_DAMAGE, 1).add(Attributes.MAX_HEALTH, 20.0);
    }

    @Override
    public List<QuestId> getAvailableQuests(PlayerEntity playerEntity) {
        return QuestHelper.getQuestsSorted(playerEntity);
    }
}
