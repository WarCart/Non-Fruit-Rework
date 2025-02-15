package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.entities.IHasImplantedSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUseResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

@Mixin(AbilityHelper.class)
public class AbilityHelperMixin {
    @Inject(method = "canUseSwordsmanAbilities(Lnet/minecraft/entity/LivingEntity;Lxyz/pixelatedw/mineminenomi/api/abilities/IAbility;)Lxyz/pixelatedw/mineminenomi/api/abilities/AbilityUseResult;", at = @At("HEAD"), remap = false, cancellable = true)
    private static void oneHandedCombat(LivingEntity entity, IAbility ability, CallbackInfoReturnable<AbilityUseResult> cir) {
        if (entity instanceof IHasImplantedSword) {
            cir.setReturnValue(AbilityUseResult.success());
        }
    }
}
