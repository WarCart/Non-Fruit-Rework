package net.warcar.non_fruit_rework.mixins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.warcar.non_fruit_rework.events.api.QuestFinishedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

@Mixin(Quest.class)
public class QuestMixin {
    @Shadow private QuestId core;

    @Inject(method = "triggerCompleteEvent", at = @At("TAIL"), remap = false)
    private void onComplete(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            MinecraftForge.EVENT_BUS.post(new QuestFinishedEvent(player, this.core));
        }
    }
}
