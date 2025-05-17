package net.warcar.non_fruit_rework.quest.electro;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.RunObjective;
import xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.quests.objectives.HitEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedHitChecks;

public class ElectricalMissileQuest extends Quest implements IHasRequirements {
    public static final QuestId<ElectricalMissileQuest> INSTANCE = new QuestId.Builder<>("Trial: Electrical missile", ElectricalMissileQuest::new).build();

    public ElectricalMissileQuest(QuestId<ElectricalMissileQuest> core) {
        super(core);
        this.addObjective(new HitEntityObjective("Hit %s enemies using Eleclaw", 25, SharedHitChecks.checkAbilitySource(EleclawAbility.INSTANCE)));
        this.addObjective(new RunObjective(600));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return QuestHelper.isAnyRace(player, ModValues.MINK);
    }
}
