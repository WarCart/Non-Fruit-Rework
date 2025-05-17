package net.warcar.non_fruit_rework.mixin;

import net.warcar.non_fruit_rework.config.CommonConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.charactercreator.ICharacterCreatorEntry;
import xyz.pixelatedw.mineminenomi.api.charactercreator.RaceId;
import xyz.pixelatedw.mineminenomi.init.ModValues;

@Mixin(RaceId.class)
public abstract class RaceIdMixin implements ICharacterCreatorEntry {
    @Inject(method = "isInBook", at = @At("RETURN"), remap = false, cancellable = true)
    private void isInBook(CallbackInfoReturnable<Boolean> cir) {
        if (this.getRegistryName().equals(ModValues.CYBORG)) {
            cir.setReturnValue(cir.getReturnValueZ() && !CommonConfig.INSTANCE.isCustomRaces());
        }
    }
}
