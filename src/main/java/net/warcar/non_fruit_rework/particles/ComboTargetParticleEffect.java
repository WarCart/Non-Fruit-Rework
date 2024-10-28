package net.warcar.non_fruit_rework.particles;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.init.ReworkedParticleEffects;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ComboTargetParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
    public void spawn(Entity entity, World world, double posX, double posY, double posZ, NoDetails details) {
        SimpleParticleData particle = new SimpleParticleData(ReworkedParticleEffects.COMBO_MARK.get());
        particle.setLife(3);
        particle.setColor(1, 0, 0);
        particle.setFunction(EasingFunction.BOUNCE_IN_OUT);
        particle.setMotion(0, 0, 0);
        world.addParticle(particle, true, posX, posY + 0.75, posZ, 0.0, 0.0, 0.0);
    }
}
