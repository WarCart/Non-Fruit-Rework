package net.warcar.non_fruit_rework.quest.rokushiki.advanced.kamie;

import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import net.warcar.non_fruit_rework.quest.objectives.TakeDamageObjective;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

public class ZanshinQuest extends Quest {
    public static final QuestId<ZanshinQuest> INSTANCE = new QuestId.Builder<>("Trial: Kamie Zanshin", ZanshinQuest::new).build();

    public ZanshinQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 1000);
        this.addObjective(objective1);
        this.addObjective(new CustomUseAbilityObjective(10, SoruAbility.INSTANCE).addRequirement(objective1));
        this.addObjective(new TakeDamageObjective("Take %s Damage", 175).addRequirement(objective1));
    }
}
