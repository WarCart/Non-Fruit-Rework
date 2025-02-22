package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.MiscHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;

@Mixin(GeppoAbility.class)
public abstract class GeppoMixin extends Ability {
    @Shadow
    @Final
    private StackComponent stackComponent = new StackComponent(this) {
        @Override
        public void addStacks(LivingEntity entity, IAbility ability, int stacks) {
            if (!MiscHelper.isBerserk(entity)) {
                super.addStacks(entity, ability, stacks);
            }
        }
    };

    private GeppoMixin(AbilityCore<? extends IAbility> core) {
        super(core);
    }
}
