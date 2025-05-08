package net.warcar.non_fruit_rework.quest.rokushiki.advanced.shigan;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.rokushiki.ShiganQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class TobuShiganQuest extends Quest implements IHasRequirements {
    public static final QuestId<TobuShiganQuest> INSTANCE = new QuestId.Builder<>("Trial: Tobu Shigan", TobuShiganQuest::new)
            .addRequirements(ShiganQuest.INSTANCE).build();

    public TobuShiganQuest(QuestId core) {
        super(core);
        this.addObjective(new KillEntityObjective("Kill %s enemies using Shigan", 45, SharedKillChecks.checkAbilitySource(ShiganAbility.INSTANCE)));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return QuestHelper.canUseAdvancedRokushiki(player);
    }
}