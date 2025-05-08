package net.warcar.non_fruit_rework.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.particles.effects.AdvancedGeppoParticleEffect;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

public class ModParticles {
    public static final DeferredRegister<ParticleEffect<?>> PARTICLE_EFFECTS = DeferredRegister.create(ModRegistries.PARTICLE_EFFECTS, NonFruitReworkMod.MOD_ID);

    public static final AdvancedGeppoParticleEffect ADVANCED_GEPPO = registerParticleEffect("Advanced Geppo Particle Effect", new AdvancedGeppoParticleEffect());

    public static void register(IEventBus bus) {
        PARTICLE_EFFECTS.register(bus);
    }

    private static <E extends ParticleEffect<?>> E registerParticleEffect(String name, E effect) {
        String resourceName = WyHelper.getResourceName(name);
        PARTICLE_EFFECTS.register(resourceName, () -> effect);
        return effect;
    }
}
