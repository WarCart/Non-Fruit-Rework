package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.data.entity.medical_data.MedicalDataCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.electro.ElectroHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUseResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

@Mixin(ElectroHelper.class)
public class ElectroHelperMixin {
    @Inject(method = "canTransform", at = @At("RETURN"), cancellable = true, remap = false)
    private static void transformInChallenges(World world, CallbackInfoReturnable<Boolean> cir) {
        if (WyHelper.isInChallengeDimension(world)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "canTransformInSulong", at = @At("HEAD"), remap = false, cancellable = true)
    private static void sulongBalls(LivingEntity entity, IAbility ability, CallbackInfoReturnable<AbilityUseResult> cir) {
        if (MedicalDataCapability.get(entity).getSulongBallTicks() > 0) {
            cir.setReturnValue(AbilityUseResult.success());
        }
    }
}
