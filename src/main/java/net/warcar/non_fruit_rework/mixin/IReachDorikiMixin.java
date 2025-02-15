package net.warcar.non_fruit_rework.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xyz.pixelatedw.mineminenomi.quests.objectives.ReachDorikiObjective;

@Mixin(ReachDorikiObjective.class)
public interface IReachDorikiMixin {
    @Accessor
    int getDoriki();
}
