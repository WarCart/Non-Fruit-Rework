package net.warcar.non_fruit_rework.models.stealth_black;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.ParametersAreNonnullByDefault;

public class StealthBlackChestModel<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer Body;
    private final ModelRenderer cape;
    private final ModelRenderer RightArm;
    private final ModelRenderer LeftArm;

    public StealthBlackChestModel() {
        super(-0.5f);
        texWidth = 128;
        texHeight = 128;

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 0.0F, 0.0F);
        Body.texOffs(0, 10).addBox(-4.0F, 0.25F, -2.0F, 8.0F, 6.0F, 4.0F, 0.3F, false);
        Body.texOffs(0, 0).addBox(-4.0F, 6.0F, -2.0F, 8.0F, 6.0F, 4.0F, 0.1F, false);
        Body.texOffs(57, 28).addBox(2.85F, 8.25F, -2.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(14, 54).addBox(1.6F, 8.25F, -2.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(18, 65).addBox(-3.85F, 8.25F, -2.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(53, 28).addBox(-2.6F, 8.25F, -2.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(65, 15).addBox(-3.85F, 8.25F, 1.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(0, 44).addBox(-2.6F, 8.25F, 1.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(0, 36).addBox(-1.35F, 8.25F, 1.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(0, 47).addBox(1.6F, 8.25F, 1.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(20, 10).addBox(0.35F, 8.25F, 1.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(53, 21).addBox(2.85F, 8.25F, 1.25F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(40, 6).addBox(3.35F, 8.25F, 0.85F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(38, 26).addBox(3.35F, 8.25F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(38, 20).addBox(3.35F, 8.25F, -1.85F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(62, 52).addBox(-4.35F, 8.25F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(10, 65).addBox(-4.35F, 8.25F, -1.85F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(14, 65).addBox(-4.35F, 8.25F, 0.85F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(38, 23).addBox(-0.5F, 8.75F, -2.15F, 1.0F, 1.0F, 1.0F, 0.2F, false);
        Body.texOffs(21, 36).addBox(-0.95F, 8.3F, -2.351F, 2.0F, 2.0F, 0.0F, 0.0F, false);
        Body.texOffs(8, 54).addBox(6.0F, -5.0F, 3.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
        Body.texOffs(0, 54).addBox(4.0F, -1.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Body.texOffs(53, 21).addBox(-5.0F, -1.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Body.texOffs(47, 41).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(50, 65).addBox(4.0F, 0.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(54, 65).addBox(-5.0F, 0.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(0, 54).addBox(-7.0F, -5.0F, 3.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
        Body.texOffs(0, 32).addBox(-6.0F, -5.0F, 5.0F, 12.0F, 3.0F, 1.0F, 0.0F, false);
        Body.texOffs(26, 32).addBox(-6.0F, -2.0F, 4.0F, 12.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(0, 10).addBox(5.0F, -2.0F, 2.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        Body.texOffs(0, 0).addBox(-6.0F, -2.0F, 2.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        Body.texOffs(66, 3).addBox(-6.0F, -1.0F, 3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(65, 56).addBox(5.0F, -1.0F, 3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        Body.texOffs(0, 51).addBox(-5.0F, 0.0F, 3.0F, 10.0F, 2.0F, 1.0F, 0.0F, false);

        cape = new ModelRenderer(this);
        cape.setPos(0, 2, 3);
        Body.addChild(cape);
        cape.texOffs(22, 61).addBox(6.0F, 14.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        cape.texOffs(28, 61).addBox(5.0F, 10.0F, 1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);
        cape.texOffs(0, 20).addBox(-5.0F, 9.0F, 2.0F, 10.0F, 10.0F, 1.0F, 0.0F, false);
        cape.texOffs(42, 46).addBox(-5.0F, 8.0F, 1.0F, 10.0F, 2.0F, 1.0F, 0.0F, false);
        cape.texOffs(25, 35).addBox(-5.0F, 4.0F, 1.0F, 10.0F, 4.0F, 1.0F, 0.0F, false);
        cape.texOffs(42, 61).addBox(5.0F, 5.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        cape.texOffs(34, 16).addBox(-5.0F, 2.0F, 0.0F, 10.0F, 3.0F, 1.0F, 0.0F, false);
        cape.texOffs(46, 65).addBox(5.0F, 3.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cape.texOffs(62, 28).addBox(-6.0F, 5.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        cape.texOffs(65, 34).addBox(-6.0F, 3.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        cape.texOffs(0, 61).addBox(-7.0F, 14.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        cape.texOffs(6, 61).addBox(-6.0F, 10.0F, 1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);
        cape.texOffs(47, 38).addBox(-5.0F, 1.0F, -1.0F, 10.0F, 2.0F, 1.0F, 0.0F, false);
        cape.texOffs(47, 49).addBox(-5.0F, -1.0F, -1.0F, 10.0F, 2.0F, 1.0F, 0.0F, false);

        RightArm = new ModelRenderer(this);
        RightArm.setPos(-5.0F, 2.0F, 0.0F);
        RightArm.texOffs(24, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);
        RightArm.texOffs(0, 45).addBox(-3.5F, 6.25F, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, false);
        RightArm.texOffs(32, 40).addBox(-3.5F, 4.75F, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, false);
        RightArm.texOffs(56, 9).addBox(-3.75F, 0.75F, -1.5F, 4.0F, 3.0F, 3.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        LeftArm.setPos(5.0F, 2.0F, 0.0F);
        LeftArm.texOffs(22, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);
        LeftArm.texOffs(58, 61).addBox(2.75F, 0.75F, -1.5F, 1.0F, 3.0F, 3.0F, 0.0F, false);
        LeftArm.texOffs(47, 32).addBox(-1.5F, 4.75F, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, false);
        LeftArm.texOffs(27, 46).addBox(-1.5F, 6.25F, -2.5F, 5.0F, 1.0F, 5.0F, -0.1F, false);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        double dist = entity.distanceToSqr(entity.xo, entity.yo, entity.zo);
        if (dist > 0.0D && dist <= 0.02D) {
            dist += 0.02D;
        }

        double angle = MathHelper.clamp(dist * 1000.0D - 1.0D, 0.0D, 70.0D);
        boolean isMoving = dist > 0.02D;
        if (isMoving) {
            angle += (MathHelper.sin((float) MathHelper.lerp(angle, entity.walkDistO, entity.walkDist)) * 6.0F);
        }
        cape.xRot = (float) Math.toRadians(angle);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.RightArm.copyFrom(this.rightArm);
        this.LeftArm.copyFrom(this.leftArm);
        this.Body.copyFrom(this.body);
        this.Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
