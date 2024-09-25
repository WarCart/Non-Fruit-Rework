package net.warcar.non_fruit_rework.models.stealth_black;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.ParametersAreNonnullByDefault;

public class StealthBlackHelmetModel<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer Head;

    public StealthBlackHelmetModel() {
        super(-0.5f);
        texWidth = 128;
        texHeight = 128;

        Head = new ModelRenderer(this);
        Head.setPos(0.0F, 0.0F, 0.0F);
        Head.texOffs(13, 17).addBox(4.0F, -6.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
        Head.texOffs(0, 17).addBox(-6.0F, -6.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
        Head.texOffs(0, 26).addBox(-5.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
        Head.texOffs(10, 26).addBox(4.0F, -9.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
        Head.texOffs(13, 8).addBox(-4.0F, -9.0F, -1.0F, 8.0F, 1.0F, 2.0F, 0.0F, false);
        Head.texOffs(0, 8).addBox(-4.0F, -4.0F, -4.25F, 8.0F, 2.0F, 0.0F, 0.0F, false);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.Head.copyFrom(this.head);
        Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
