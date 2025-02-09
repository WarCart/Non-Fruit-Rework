package net.warcar.non_fruit_rework.quest.objectives;

import net.minecraft.util.text.TranslationTextComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.quests.objectives.UseAbilityObjective;

public class CustomUseAbilityObjective extends UseAbilityObjective {
    private final int count;
    private final AbilityCore ability;

    public CustomUseAbilityObjective(int count, AbilityCore ability) {
        super("Use %s %s times", count, ability);
        this.count = count;
        this.ability = ability;
    }

    @Override
    public String getLocalizedTitle() {
        return new TranslationTextComponent("quest.objective.mineminenomi.use_%s_%s_times", this.ability.getLocalizedName(), this.getUsesNeeded()).getString();
    }

    public int getUsesNeeded() {
        return count;
    }
}
