package net.warcar.non_fruit_rework.mixin.cartaddon;

import net.MrMagicalCart.cartaddon.init.CartAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.warcar.non_fruit_rework.init.ModEntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CartAttributes.class)
public class NoSizePleaseMixin {
    @Inject(method = "registerAttribute", at = @At("HEAD"), cancellable = true, remap = false)
    private static  <A extends Attribute> void registerAttribute(A attribute, CallbackInfoReturnable<A> cir) {
        if (attribute.getDescriptionId().equalsIgnoreCase("size")) {
            cir.setReturnValue((A) ModEntityAttributes.SIZE);
        }
    }
}
