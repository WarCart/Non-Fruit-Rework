package net.warcar.non_fruit_rework.mixin;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectroHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

@Mixin(ElectroHelper.class)
public class DimSulongMixin {
    @Inject(method = "canTransform", at = @At("RETURN"), cancellable = true, remap = false)
    private static void transformInChallenges(World world, CallbackInfoReturnable<Boolean> cir) {
        if (WyHelper.isInChallengeDimension(world)) {
            cir.setReturnValue(true);
        }
    }
}
