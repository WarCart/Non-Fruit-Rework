package net.warcar.non_fruit_rework.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

@Mixin(AbilityCore.class)
public interface IAbilityCoreMixin {
    @Accessor
    void setUnlockCheck(AbilityCore.ICanUnlock check);
}
