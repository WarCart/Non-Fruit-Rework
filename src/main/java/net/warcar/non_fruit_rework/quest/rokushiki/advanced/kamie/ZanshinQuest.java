package net.warcar.non_fruit_rework.quest.rokushiki.advanced.kamie;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import net.warcar.non_fruit_rework.quest.objectives.TakeDamageObjective;
import net.warcar.non_fruit_rework.quest.rokushiki.KamieQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

public class ZanshinQuest extends Quest implements IHasRequirements {
    public static final QuestId<ZanshinQuest> INSTANCE = new QuestId.Builder<>("Trial: Kamie Zanshin", ZanshinQuest::new)
            .addRequirements(KamieQuest.INSTANCE).build();

    public ZanshinQuest(QuestId core) {
        super(core);
        ReachDorikiObjective objective1 = new ReachDorikiObjective("Get %s Doriki Strong", 1000);
        this.addObjective(objective1);
        this.addObjective(new CustomUseAbilityObjective(10, SoruAbility.INSTANCE).addRequirement(objective1));
        this.addObjective(new TakeDamageObjective("Take %s Damage", 175).addRequirement(objective1));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return EntityHelper.canUseAdvancedRokushiki(player);
    }
}
