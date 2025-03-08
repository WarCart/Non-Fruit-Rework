package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.helpers.IHasRequirements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

@Mixin(ItemsHelper.class)
public class ItemsHelperMixin {
    private static World world;
    private static Vector3d pos;

    @Inject(method = "lambda$dropWantedPosters$12", at = @At("HEAD"), cancellable = true, remap = false)
    private static void modifyRules(ChallengeCore core, CallbackInfoReturnable<Boolean> cir) {
        if (core.createChallenge() instanceof IHasRequirements) {
            PlayerEntity player = WyHelper.getEntitiesNearSphere(pos, world, 10, null, PlayerEntity.class).stream().findFirst().orElse(null);
            cir.setReturnValue(((IHasRequirements) core).canGet(player));
        }
    }

    @Inject(method = "dropWantedPosters", at = @At("HEAD"), remap = false)
    private static void saveVals(World world, Vector3d pos, CallbackInfo ci) {
        ItemsHelperMixin.world = world;
        ItemsHelperMixin.pos = pos;
    }
}
