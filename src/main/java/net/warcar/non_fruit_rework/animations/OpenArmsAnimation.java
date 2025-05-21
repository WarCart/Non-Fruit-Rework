package net.warcar.non_fruit_rework.animations;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

//"Go ahead hit me" ahh animation
public class OpenArmsAnimation extends Animation<LivingEntity, AgeableModel> {
    public OpenArmsAnimation(AnimationId<OpenArmsAnimation> animId) {
        super(animId);
        this.setAnimationAngles(this::angles);
    }

    public void angles(LivingEntity entity, AgeableModel model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ModelRenderer rightArm = null;
        ModelRenderer leftArm = null;
        if (model instanceof BipedModel) {
            BipedModel<?> biped = (BipedModel)model;
            rightArm = biped.rightArm;
            leftArm = biped.leftArm;
        } else if (model instanceof DugongModel) {
            DugongModel<?> dugong = (DugongModel)model;
            rightArm = dugong.rightArm;
            leftArm = dugong.leftArm;
            rightArm.z = -4.0F;
            leftArm.z = -4.0F;
        }

        if (leftArm != null) {
            leftArm.zRot = -0.8F;
        }

        if (rightArm != null) {
            rightArm.zRot = 0.8F;
        }

    }
}