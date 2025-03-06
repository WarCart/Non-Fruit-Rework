package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.cyborg.CyborgBodyQuest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsBase;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;

@Mixin(EntityStatsBase.class)
public abstract class EntityDataMixin implements IEntityStats {
    @Shadow private LivingEntity owner;

    @Override
    public boolean isCyborg() {
        return QuestHelper.hasFinishedQuest(this.owner, CyborgBodyQuest.INSTANCE) || QuestHelper.isTrueRace(this.owner, "cyborg");
    }
}
