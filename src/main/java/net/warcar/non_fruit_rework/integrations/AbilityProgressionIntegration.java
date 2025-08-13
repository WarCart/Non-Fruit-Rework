package net.warcar.non_fruit_rework.integrations;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.fruit_progression.init.ModRegistry;
import net.warcar.fruit_progression.new_data_reader.AbilityDataReader;
import net.warcar.fruit_progression.requirements.Requirement;
import net.warcar.fruit_progression.requirements.RequirementSetInstance;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.api.events.CanUnlockQuestEvent;
import net.warcar.non_fruit_rework.integrations.requirements.AnyRaceRequirement;
import net.warcar.non_fruit_rework.integrations.requirements.HybridRaceRequirement;
import net.warcar.non_fruit_rework.integrations.requirements.NoGenesModifiedRequirement;

public class AbilityProgressionIntegration {
    public static final DeferredRegister<Requirement> REQUIREMENTS = DeferredRegister.create(ModRegistry.REQUIREMENTS, NonFruitReworkMod.MOD_ID);
    public static final AbilityDataReader<RequirementSetInstance> QUESTS = new AbilityDataReader<>("quests", RequirementSetInstance::getRequirementSetInstance);

    public static void register(IEventBus bus) {
        REQUIREMENTS.register(bus);
        REQUIREMENTS.register("hybrid_race", HybridRaceRequirement::new);
        REQUIREMENTS.register("any_race", AnyRaceRequirement::new);
        REQUIREMENTS.register("genome_untouched", NoGenesModifiedRequirement::new);
        MinecraftForge.EVENT_BUS.register(AbilityProgressionIntegration.class);
    }

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(QUESTS);
    }

    @SubscribeEvent
    public static void onQuestUnlock(CanUnlockQuestEvent event) {
        ResourceLocation location = event.getUnlockable().getRegistryName();
        LivingEntity entity = event.getEntityLiving();
        if (location != null && QUESTS.map.containsKey(location)) {
            RequirementSetInstance requirementSetInstance = QUESTS.map.get(location);
            if (requirementSetInstance != null) {
                if (requirementSetInstance.isFulfilled(entity, null)) {
                    event.setResult(Event.Result.ALLOW);
                } else {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
