package net.warcar.non_fruit_rework.entities.projectiles.rankyaku;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.AmaneDachiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;

public class AmaneDachiProjectile extends AbilityProjectileEntity {
    public AmaneDachiProjectile(EntityType type, World world) {
        super(type, world);
    }

    public AmaneDachiProjectile(World world, LivingEntity thrower) {
        super(AdvancedRankyakuProjectiles.AMANE_DACHI.get(), world, thrower, AmaneDachiAbility.INSTANCE);
        this.setDamage(100);
        this.setMaxLife(40);
        this.setBlocksAffectedLimit(4096);
        this.setPassThroughEntities();
        this.onBlockImpactEvent = this::onBlockImpactEvent;
    }

    private void onBlockImpactEvent(BlockPos hit) {
        ExplosionAbility explosion = super.createExplosion(this.getThrower(), this.level, hit.getX(), hit.getY(), hit.getZ(), 8);
        explosion.setStaticDamage(15.0F);
        explosion.setSmokeParticles(new CommonExplosionParticleEffect(8));
        explosion.doExplosion();
    }
}
