package net.warcar.non_fruit_rework.quest.rokushiki.advanced.soru;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import net.warcar.non_fruit_rework.helpers.interfaces.IHasRequirements;
import net.warcar.non_fruit_rework.quest.objectives.CustomUseAbilityObjective;
import net.warcar.non_fruit_rework.quest.objectives.TimedAbilityUseObjective;
import net.warcar.non_fruit_rework.quest.rokushiki.SoruQuest;
import net.warcar.non_fruit_rework.quest.rokushiki.TekkaiQuest;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public class TekkaiDamaQuest extends Quest implements IHasRequirements {
    public static final QuestId<TekkaiDamaQuest> INSTANCE = new QuestId.Builder<>("Trial: Tekkai Dama", TekkaiDamaQuest::new)
            .addRequirements(TekkaiQuest.INSTANCE, SoruQuest.INSTANCE).build();

    public TekkaiDamaQuest(QuestId core) {
        super(core);
        this.addObjective(new CustomUseAbilityObjective(50, SoruAbility.INSTANCE));
        this.addObjective(new TimedAbilityUseObjective("Use tekkai for %s seconds", TekkaiAbility.INSTANCE, 500));
    }

    @Override
    public boolean canGet(LivingEntity player) {
        return EntityHelper.canUseAdvancedRokushiki(player);
    }
}
