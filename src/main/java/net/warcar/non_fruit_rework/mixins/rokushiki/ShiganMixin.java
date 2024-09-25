package net.warcar.non_fruit_rework.mixins.rokushiki;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.warcar.non_fruit_rework.quests.QuestProgression;
import net.warcar.non_fruit_rework.quests.rokushiki.advanced.ShiganOrenQuest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility2;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModDamageSource;

@Mixin(ShiganAbility.class)
public abstract class ShiganMixin extends PunchAbility2 {
    protected LivingEntity entity;

    private ShiganMixin(AbilityCore<? extends PunchAbility2> core) {
        super(core);
    }

    @Inject(method = "onHitEffect", at = @At("TAIL"), remap = false)
    private void saveEntity(LivingEntity entity, LivingEntity target, ModDamageSource source, CallbackInfo ci) {
        this.entity = entity;
    }

    @Inject(method = "getUseLimit", at = @At("HEAD"), remap = false, cancellable = true)
    private void useEntity(CallbackInfoReturnable<Integer> cir) {
        if (QuestProgression.hasFinishedQuest(ShiganOrenQuest.INSTANCE).canUnlock(this.entity)) {
            cir.setReturnValue((int) Math.max(EntityStatsCapability.get(this.entity).getDoriki() / 500, 3));
        }
    }
}
