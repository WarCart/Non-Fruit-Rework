package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.mixins.RangedAttributeMixin;

@Mixin(ModAttributes.class)
public class ModAttributesMixin {
    @Inject(method = "lambda$static$9", at = @At("TAIL"), cancellable = true, remap = false)
    private static void infiniteToughness(CallbackInfoReturnable<Attribute> cir) {
        ((RangedAttributeMixin) cir.getReturnValue()).setMaxValue(2000);
    }
}
