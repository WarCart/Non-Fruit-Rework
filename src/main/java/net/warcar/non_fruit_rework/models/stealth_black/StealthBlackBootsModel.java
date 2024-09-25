package net.warcar.non_fruit_rework.models.stealth_black;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class StealthBlackBootsModel<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer RightBoot;
    private final ModelRenderer RightBoot_r1;
    private final ModelRenderer RightBoot_r2;
    private final ModelRenderer LeftBoot;
    private final ModelRenderer LeftLeg_r1;
    private final ModelRenderer LeftLeg_r2;

    public StealthBlackBootsModel() {
        super(-0.5f);
        texWidth = 128;
        texHeight = 128;

        RightBoot = new ModelRenderer(this);
        RightBoot.setPos(-1.9F, 12.0F, 0.0F);
        RightBoot.texOffs(53, 16).addBox(-2.0F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        RightBoot.texOffs(46, 52).addBox(-1.0F, 11.25F, -3.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        RightBoot.texOffs(30, 52).addBox(-1.0F, 11.25F, -0.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        RightBoot.texOffs(20, 0).addBox(-1.0F, 10.0F, 1.0F, 2.0F, 2.0F, 2.0F, -0.4F, false);
        RightBoot.texOffs(61, 21).addBox(-1.5F, 9.5F, 2.0F, 3.0F, 3.0F, 2.0F, -0.5F, false);
        RightBoot.texOffs(55, 0).addBox(-2.0F, 10.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
        RightBoot.texOffs(16, 40).addBox(-2.0F, 5.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

        RightBoot_r1 = new ModelRenderer(this);
        RightBoot_r1.setPos(-4.6864F, 11.8554F, 0.45F);
        RightBoot.addChild(RightBoot_r1);
        setRotationAngle(RightBoot_r1, 0.0F, 0.0F, 0.3927F);
        RightBoot_r1.texOffs(38, 20).addBox(-0.1F, -7.0F, -3.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);

        RightBoot_r2 = new ModelRenderer(this);
        RightBoot_r2.setPos(0.2518F, 13.6923F, 0.45F);
        RightBoot.addChild(RightBoot_r2);
        setRotationAngle(RightBoot_r2, 0.0F, 0.0F, -0.3927F);
        RightBoot_r2.texOffs(0, 36).addBox(-0.1F, -7.0F, -3.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);

        LeftBoot = new ModelRenderer(this);
        LeftBoot.setPos(1.9F, 12.0F, 0.0F);
        LeftBoot.texOffs(50, 52).addBox(-2.0F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        LeftBoot.texOffs(28, 40).addBox(-1.0F, 11.25F, -0.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        LeftBoot.texOffs(15, 36).addBox(-1.0F, 11.25F, -3.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        LeftBoot.texOffs(54, 57).addBox(-2.0F, 10.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
        LeftBoot.texOffs(36, 0).addBox(-1.0F, 10.0F, 1.0F, 2.0F, 2.0F, 2.0F, -0.4F, false);
        LeftBoot.texOffs(32, 61).addBox(-1.5F, 9.5F, 2.075F, 3.0F, 3.0F, 2.0F, -0.5F, false);
        LeftBoot.texOffs(40, 6).addBox(-2.0F, 5.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

        LeftLeg_r1 = new ModelRenderer(this);
        LeftLeg_r1.setPos(0.2518F, 13.6923F, 0.45F);
        LeftBoot.addChild(LeftLeg_r1);
        setRotationAngle(LeftLeg_r1, 0.0F, 0.0F, -0.3927F);
        LeftLeg_r1.texOffs(38, 26).addBox(-0.1F, -7.0F, -3.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);

        LeftLeg_r2 = new ModelRenderer(this);
        LeftLeg_r2.setPos(-4.6864F, 11.8554F, 0.45F);
        LeftBoot.addChild(LeftLeg_r2);
        setRotationAngle(LeftLeg_r2, 0.0F, 0.0F, 0.3927F);
        LeftLeg_r2.texOffs(40, 0).addBox(-0.1F, -7.0F, -3.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.RightBoot.copyFrom(this.rightLeg);
        this.LeftBoot.copyFrom(this.leftLeg);
        RightBoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftBoot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
