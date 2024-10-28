package net.warcar.non_fruit_rework.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.particles.ComboTargetParticleEffect;
import net.warcar.non_fruit_rework.particles.IfritJambeParticleEffect;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticle;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReworkedParticleEffects {
    public static final RegistryObject<ParticleEffect<?>> IFRIT_JAMBE = WyRegistry.registerParticleEffect("Ifrit Jambe", IfritJambeParticleEffect::new);
    public static final RegistryObject<ParticleEffect<?>> COMBO_ABILITY_TARGET = WyRegistry.registerParticleEffect("Combo Target Particle", ComboTargetParticleEffect::new);
    public static final RegistryObject<ParticleType<SimpleParticleData>> COMBO_MARK = WyRegistry.registerParticleType("Combo Ability Target", SimpleParticleData::new);


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleManager manager = Minecraft.getInstance().particleEngine;
        manager.register(COMBO_MARK.get(), new SimpleParticle.Factory(new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/particle/combo_ability_target.png")));
    }
}
