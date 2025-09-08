package net.warcar.non_fruit_rework.effects;

import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ModEffect;

public class SapphireScalesEffect extends ModEffect implements ITextureOverlayEffect {
    public SapphireScalesEffect() {
        super(EffectType.HARMFUL, 0x000080);
    }

    @Override
    public boolean shouldUpdateClient() {
        return true;
    }

    @Override
    public ResourceLocation getBodyTexture(int duration, int amplifier) {
        switch (amplifier) {
            case 2:
                return new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/entities/effects/sapphire_scales_2.png");
            case 1:
                return new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/entities/effects/sapphire_scales_1.png");
            case 0:
            default:
                return new ResourceLocation(NonFruitReworkMod.MOD_ID, "textures/entities/effects/sapphire_scales_0.png");
        }
    }

    @Override
    public ResourceLocation getViewTexture(int duration, int amplifier) {
        return null;
    }
}
