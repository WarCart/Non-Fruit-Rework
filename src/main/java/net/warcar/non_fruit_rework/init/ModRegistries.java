package net.warcar.non_fruit_rework.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import net.warcar.non_fruit_rework.experiments.ExperimentResult;

public class ModRegistries {
    public static final IForgeRegistry<ExperimentResult> EXPERIMENT_RESULTS = create("experiment", ExperimentResult.class);

    private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> create(String name, Class<T> type) {
        RegistryBuilder<T> builder = new RegistryBuilder<>();
        return builder.setName(new ResourceLocation(name)).setType(type).setMaxID(2147483646).create();
    }
}
