package net.warcar.non_fruit_rework.entities.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.abilities.test.IkokuAbility;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AbilityProjectileEntity;

public class IkokuProjectile extends AbilityProjectileEntity {
    public IkokuProjectile(EntityType type, World world) {
        super(type, world);
    }

    public IkokuProjectile(LivingEntity entity) {
        super(NonFruitProjectiles.IKOKU.get(), entity.level, entity, IkokuAbility.INSTANCE);
        this.setPassThroughEntities();
    }
}
