package net.warcar.non_fruit_rework.entities.projectiles.rankyaku;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;

public class HakuraiProjectile extends AbilityProjectileEntity {
    public HakuraiProjectile(EntityType type, World world) {
        super(type, world);
    }

    public HakuraiProjectile(World world, LivingEntity thrower) {
        super(AdvancedRankyakuProjectiles.HAKURAI.get(), world, thrower, RankyakuAbility.INSTANCE);
        this.setDamage(60.0F);
        this.setMaxLife(40);
        this.setBlocksAffectedLimit(1024);
        this.setPassThroughEntities();
        this.setArmorPiercing(0.15f);
        this.onBlockImpactEvent = this::onBlockImpactEvent;
    }

    private void onBlockImpactEvent(BlockPos hit) {
        ExplosionAbility explosion = super.createExplosion(this.getThrower(), this.level, hit.getX(), hit.getY(), hit.getZ(), 5.0F);
        explosion.setStaticDamage(20.0F);
        explosion.setSmokeParticles(new CommonExplosionParticleEffect(5));
        explosion.doExplosion();
    }
}
