package net.warcar.non_fruit_rework.particles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

public class IfritJambeParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {

    public IfritJambeParticleEffect() {
    }

    public void spawn(Entity entity, World world, double posX, double posY, double posZ, NoDetails details) {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)entity;
            float f = living.yBodyRotO + (living.yBodyRot - living.yBodyRotO) + 30.0F;
            double x = (double) MathHelper.sin(f * 3.1415927F / -180.0F) * 0.2D;
            double z = (double)MathHelper.cos(f * 3.1415927F / -180.0F) * 0.2D;
            double offsetX = x + WyHelper.randomDouble() / 5.0D;
            double offsetY = 0.6D + WyHelper.randomDouble() / 2.5D;
            double offsetZ = z + WyHelper.randomDouble() / 5.0D;
            int age = (int)(3.0D + WyHelper.randomWithRange(0, 2));
            SimpleParticleData data = new SimpleParticleData(ModParticleTypes.BLUE_FLAME.get());
            data.setLife(age);
            data.setSize((float)age / 2.5F);
            data.setMotion(0.0D, 0.002D, 0.0D);
            world.addParticle(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, 0.0D, 0.0D, 0.0D);
        }

    }
}
