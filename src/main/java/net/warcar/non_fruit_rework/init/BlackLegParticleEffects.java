package net.warcar.non_fruit_rework.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.warcar.non_fruit_rework.particles.IfritJambeParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

public class BlackLegParticleEffects {
    public static void register(IEventBus bus) {}
    public static final RegistryObject<ParticleEffect<?>> IFRIT_JAMBE = WyRegistry.registerParticleEffect("Ifrit Jambe", IfritJambeParticleEffect::new);
}
