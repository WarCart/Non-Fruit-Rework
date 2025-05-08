package net.warcar.non_fruit_rework.entities.projectiles.rankyaku;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;

public class RankyakuRanProjectile extends AbilityProjectileEntity {
    public RankyakuRanProjectile(EntityType type, World world) {
        super(type, world);
    }

    public RankyakuRanProjectile(World world, LivingEntity thrower) {
        super(AdvancedRankyakuProjectiles.RANKYAKU_RAN.get(), world, thrower, RankyakuAbility.INSTANCE);
        this.setDamage(10.0F);
        this.setMaxLife(40);
        this.setBlocksAffectedLimit(256);
    }
}
