package net.warcar.non_fruit_rework.models.trainer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.warcar.non_fruit_rework.entities.quests.ElectroTrainer;

public class ElectroTrainerModel extends BipedModel<ElectroTrainer> {
	private final ModelRenderer Head;
	private final ModelRenderer Snout_r1;
	private final ModelRenderer Snout_r2;
	private final ModelRenderer Snout_r3;
	private final ModelRenderer Snout_r4;
	private final ModelRenderer RightEar;
	private final ModelRenderer LeftEar;
	private final ModelRenderer Body;
	private final ModelRenderer Cloak_r1;
	private final ModelRenderer Cloak_r2;
	private final ModelRenderer Cape;
	private final ModelRenderer Tail;
	private final ModelRenderer Tail2;
	private final ModelRenderer Tail3;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;

	public ElectroTrainerModel() {
		super(0, 0, 128, 128);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 0.0F, 0.0F);
		Head.texOffs(0, 24).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		Head.texOffs(33, 24).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);

		Snout_r1 = new ModelRenderer(this);
		Snout_r1.setPos(0.0F, -3.5509F, -4.6757F);
		Head.addChild(Snout_r1);
		setRotationAngle(Snout_r1, 0.5585F, 0.0F, 0.0F);
		Snout_r1.texOffs(83, 18).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 2.0F, 0.01F, false);

		Snout_r2 = new ModelRenderer(this);
		Snout_r2.setPos(0.0F, -3.0509F, -6.1757F);
		Head.addChild(Snout_r2);
		setRotationAngle(Snout_r2, 0.192F, 0.0F, 0.0F);
		Snout_r2.texOffs(67, 51).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		Snout_r3 = new ModelRenderer(this);
		Snout_r3.setPos(0.0F, -1.9F, -3.6F);
		Head.addChild(Snout_r3);
		setRotationAngle(Snout_r3, 0.2531F, 0.0F, 0.0F);
		Snout_r3.texOffs(76, 82).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 2.0F, 4.0F, 0.05F, false);

		Snout_r4 = new ModelRenderer(this);
		Snout_r4.setPos(0.0F, -0.1F, -3.2F);
		Head.addChild(Snout_r4);
		setRotationAngle(Snout_r4, 0.1222F, 0.0F, 0.0F);
		Snout_r4.texOffs(59, 82).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 2.0F, 4.0F, 0.0F, false);

		RightEar = new ModelRenderer(this);
		RightEar.setPos(4.5F, -8.5F, -0.5F);
		Head.addChild(RightEar);
		RightEar.texOffs(17, 69).addBox(0.5F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		RightEar.texOffs(17, 66).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		RightEar.texOffs(17, 63).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		RightEar.texOffs(17, 58).addBox(-1.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		LeftEar = new ModelRenderer(this);
		LeftEar.setPos(-4.5F, -8.5F, -0.5F);
		Head.addChild(LeftEar);
		LeftEar.texOffs(83, 13).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 0.0F, 0.0F);
		Body.texOffs(37, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		Body.texOffs(36, 74).addBox(-0.5F, 2.5F, -2.4F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.texOffs(36, 77).addBox(1.5F, 1.5F, -2.4F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.texOffs(36, 80).addBox(-2.5F, 1.5F, -2.4F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		Body.texOffs(0, 41).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.26F, false);
		Body.texOffs(0, 75).addBox(-5.0F, -4.1F, -1.6F, 0.0F, 5.0F, 7.0F, 0.001F, false);
		Body.texOffs(79, 0).addBox(5.0F, -4.1F, -1.6F, 0.0F, 5.0F, 7.0F, 0.001F, false);
		Body.texOffs(37, 17).addBox(-4.0F, 7.8F, -2.0F, 8.0F, 2.0F, 4.0F, 0.27F, false);

		Cloak_r1 = new ModelRenderer(this);
		Cloak_r1.setPos(-1.5F, 0.9F, 5.9F);
		Body.addChild(Cloak_r1);
		setRotationAngle(Cloak_r1, -1.5708F, 0.0F, -1.5708F);
		Cloak_r1.texOffs(46, 41).addBox(0.0F, 0.5F, -3.5F, 0.0F, 3.0F, 10.0F, 0.001F, false);

		Cloak_r2 = new ModelRenderer(this);
		Cloak_r2.setPos(-1.5F, -1.6F, 5.4F);
		Body.addChild(Cloak_r2);
		setRotationAngle(Cloak_r2, 0.0F, 1.5708F, 0.0F);
		Cloak_r2.texOffs(25, 41).addBox(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 10.0F, 0.001F, false);

		Cape = new ModelRenderer(this);
		Cape.setPos(0.0F, 0.0F, 0.0F);
		Body.addChild(Cape);
		Cape.texOffs(0, 0).addBox(-8.5F, -0.5F, 2.1F, 17.0F, 22.0F, 1.0F, -0.2F, false);

		Tail = new ModelRenderer(this);
		Tail.setPos(0.0F, 10.0F, 2.0F);
		Body.addChild(Tail);
		Tail.texOffs(59, 72).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		Tail2 = new ModelRenderer(this);
		Tail2.setPos(0.0F, 0.0F, 7.0F);
		Tail.addChild(Tail2);
		Tail2.texOffs(17, 74).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		Tail3 = new ModelRenderer(this);
		Tail3.setPos(0.0F, 0.0F, 7.0F);
		Tail2.addChild(Tail3);
		Tail3.texOffs(78, 72).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-5.0F, 2.0F, 0.0F);
		RightArm.texOffs(46, 55).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		RightArm.texOffs(25, 57).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.25F, false);
		RightArm.texOffs(80, 60).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.3F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(5.0F, 2.0F, 0.0F);
		LeftArm.texOffs(0, 58).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		LeftArm.texOffs(62, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.25F, false);
		LeftArm.texOffs(80, 51).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.3F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-1.9F, 12.0F, 0.0F);
		RightLeg.texOffs(63, 55).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(1.9F, 12.0F, 0.0F);
		LeftLeg.texOffs(67, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
	}

	private void setRotationAngle(ModelRenderer modelRenderer, ModelRenderer from) {
		modelRenderer.xRot = from.xRot;
		modelRenderer.yRot = from.yRot;
		modelRenderer.zRot = from.zRot;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		setRotationAngle(Head, head);
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(Body, body);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(RightArm, rightArm);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(LeftArm, leftArm);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(RightLeg, rightLeg);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(LeftLeg, leftLeg);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}