package net.warcar.non_fruit_rework.init;

import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.animations.OpenArmsAnimation;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ModAnims {
    public static final AnimationId<OpenArmsAnimation> OPEN_ARMS = register("open_arms");

    public static void clientInit() {
        AnimationId.register(new OpenArmsAnimation(OPEN_ARMS));
    }

    private static <A extends Animation<?, ?>> AnimationId<A> register(String name) {
        return new AnimationId<>(new ResourceLocation(NonFruitReworkMod.MOD_ID, name));
    }
}
