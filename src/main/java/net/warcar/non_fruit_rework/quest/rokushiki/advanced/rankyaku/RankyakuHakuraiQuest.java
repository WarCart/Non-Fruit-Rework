package net.warcar.non_fruit_rework.quest.rokushiki.advanced.rankyaku;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.rokushiki.RankyakuQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.KillEntityObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.quests.objectives.SharedKillChecks;
import xyz.pixelatedw.mineminenomi.quests.objectives.UseAbilityObjective;

public class RankyakuHakuraiQuest extends Quest implements IHasRequirements {
    public static final QuestId<RankyakuHakuraiQuest> INSTANCE = new QuestId.Builder<>("Trial: Rankyaku Hakurai", RankyakuHakuraiQuest::new)
            .addRequirements(RankyakuQuest.INSTANCE).build();

    public RankyakuHakuraiQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 2000);
        this.addObjective(objective);
        this.addObjective(new KillEntityObjective("Kill %s enemies using Rankyaku", 25, SharedKillChecks.checkAbilitySource(RankyakuAbility.INSTANCE)).addRequirement(objective));
        this.addObjective(new UseAbilityObjective("Use Geppo %s times", 10, GeppoAbility.INSTANCE).addRequirement(objective));
        this.addObjective(new UseAbilityObjective("Use Rankyaku %s times", 35, RankyakuAbility.INSTANCE).addRequirement(objective));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return QuestHelper.canUseAdvancedRokushiki(player);
    }
}