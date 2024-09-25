package net.warcar.non_fruit_rework.mixins.ccr;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

import java.util.function.Predicate;

@Mixin(ModEntityPredicates.class)
public class EntityPredicatesMixin {
    //@Inject(method = "getEnemyFactions", at = @At("RETURN"), remap = false, cancellable = true)
    private static void updatedFactions(LivingEntity entity, CallbackInfoReturnable<Predicate<Entity>> cir) {
        cir.setReturnValue(cir.getReturnValue().and(target -> target instanceof LivingEntity && !(EntityStatsCapability.get(((LivingEntity) target)).getFaction().equalsIgnoreCase("agent")
                && EntityStatsCapability.get(entity).isMarine() || EntityStatsCapability.get(entity).isBountyHunter())));
    }
}
