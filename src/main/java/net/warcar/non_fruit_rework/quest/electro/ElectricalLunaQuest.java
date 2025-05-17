package net.warcar.non_fruit_rework.quest.electro;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalMissileAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalTempestaAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class ElectricalLunaQuest extends Quest implements IHasRequirements {
    public static final QuestId<ElectricalLunaQuest> INSTANCE = new QuestId.Builder<>("Trial: Electrical luna", ElectricalLunaQuest::new)
            .addRequirements(ElectricalMissileQuest.INSTANCE, ElectricalTempestaQuest.INSTANCE).build();

    public ElectricalLunaQuest(QuestId<ElectricalLunaQuest> core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Reach %s doriki", 720);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s enemies using Electrical Tempesta", 5, SharedKillChecks.checkAbilitySource(ElectricalTempestaAbility.INSTANCE)).addRequirement(objective));
        this.addObjective(new CustomUseAbilityObjective(5, ElectricalMissileAbility.INSTANCE).addRequirement(objective));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return QuestHelper.isAnyRace(player, ModValues.MINK);
    }
}
