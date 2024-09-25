package net.warcar.non_fruit_rework.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsBase;

@Mixin(EntityStatsBase.class)
public class EntityStatsMixin {
    @Shadow private String race;

    @Inject(method = "isHuman", at = @At("RETURN"), remap = false, cancellable = true)
    private void orModified(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || this.race.equalsIgnoreCase("modified_human"));
    }
}
