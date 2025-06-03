package net.warcar.non_fruit_rework.models.boss;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.warcar.non_fruit_rework.entities.bosses.InuarashiBoss;

public class InuarashiModel extends BipedModel<InuarashiBoss> {
	private final ModelRenderer Body;
	private final ModelRenderer Tail;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer Cape;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer Head;

	public InuarashiModel() {
		super(0, 0, 256, 256);
		Body = new ModelRenderer(this);
		Body.setPos(0.0F, -12.0F, 0.0F);
		Body.texOffs(90, 85).addBox(-6.0F, 0.0F, -3.0F, 12.0F, 18.0F, 6.0F, 0.0F, false);
		Body.texOffs(0, 97).addBox(-5.875F, 0.125F, -3.125F, 12.0F, 18.0F, 6.0F, 0.2F, false);
		Body.texOffs(127, 94).addBox(-5.875F, 18.525F, -3.125F, 12.0F, 4.0F, 6.0F, 0.2F, false);
		Body.texOffs(104, 135).addBox(-5.875F, 17.025F, -3.125F, 12.0F, 2.0F, 6.0F, 0.3F, false);
		Body.texOffs(187, 99).addBox(-5.0F, -1.5F, 2.9F, 10.0F, 6.0F, 1.0F, 0.0F, false);

		Tail = new ModelRenderer(this);
		Tail.setPos(0.0F, 15.8993F, 3.1938F);
		Body.addChild(Tail);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, 8.1007F, 21.4062F);
		Tail.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.1658F, 0.0F, 0.0F);
		cube_r1.texOffs(50, 125).addBox(-3.0F, -4.0F, -1.0F, 6.0F, 6.0F, 5.0F, -0.1F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-0.5F, 7.3007F, 17.1062F);
		Tail.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.0436F, 0.0F, 0.0F);
		cube_r2.texOffs(145, 34).addBox(-3.0F, -4.0F, -1.0F, 7.0F, 7.0F, 5.0F, -0.1F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, 5.8007F, 7.1062F);
		Tail.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.2007F, 0.0F, 0.0F);
		cube_r3.texOffs(49, 85).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 8.0F, 12.0F, -0.05F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(0.5F, 1.4007F, -1.9938F);
		Tail.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.5672F, 0.0F, 0.0F);
		cube_r4.texOffs(37, 106).addBox(-4.0F, -5.0F, -1.0F, 7.0F, 7.0F, 11.0F, 0.0F, false);

		Cape = new ModelRenderer(this);
		Cape.setPos(0.0F, 2.0F, 0.0F);
		Body.addChild(Cape);
		Cape.texOffs(74, 106).addBox(-12.5F, -0.5F, -3.5F, 0.0F, 32.0F, 7.0F, 0.0F, false);
		Cape.texOffs(17, 150).addBox(-12.5F, -0.5F, -3.5F, 4.0F, 10.0F, 0.0F, 0.0F, false);
		Cape.texOffs(114, 202).addBox(-8.5F, -2.5F, -3.5F, 5.0F, 7.0F, 0.0F, 0.0F, false);
		Cape.texOffs(175, 149).addBox(3.5F, -2.5F, -3.5F, 5.0F, 7.0F, 0.0F, 0.0F, false);
		Cape.texOffs(26, 150).addBox(8.5F, -0.5F, -3.5F, 4.0F, 10.0F, 0.0F, 0.0F, false);
		Cape.texOffs(89, 110).addBox(12.5F, -0.5F, -3.5F, 0.0F, 32.0F, 7.0F, 0.0F, false);
		Cape.texOffs(69, 52).addBox(-12.5F, -0.5F, 3.5F, 25.0F, 32.0F, 0.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(7.5F, -9.0F, 0.0F);
		LeftArm.texOffs(0, 122).addBox(-1.5F, -3.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		LeftArm.texOffs(25, 125).addBox(-1.375F, -2.875F, -3.125F, 6.0F, 18.0F, 6.0F, 0.2F, false);
		LeftArm.texOffs(141, 135).addBox(-1.375F, 6.125F, -3.125F, 6.0F, 9.0F, 6.0F, 0.3F, false);
		LeftArm.texOffs(129, 120).addBox(-2.5F, -4.5F, -4.1F, 8.0F, 6.0F, 8.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-7.5F, -9.0F, 0.0F);
		RightArm.texOffs(0, 122).addBox(-4.5F, -3.0F, -3.0F, 6.0F, 18.0F, 6.0F, 0.0F, true);
		RightArm.texOffs(25, 125).addBox(-4.625F, -2.875F, -3.125F, 6.0F, 18.0F, 6.0F, 0.2F, true);
		RightArm.texOffs(141, 135).addBox(-4.625F, 6.125F, -3.125F, 6.0F, 9.0F, 6.0F, 0.3F, true);
		RightArm.texOffs(129, 120).addBox(-5.5F, -4.5F, -4.1F, 8.0F, 6.0F, 8.0F, 0.0F, true);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-2.85F, 6.0F, 0.0F);
		RightLeg.texOffs(127, 69).addBox(-2.9F, 0.0F, -3.125F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		RightLeg.texOffs(138, 15).addBox(-2.875F, 0.125F, -3.125F, 6.0F, 12.0F, 6.0F, 0.15F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(2.85F, 6.0F, 0.0F);
		LeftLeg.texOffs(129, 144).addBox(0.0F, 8.0F, -2.6F, 0.0F, 8.0F, 5.0F, 0.001F, false);
		LeftLeg.texOffs(109, 44).addBox(0.0F, 16.0F, -2.1F, 0.0F, 2.0F, 4.0F, 0.001F, false);
		LeftLeg.texOffs(73, 146).addBox(-0.5F, 8.0F, -1.6F, 1.0F, 8.0F, 3.0F, 0.001F, false);
		LeftLeg.texOffs(120, 69).addBox(-0.5F, 16.0F, -1.1F, 1.0F, 2.0F, 2.0F, 0.001F, false);
		LeftLeg.texOffs(144, 0).addBox(-2.875F, 0.125F, -3.125F, 6.0F, 8.0F, 6.0F, 0.15F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -12.0F, 0.0F);
		Head.texOffs(0, 72).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);
		Head.texOffs(101, 0).addBox(-6.0F, -10.0F, -6.0F, 12.0F, 5.0F, 9.0F, 0.25F, false);
		Head.texOffs(0, 0).addBox(-12.5F, -12.0F, -12.5F, 25.0F, 2.0F, 25.0F, 0.0F, false);
		Head.texOffs(0, 28).addBox(-10.5F, -14.0F, -10.5F, 21.0F, 2.0F, 21.0F, 0.0F, false);
		Head.texOffs(0, 52).addBox(-8.5F, -16.0F, -8.5F, 17.0F, 2.0F, 17.0F, 0.0F, false);
		Head.texOffs(0, 147).addBox(6.0F, -11.0F, -4.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
		Head.texOffs(49, 72).addBox(6.0F, -5.0F, -4.0F, 3.0F, 5.0F, 6.0F, 0.0F, false);
		Head.texOffs(145, 56).addBox(-9.0F, -5.0F, -4.0F, 3.0F, 5.0F, 6.0F, 0.0F, false);
		Head.texOffs(50, 148).addBox(-8.0F, -11.0F, -4.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
		Head.texOffs(50, 137).addBox(-3.0F, -6.0F, -11.0F, 6.0F, 5.0F, 5.0F, 0.05F, false);
		Head.texOffs(37, 103).addBox(-1.0F, -6.1F, -11.1F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		Head.texOffs(145, 47).addBox(-3.0F, -2.5F, -11.0F, 6.0F, 3.0F, 5.0F, 0.0F, false);
		Head.texOffs(85, 44).addBox(-2.0F, 0.5F, -11.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(InuarashiBoss entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		setRotation(Body, body);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(LeftArm, leftArm);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(RightArm, rightArm);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(RightLeg, rightLeg);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(LeftLeg, leftLeg);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(Head, head);
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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