package net.warcar.non_fruit_rework.quest.electro;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalMissileAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class ElectricalTempestaQuest extends Quest implements IHasRequirements {
    public static final QuestId<ElectricalTempestaQuest> INSTANCE = new QuestId.Builder<>("Trial: Electrical Tempesta", ElectricalTempestaQuest::new).build();

    public ElectricalTempestaQuest(QuestId<ElectricalTempestaQuest> core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Reach %s doriki", 600);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s enemies using Eleclaw", 15, SharedKillChecks.checkAbilitySource(EleclawAbility.INSTANCE)).addRequirement(objective));
        this.addObjective(new CustomUseAbilityObjective(5, ElectricalMissileAbility.INSTANCE).addRequirement(objective));
    }

    @Override
    public boolean canGet(PlayerEntity player) {
        return QuestHelper.isAnyRace(player, "mink");
    }
}
