package net.warcar.non_fruit_rework.animations;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SanzenSekaiAnimation extends Animation<LivingEntity, BipedModel<LivingEntity>> {
    public static final AnimationId<SanzenSekaiAnimation> INSTANCE = new AnimationId<>(new ResourceLocation(NonFruitReworkMod.MOD_ID, "sanzen_sekai"));

    public SanzenSekaiAnimation(AnimationId<SanzenSekaiAnimation> animId) {
        super(animId);
    }

    public void setupAnimation(LivingEntity playerEntity, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, float v, float v1) {
    }

    @Override
    public void setAnimationAngles(LivingEntity player, BipedModel model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.leftArm.xRot = (float) -Math.PI / 2;
        model.leftArm.yRot = 0;
        model.leftArm.x = 1;
        model.leftArm.z -= 2;
        model.leftArm.zRot = (float) Math.PI / 2 + ageInTicks;
        model.rightArm.xRot = (float) -Math.PI / 2;
        model.rightArm.yRot = 0;
        model.rightArm.x = -1;
        model.rightArm.z -= 2;
        model.rightArm.zRot = ageInTicks;
    }

    @Override
    public void setupHeldItem(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, HandSide handSide, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i) {
        matrixStack.mulPose(Vector3f.YN.rotationDegrees(90));
    }
}
