package net.warcar.non_fruit_rework.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.screens.SelectHotbarAbilitiesScreen;

import java.util.List;
import java.util.Map;

@Mixin(SelectHotbarAbilitiesScreen.class)
public class SelectHotbarAbilitiesScreenMixin extends Screen {
    @Shadow @Final private static Map<AbilityCore<? extends IAbility>, List<ITextComponent>> TOOLTIPS_CACHE;

    private SelectHotbarAbilitiesScreenMixin(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void onInit(CallbackInfo ci) {
        TOOLTIPS_CACHE.clear();
    }
}
