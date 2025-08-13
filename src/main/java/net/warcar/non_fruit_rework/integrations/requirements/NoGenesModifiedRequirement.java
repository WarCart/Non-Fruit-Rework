package net.warcar.non_fruit_rework.integrations.requirements;

import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.warcar.fruit_progression.requirements.Requirement;
import net.warcar.fruit_progression.requirements.RequirementInstance;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class NoGenesModifiedRequirement extends Requirement {
    @Override
    public boolean requirementMet(LivingEntity livingEntity, AbilityCore<?> abilityCore, RequirementInstance requirementInstance) {
        return EntityHelper.noGeneModifications(livingEntity);
    }

    @Override
    public RequirementInstance deserializeInstance(JsonObject jsonObject) {
        return new RequirementInstance(this);
    }
}
