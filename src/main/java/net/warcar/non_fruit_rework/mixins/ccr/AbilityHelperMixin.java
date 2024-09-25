package net.warcar.non_fruit_rework.mixins.ccr;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.warcar.non_fruit_rework.enums.Faction;
import net.warcar.non_fruit_rework.enums.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

@Mixin(AbilityHelper.class)
public class AbilityHelperMixin {
    @Inject(method = "canUseSwordsmanAbilities*", at = @At("RETURN"), remap = false, cancellable = true)
    private static void moreSwordsmanFreedom(LivingEntity entity, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(info.getReturnValue() || ItemsHelper.isSword(entity.getOffhandItem()) || ItemsHelper.isSword(entity.getItemBySlot(EquipmentSlotType.HEAD)));
    }
}
