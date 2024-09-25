package net.warcar.non_fruit_rework.models.stealth_black;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class StealthBlackLegsModel<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer RightLeg;
    private final ModelRenderer LeftLeg;

    public StealthBlackLegsModel() {
        super(-0.5f);
        texWidth = 128;
        texHeight = 128;

        RightLeg = new ModelRenderer(this);
        RightLeg.setPos(-1.9F, 12.0F, 0.0F);
        RightLeg.texOffs(34, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);

        LeftLeg = new ModelRenderer(this);
        LeftLeg.setPos(1.9F, 12.0F, 0.0F);
        LeftLeg.texOffs(18, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.RightLeg.copyFrom(this.rightLeg);
        this.LeftLeg.copyFrom(this.leftLeg);
        RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
