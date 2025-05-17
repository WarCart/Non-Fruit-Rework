package net.warcar.non_fruit_rework.quest.rokushiki.advanced.geppo;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.quest.objectives.CustomSurvivalObjective;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import net.warcar.non_fruit_rework.quest.objectives.RunObjective;
import net.warcar.non_fruit_rework.quest.rokushiki.GeppoQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.SoruQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

public class KamisoriQuest extends Quest implements IHasRequirements {
    public static final QuestId<KamisoriQuest> INSTANCE = new QuestId.Builder<>("Trial: Kamisori", KamisoriQuest::new)
            .addRequirements(GeppoQuest.INSTANCE, SoruQuest.INSTANCE).build();

    public KamisoriQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective = new ReachDorikiObjective("Get %s Doriki Strong", 1000);
        this.addObjective(objective);
        this.addObjective(new CustomSurvivalObjective(300).addRequirement(objective));
        this.addObjective(new RunObjective(1200).addRequirement(objective));
        this.addObjective(new CustomUseAbilityObjective(50, SoruAbility.INSTANCE).addRequirement(objective));
        this.addObjective(new CustomUseAbilityObjective(60, GeppoAbility.INSTANCE).addRequirement(objective));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return QuestHelper.canUseAdvancedRokushiki(player);
    }
}
