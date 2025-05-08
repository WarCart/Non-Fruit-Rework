package net.warcar.non_fruit_rework.particles.effects;

import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

import static java.lang.Math.PI;

public class AdvancedGeppoParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
    public void spawn(Entity entity, World world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
        Vector3d vector = entity.getLookAngle().reverse();
        for(float theta = 0; theta < PI * 2; theta += (float) PI / 36f) {
            Vector3d rotating = new Vector3d(0.3, -0.1, 0);
            Vector3d rotated = rotating.scale(Math.cos(theta)).add(vector.cross(rotating).scale(Math.sin(theta))).add(vector.scale(vector.dot(rotating) * (1 - Math.cos(theta))));
            world.addParticle(ParticleTypes.CLOUD, true, posX, posY, posZ, rotated.x, rotated.y, rotated.z);
        }
    }
}
