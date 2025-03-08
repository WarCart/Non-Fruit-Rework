package net.warcar.non_fruit_rework.quest.electro;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import xyz.pixelatedw.mineminenomi.abilities.electro.EleclawAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalLunaAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalMissileAbility;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectricalTempestaAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;

public class ElectricalShowerQuest extends Quest implements IHasRequirements {
    public static final QuestId<ElectricalShowerQuest> INSTANCE = new QuestId.Builder<>("Trial: Electrical Shower", ElectricalShowerQuest::new)
            .addRequirements(ElectricalLunaQuest.INSTANCE).build();

    public ElectricalShowerQuest(QuestId<ElectricalShowerQuest> core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Reach %s doriki", 2000);
        this.addObjective(objective);
        Objective objective1 = new KillEntityObjective("Kill %s enemies using Electrical Luna", 25, SharedKillChecks.checkAbilitySource(ElectricalLunaAbility.INSTANCE)).addRequirement(objective);
        this.addObjective(objective1);
        Objective objective2 = new CustomUseAbilityObjective(15, ElectricalMissileAbility.INSTANCE).addRequirement(objective);
        this.addObjective(objective2);
        Objective objective3 = new CustomUseAbilityObjective(10, ElectricalTempestaAbility.INSTANCE).addRequirement(objective);
        this.addObjective(objective3);
        this.addObjective(new KillEntityObjective("Kill %s enemies using Eleclaw", 50, SharedKillChecks.checkAbilitySource(EleclawAbility.INSTANCE))
                .addRequirements(objective1, objective2, objective3));
    }

    @Override
    public boolean canGet(PlayerEntity player) {
        return QuestHelper.isAnyRace(player, "mink");
    }
}
