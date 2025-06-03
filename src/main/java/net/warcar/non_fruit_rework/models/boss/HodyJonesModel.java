package net.warcar.non_fruit_rework.models.boss;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.warcar.non_fruit_rework.entities.bosses.HodyJonesBoss;

public class HodyJonesModel extends BipedModel<HodyJonesBoss> {
	private final ModelRenderer Head;
	private final ModelRenderer hat;
	private final ModelRenderer Body;
	private final ModelRenderer belt_r1;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;

	public HodyJonesModel() {
		super(0, 0, 128, 128);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -12.0F, 0.0F);
		Head.texOffs(0, 20).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);
		Head.texOffs(49, 20).addBox(-6.0F, -9.84F, -2.81F, 12.0F, 10.0F, 10.0F, 0.05F, false);
		Head.texOffs(98, 7).addBox(3.0F, -9.7F, -6.5F, 2.0F, 8.0F, 0.0F, 0.0F, false);
		Head.texOffs(98, 0).addBox(-6.0F, -9.84F, -4.91F, 12.0F, 4.0F, 2.0F, 0.05F, false);
		Head.texOffs(61, 0).addBox(-6.5F, -3.64F, 3.19F, 13.0F, 11.0F, 5.0F, 0.1F, false);

		hat = new ModelRenderer(this);
		hat.setPos(0.0F, -1.0F, 0.0F);
		Head.addChild(hat);
		hat.texOffs(0, 45).addBox(-6.0F, -14.95F, -6.05F, 12.0F, 6.0F, 12.0F, 0.1F, false);
		hat.texOffs(94, 30).addBox(-6.0F, -10.7F, -9.0F, 12.0F, 2.0F, 3.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, -12.0F, 0.0F);
		Body.texOffs(49, 41).addBox(-6.0F, 0.0F, -3.0F, 12.0F, 18.0F, 6.0F, 0.0F, false);
		Body.texOffs(0, 64).addBox(-6.0F, 0.125F, -3.0F, 12.0F, 18.0F, 6.0F, 0.2F, false);
		Body.texOffs(0, 0).addBox(-8.0F, -1.0F, -7.0F, 16.0F, 5.0F, 14.0F, 0.0F, false);
		Body.texOffs(50, 108).addBox(3.0F, 4.0F, -7.0F, 5.0F, 7.0F, 5.0F, 0.0F, false);
		Body.texOffs(0, 89).addBox(-6.0F, 15.58F, -3.0F, 12.0F, 2.0F, 6.0F, 0.25F, false);
		Body.texOffs(109, 110).addBox(-1.0F, 2.0F, 3.2F, 2.0F, 7.0F, 4.0F, 0.0F, false);
		Body.texOffs(110, 110).addBox(-0.5F, 3.0F, 7.2F, 1.0F, 6.0F, 4.0F, 0.0F, false);
		Body.texOffs(110, 110).addBox(-0.5F, 4.2F, 11.0F, 1.0F, 5.0F, 4.0F, -0.25F, false);

		belt_r1 = new ModelRenderer(this);
		belt_r1.setPos(4.1F, 18.18F, -6.49F);
		Body.addChild(belt_r1);
		setRotationAngle(belt_r1, 0.0F, 0.0524F, 1.5708F);
		belt_r1.texOffs(94, 36).addBox(-3.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, 0.001F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-7.5F, -9.0F, 0.0F);
		RightArm.texOffs(37, 66).addBox(-4.5F, -3.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		RightArm.texOffs(94, 17).addBox(-4.375F, -2.875F, -3.125F, 6.0F, 6.0F, 6.0F, 0.25F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(7.5F, -9.0F, 0.0F);
		LeftArm.texOffs(62, 66).addBox(-1.5F, -3.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		LeftArm.texOffs(0, 98).addBox(-1.375F, -2.875F, -3.125F, 6.0F, 6.0F, 6.0F, 0.25F, false);
		LeftArm.texOffs(87, 91).addBox(-2.475F, 11.125F, -4.025F, 8.0F, 0.0F, 8.0F, 0.001F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-2.85F, 6.0F, 0.0F);
		RightLeg.texOffs(86, 41).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		RightLeg.texOffs(62, 91).addBox(-3.125F, 0.125F, -3.125F, 6.0F, 10.0F, 6.0F, 0.25F, true);
		RightLeg.texOffs(87, 100).addBox(-3.125F, 14.125F, -3.125F, 6.0F, 4.0F, 6.0F, 0.25F, true);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(2.85F, 6.0F, 0.0F);
		LeftLeg.texOffs(87, 66).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		LeftLeg.texOffs(62, 91).addBox(-2.875F, 0.125F, -3.125F, 6.0F, 10.0F, 6.0F, 0.25F, false);
		LeftLeg.texOffs(87, 100).addBox(-2.875F, 14.125F, -3.125F, 6.0F, 4.0F, 6.0F, 0.25F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		setRotation(Head, head);
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(Body, body);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(RightArm, RightArm);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(LeftArm, LeftArm);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(RightLeg, RightLeg);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(LeftLeg, LeftLeg);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	private void setRotation(ModelRenderer modelRenderer, ModelRenderer from) {
		modelRenderer.xRot = from.xRot;
		modelRenderer.yRot = from.yRot;
		modelRenderer.zRot = from.zRot;
	}
}