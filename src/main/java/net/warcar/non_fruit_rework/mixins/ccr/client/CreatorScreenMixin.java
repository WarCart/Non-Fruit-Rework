package net.warcar.non_fruit_rework.mixins.ccr.client;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.enums.Faction;
import net.warcar.non_fruit_rework.enums.Race;
import net.warcar.non_fruit_rework.enums.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.screens.CharacterCreatorScreen;

@Mixin(CharacterCreatorScreen.class)
public abstract class CreatorScreenMixin {
    @Shadow
    @Final
    private static final String[] FACTIONS = Faction.registryNames();

    @Shadow
    @Final
    private static final String[] RACES = Race.registryNames();

    @Shadow
    @Final
    private static final String[] STYLES = Style.registryNames();

    @Shadow private int page;

    @Shadow public abstract int getSelectedRaceId();

    @Shadow private ResourceLocation icon;

    @Shadow private int minkTextureId;

    @Inject(method = "isOnMinkPage", at = @At("HEAD"), remap = false, cancellable = true)
    private void isRealy(CallbackInfoReturnable<Boolean> cir) {
        if (this.getSelectedRaceId() != 0) cir.setReturnValue(this.page == 1 && Race.values()[this.getSelectedRaceId() - 1].hasSubRaces());
    }

    @Inject(method = "updateMinkSelection", at = @At("HEAD"), remap = false, cancellable = true)
    private void actualSelection(CallbackInfo callbackInfo) {
        callbackInfo.cancel();
        this.icon = Race.values()[this.getSelectedRaceId() - 1].getSubRaces()[this.minkTextureId].getIcon();
    }
}
