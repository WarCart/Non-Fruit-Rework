package net.warcar.non_fruit_rework.init;

import net.warcar.non_fruit_rework.animations.SanzenSekaiAnimation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ReworkedAnimations {
    public static void clientInit() {
        AnimationId.register(SanzenSekaiAnimation.INSTANCE);
    }
}
