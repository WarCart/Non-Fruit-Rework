package net.warcar.non_fruit_rework.models;
// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.warcar.non_fruit_rework.entities.quests.VegapunkEntity;

public class VegapunkModel extends BipedModel<VegapunkEntity> {
	private final ModelRenderer Head;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer torso;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_arm;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;

	public VegapunkModel() {
		super(0.0F, 0.0F, 128, 128);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -14.0F, 0.0F);
		Head.texOffs(51, 0).addBox(-4.0F, -7.0F, -6.9F, 8.0F, 9.0F, 8.0F, 0.0F, false);
		Head.texOffs(0, 0).addBox(-7.0F, -13.0F, -3.9F, 14.0F, 12.0F, 11.0F, 0.0F, false);
		Head.texOffs(74, 40).addBox(-2.0F, -2.0F, -7.9F, 4.0F, 11.0F, 1.0F, 0.0F, false);
		Head.texOffs(82, 22).addBox(-1.0F, -4.0F, -8.7F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		Head.texOffs(0, 48).addBox(-4.5F, -14.0F, -6.9F, 9.0F, 7.0F, 9.0F, 0.0F, false);
		Head.texOffs(27, 65).addBox(-1.0F, -24.0F, -3.4F, 2.0F, 11.0F, 2.0F, 0.0F, false);
		Head.texOffs(72, 18).addBox(0.2F, -24.0F, -2.4F, 7.0F, 3.0F, 0.0F, 0.0F, false);
		Head.texOffs(41, 24).addBox(-5.0F, -20.4F, -7.4F, 10.0F, 5.0F, 10.0F, 0.0F, false);
		Head.texOffs(69, 96).addBox(-4.0F, -19.4F, -6.4F, 8.0F, 4.0F, 8.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-2.5F, -11.0F, -7.0F);
		Head.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, -0.3927F);
		cube_r1.texOffs(83, 56).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(2.5F, -11.0F, -7.0F);
		Head.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.3927F);
		cube_r2.texOffs(83, 53).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, -2.8F, -7.4F);
		Head.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.5672F, 0.0F, 0.0F);
		cube_r3.texOffs(51, 18).addBox(-5.0F, -1.0F, 0.0F, 10.0F, 3.0F, 0.0F, 0.0F, false);

		torso = new ModelRenderer(this);
		torso.setPos(0.0F, -6.7F, 0.0F);
		torso.texOffs(41, 40).addBox(-5.0F, -7.2F, -3.0F, 10.0F, 14.0F, 6.0F, 0.0F, false);
		torso.texOffs(0, 24).addBox(-6.0F, -7.3F, -4.0F, 12.0F, 15.0F, 8.0F, 0.0F, false);

		right_arm = new ModelRenderer(this);
		right_arm.setPos(-6.0F, -12.45F, 0.0F);
		right_arm.texOffs(63, 61).addBox(-1.0F, -1.45F, -1.0F, 2.0F, 24.0F, 2.0F, 0.0F, true);
		right_arm.texOffs(37, 61).addBox(-1.5F, -1.55F, -1.5F, 3.0F, 22.0F, 3.0F, 0.0F, true);

		left_arm = new ModelRenderer(this);
		left_arm.setPos(6.0F, -12.45F, 0.0F);
		left_arm.texOffs(63, 61).addBox(-1.0F, -1.45F, -1.0F, 2.0F, 24.0F, 2.0F, 0.0F, false);
		left_arm.texOffs(37, 61).addBox(-1.5F, -1.55F, -1.5F, 3.0F, 22.0F, 3.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setPos(4.0F, -0.06F, 0.0F);
		left_leg.texOffs(72, 61).addBox(-2.0F, 17.06F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);
		left_leg.texOffs(84, 0).addBox(2.0F, 16.06F, -1.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		left_leg.texOffs(74, 53).addBox(2.0F, 18.06F, -1.5F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		left_leg.texOffs(82, 27).addBox(-1.0F, 16.76F, -3.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		left_leg.texOffs(18, 65).addBox(-1.0F, 0.06F, -1.0F, 2.0F, 18.0F, 2.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setPos(-4.0F, -0.06F, 0.0F);
		right_leg.texOffs(72, 61).addBox(-2.0F, 17.06F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, true);
		right_leg.texOffs(84, 0).addBox(-3.0F, 16.06F, -1.5F, 1.0F, 2.0F, 1.0F, 0.0F, true);
		right_leg.texOffs(74, 53).addBox(-3.0F, 18.06F, -1.5F, 1.0F, 3.0F, 3.0F, 0.0F, true);
		right_leg.texOffs(82, 27).addBox(-1.0F, 16.76F, -3.0F, 2.0F, 4.0F, 1.0F, 0.0F, true);
		right_leg.texOffs(18, 65).addBox(-1.0F, 0.06F, -1.0F, 2.0F, 18.0F, 2.0F, 0.0F, true);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		setRotationAngle(Head, head);
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(torso, body);
		torso.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(right_arm, rightArm);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(left_arm, leftArm);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(left_leg, leftLeg);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(right_leg, right_leg);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	private void setRotationAngle(ModelRenderer modelRenderer, ModelRenderer from) {
		modelRenderer.xRot = from.xRot;
		modelRenderer.yRot = from.yRot;
		modelRenderer.zRot = from.zRot;
	}
}