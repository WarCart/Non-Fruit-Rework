package net.warcar.non_fruit_rework.integrations.requirements;

import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.warcar.fruit_progression.requirements.Requirement;
import net.warcar.fruit_progression.requirements.RequirementInstance;
import net.warcar.non_fruit_rework.helpers.EntityHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class AnyRaceRequirement extends Requirement {
    public AnyRaceRequirement() {
        super(String.class);
    }

    @Override
    public boolean requirementMet(LivingEntity livingEntity, AbilityCore<?> abilityCore, RequirementInstance requirementInstance) {
        return EntityHelper.isAnyRace(livingEntity, new ResourceLocation(requirementInstance.getValues()[0]));
    }

    public RequirementInstance deserializeInstance(JsonObject json) {
        RequirementInstance instance = new RequirementInstance(this);
        instance.setValues(json.get("race").getAsString());
        new ResourceLocation(json.get("race").getAsString());
        return instance;
    }
}
