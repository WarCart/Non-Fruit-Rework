package net.warcar.non_fruit_rework.mixin.advanced_rokushiki;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.abilities.IHasQuestRequirement;
import net.warcar.non_fruit_rework.abilities.human.advanced_rokushiki.modes.RokuoganMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RokuoganAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility2;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.entities.projectiles.rokushiki.RokuoganProjectile;

@Mixin(RokuoganAbility.class)
public abstract class RokuoganMixin extends PunchAbility2 {
    @Shadow private ProjectileComponent projectileComponent;
    @Unique private final AltModeComponent<RokuoganMode> modeComponent = new AltModeComponent<>(this, RokuoganMode.class, RokuoganMode.SIMPLE);

    public RokuoganMixin(AbilityCore<? extends PunchAbility2> core) {
        super(core);
    }

    @Override
    public float getPunchDamage() {
        if (this.modeComponent.isMode(RokuoganMode.SAI_DAI_RIN)) {
            return 150;
        }
        return 60;
    }

    @Override
    public float getPunchCooldown() {
        if (this.modeComponent.isMode(RokuoganMode.SAI_DAI_RIN)) {
            return 1980;
        }
        return 700;
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void onConstruct(AbilityCore<RokuoganAbility> core, CallbackInfo ci) {
        this.addComponents(modeComponent);
        IHasQuestRequirement.addAltModeEvent(modeComponent);
    }

    @Inject(method = "createProjectile", at = @At("RETURN"), remap = false)
    private void onConstruct(LivingEntity entity, CallbackInfoReturnable<RokuoganProjectile> cir) {
        if (this.modeComponent.isMode(RokuoganMode.SAI_DAI_RIN)) {
            RokuoganProjectile projectile = cir.getReturnValue();
            projectile.onBlockImpactEvent = hit -> {
                ExplosionAbility explosion = projectile.createExplosion(entity, entity.level, projectile.getX(), projectile.getY(), projectile.getZ(), 2);
                explosion.setExplosionSound(true);
                explosion.setDamageOwner(false);
                explosion.setDestroyBlocks(true);
                explosion.setDamageEntities(true);
                explosion.setFireAfterExplosion(false);
                explosion.doExplosion();
            };
            projectile.setMaxLife(20);
            projectile.setBlocksAffectedLimit(1000);
        }
    }
}
