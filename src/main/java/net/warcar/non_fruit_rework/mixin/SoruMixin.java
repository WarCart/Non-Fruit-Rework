package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.LivingEntity;
import net.warcar.non_fruit_rework.helpers.MiscHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;

@Mixin(SoruAbility.class)
public abstract class SoruMixin extends Ability {
    @Shadow
    @Final
    private StackComponent stackComponent = new StackComponent(this, 5) {
        @Override
        public void addStacks(LivingEntity entity, IAbility ability, int stacks) {
            if (!MiscHelper.isBerserk(entity)) {
                super.addStacks(entity, ability, stacks);
            } else {
                this.getAbility().getComponent(ModAbilityKeys.COOLDOWN).ifPresent(cooldownComponent1 -> cooldownComponent1.startCooldown(entity, 10));
            }
        }
    }.addStackChangeEvent(this::onStacksChange);

    @Shadow protected abstract void onStacksChange(LivingEntity entity, IAbility ability, int stacks);

    private SoruMixin(AbilityCore<? extends IAbility> core) {
        super(core);
    }
}
