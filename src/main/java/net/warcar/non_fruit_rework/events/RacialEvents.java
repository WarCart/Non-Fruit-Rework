package net.warcar.non_fruit_rework.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.init.ModEntityAttributes;
import xyz.pixelatedw.mineminenomi.api.events.SetCameraZoomEvent;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID)
public class RacialEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void hitboxRescale(EntityEvent.Size event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            try {
                float size = Math.abs(MathHelper.clamp((float) entity.getAttributeValue(ModEntityAttributes.SIZE), -100f, 100f));
                event.setNewSize(event.getNewSize().scale(size), true);
                event.setNewEyeHeight(event.getNewSize().height * 0.85f);
            } catch (NullPointerException ignored) {
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void preRender(RenderPlayerEvent.Pre event) {
        MatrixStack stack = event.getMatrixStack();
        stack.pushPose();
        try {
            float size = (float) event.getPlayer().getAttributeValue(ModEntityAttributes.SIZE);
            stack.scale(size, size, size);
            if (size < 0) {
                stack.translate(0, -2, 0);
            }
        } catch (NullPointerException ignored) {
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void postRender(RenderPlayerEvent.Post event) {
        MatrixStack stack = event.getMatrixStack();
        stack.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraZoom(SetCameraZoomEvent event) {
        ClientPlayerEntity entity = event.getPlayer();
        try {
            double size = Math.abs(entity.getAttributeValue(ModEntityAttributes.SIZE));
            event.setZoom(event.getZoom() * size);
            if (size != 1) {
                event.setResult(Event.Result.ALLOW);
            }
        } catch (NullPointerException ignored) {
        }
    }
}
