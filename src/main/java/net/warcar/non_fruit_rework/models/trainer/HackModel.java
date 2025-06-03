package net.warcar.non_fruit_rework.models.trainer;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.warcar.non_fruit_rework.entities.quests.FishmanTrainer;

public class HackModel extends BipedModel<FishmanTrainer> {
	private final ModelRenderer LeftLeg;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer Body;
	private final ModelRenderer belt_r1;
	private final ModelRenderer belt_r2;
	private final ModelRenderer Head;

	public HackModel() {
		super(0.0F, 0.0F, 128, 128);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(2.849F, 5.88F, 0.03F);
		LeftLeg.texOffs(50, 75).addBox(-2.98F, 0.12F, -3.02F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		LeftLeg.texOffs(86, 0).addBox(-2.98F, 0.12F, -3.02F, 6.0F, 13.0F, 6.0F, 0.2F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-2.889F, 5.88F, 0.03F);
		RightLeg.texOffs(25, 75).addBox(-2.98F, 0.12F, -3.02F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		RightLeg.texOffs(86, 20).addBox(-2.98F, 0.12F, -3.02F, 6.0F, 13.0F, 6.0F, 0.2F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(7.55F, -9.22F, 0.03F);
		LeftArm.texOffs(75, 75).addBox(-1.61F, -2.8F, -3.02F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		LeftArm.texOffs(25, 100).addBox(-1.61F, -2.8F, -3.02F, 6.0F, 12.0F, 6.0F, 0.2F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-7.55F, -9.22F, 0.03F);
		RightArm.texOffs(0, 63).addBox(-4.39F, -2.8F, -3.02F, 6.0F, 18.0F, 6.0F, 0.0F, false);
		RightArm.texOffs(0, 88).addBox(-4.39F, -2.8F, -3.02F, 6.0F, 12.0F, 6.0F, 0.2F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, -12.24F, 0.03F);
		Body.texOffs(49, 0).addBox(-6.0F, 0.22F, -3.02F, 12.0F, 18.0F, 6.0F, 0.0F, false);
		Body.texOffs(49, 25).addBox(-6.0F, 0.22F, -3.02F, 12.0F, 18.0F, 6.0F, 0.2F, false);
		Body.texOffs(82, 66).addBox(-6.0F, 14.22F, -3.02F, 12.0F, 2.0F, 6.0F, 0.25F, false);

		belt_r1 = new ModelRenderer(this);
		belt_r1.setPos(2.0F, 18.22F, -6.32F);
		Body.addChild(belt_r1);
		setRotationAngle(belt_r1, 0.0F, 0.0F, 1.1781F);
		belt_r1.texOffs(25, 66).addBox(-3.0F, -1.0F, 3.0F, 8.0F, 2.0F, 0.0F, 0.0F, false);

		belt_r2 = new ModelRenderer(this);
		belt_r2.setPos(-2.0F, 18.22F, -6.32F);
		Body.addChild(belt_r2);
		setRotationAngle(belt_r2, 0.0F, 0.0F, -1.1781F);
		belt_r2.texOffs(25, 63).addBox(-5.0F, -1.0F, 3.0F, 8.0F, 2.0F, 0.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -12.24F, 0.03F);
		Head.texOffs(0, 0).addBox(-6.0F, -11.8F, -6.04F, 12.0F, 12.0F, 12.0F, 0.0F, false);
		Head.texOffs(80, 109).addBox(-6.0F, -11.8F, -6.04F, 12.0F, 5.0F, 12.0F, 0.1F, false);
		Head.texOffs(1, 26).addBox(-6.0F, -8.6F, -2.84F, 12.0F, 10.0F, 10.0F, 0.1F, false);
		Head.texOffs(57, 112).addBox(-6.0F, -8.6F, -5.04F, 12.0F, 3.0F, 2.0F, 0.1F, false);
		Head.texOffs(81, 49).addBox(-6.0F, 1.6F, 2.16F, 12.0F, 11.0F, 5.0F, 0.1F, false);
		Head.texOffs(49, 50).addBox(0.0F, -16.8F, -7.04F, 0.0F, 8.0F, 16.0F, 0.0F, false);
		Head.texOffs(86, 45).addBox(-6.0F, -4.8F, -6.14F, 12.0F, 4.0F, 0.0F, 0.0F, false);
		Head.texOffs(86, 40).addBox(-6.0F, 0.2F, -6.04F, 12.0F, 4.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		setRotationAngle(LeftLeg, leftLeg);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(RightLeg, rightLeg);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(LeftArm, leftArm);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(RightArm, rightArm);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(Body, body);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotationAngle(Head, head);
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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