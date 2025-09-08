package net.warcar.non_fruit_rework.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.experiments.*;

public class ModExperimentResults {
    public static final DeferredRegister<ExperimentResult> REGISTRY = DeferredRegister.create(ModRegistries.EXPERIMENT_RESULTS, NonFruitReworkMod.MOD_ID);

    public static final RegistryObject<SapphireScalesResult> SAPPHIRE_SCALES = REGISTRY.register("sapphire_scales", SapphireScalesResult::new);
    public static final RegistryObject<StoneSkinResult> STONE_SKIN = REGISTRY.register("stone_skin", StoneSkinResult::new);
    public static final RegistryObject<BoozeAddictionResult> BOOZE_ADDICTION = REGISTRY.register("booze_addiction", BoozeAddictionResult::new);
    public static final RegistryObject<MildColdAdaptationResult> MILD_COLD_ADAPTATION = REGISTRY.register("mild_cold_adaptation", MildColdAdaptationResult::new);
    public static final RegistryObject<StrongColdAdaptationResult> STRONG_COLD_ADAPTATION = REGISTRY.register("strong_cold_adaptation", StrongColdAdaptationResult::new);
    public static final RegistryObject<NothingHappenedResult> NO_RESULT = REGISTRY.register("no_result", NothingHappenedResult::new);
    public static final RegistryObject<PoisonToleranceResult> POISON_TOLERANCE = REGISTRY.register("poison_tolerance", PoisonToleranceResult::new);
    public static final RegistryObject<TwoFruitsFailedResult> TWO_FRUITS_DEATH = REGISTRY.register("two_fruits_death", TwoFruitsFailedResult::new);
    public static final RegistryObject<CantSwimResult> CANT_SWIM = REGISTRY.register("cant_swim", CantSwimResult::new);
    public static final RegistryObject<GeneticDriftResult> GENETIC_DRIFT = REGISTRY.register("genetic_drift", GeneticDriftResult::new);

    public static void register(IEventBus bus) {
        REGISTRY.register(bus);
    }
}
