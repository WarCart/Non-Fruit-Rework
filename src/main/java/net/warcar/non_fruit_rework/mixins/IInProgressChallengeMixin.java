package net.warcar.non_fruit_rework.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;

@Mixin(InProgressChallenge.class)
public interface IInProgressChallengeMixin {
    @Accessor
    Challenge getChallenge();
}
