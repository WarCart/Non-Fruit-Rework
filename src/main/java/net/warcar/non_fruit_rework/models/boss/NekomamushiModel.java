package net.warcar.non_fruit_rework.models.boss;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.warcar.non_fruit_rework.entities.bosses.NekomamushiBoss;

public class NekomamushiModel extends BipedModel<NekomamushiBoss> {
	private final ModelRenderer Head;
	private final ModelRenderer Ear_r1;
	private final ModelRenderer Ear_r2;
	private final ModelRenderer pipe;
	private final ModelRenderer Hair;
	private final ModelRenderer Body;
	private final ModelRenderer tail;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer kimono;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftArm;
	private final ModelRenderer Gun;
	private final ModelRenderer RightArm;

	public NekomamushiModel() {
		super(0, 0, 256, 256);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -6.0F, 0.0F);
		Head.texOffs(152, 19).addBox(-1.0F, -7.55F, -7.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		Head.texOffs(36, 97).addBox(-6.0F, -6.55F, -7.0F, 12.0F, 5.0F, 1.0F, 0.0F, false);
		Head.texOffs(89, 0).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);

		Ear_r1 = new ModelRenderer(this);
		Ear_r1.setPos(-10.3F, -15.9F, -0.5F);
		Head.addChild(Ear_r1);
		setRotationAngle(Ear_r1, 0.0F, 0.0F, 0.6981F);
		Ear_r1.texOffs(133, 138).addBox(-1.25F, -2.0F, -1.0F, 8.0F, 5.0F, 2.0F, 0.0F, true);
		Ear_r1.texOffs(134, 51).addBox(-4.25F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, true);

		Ear_r2 = new ModelRenderer(this);
		Ear_r2.setPos(10.3F, -15.9F, -0.5F);
		Head.addChild(Ear_r2);
		setRotationAngle(Ear_r2, 0.0F, 0.0F, -0.6981F);
		Ear_r2.texOffs(134, 51).addBox(1.25F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
		Ear_r2.texOffs(133, 138).addBox(-6.75F, -2.0F, -1.0F, 8.0F, 5.0F, 2.0F, 0.0F, false);

		pipe = new ModelRenderer(this);
		pipe.setPos(4.2167F, -5.2667F, -11.0833F);
		Head.addChild(pipe);
		setRotationAngle(pipe, -0.279F, -0.5189F, 0.0389F);
		pipe.texOffs(47, 141).addBox(-0.5167F, -0.2833F, -1.2167F, 1.0F, 1.0F, 7.0F, -0.2F, false);
		pipe.texOffs(125, 149).addBox(-0.4917F, -0.2583F, -1.8917F, 1.0F, 1.0F, 2.0F, -0.15F, false);
		pipe.texOffs(64, 141).addBox(-0.4917F, -0.9583F, -1.8917F, 1.0F, 1.0F, 1.0F, -0.15F, false);

		Hair = new ModelRenderer(this);
		Hair.setPos(0.0F, 30.0F, 0.0F);
		Head.addChild(Hair);
		Hair.texOffs(47, 150).addBox(5.5F, -44.0F, 6.0F, 2.0F, 9.0F, 3.0F, 0.0F, false);
		Hair.texOffs(58, 150).addBox(-8.0F, -44.0F, 6.0F, 2.0F, 9.0F, 3.0F, 0.0F, false);
		Hair.texOffs(0, 96).addBox(-6.0F, -44.0F, 6.0F, 12.0F, 26.0F, 5.0F, 0.0F, false);
		Hair.texOffs(67, 85).addBox(-8.0F, -44.0F, -4.0F, 16.0F, 14.0F, 10.0F, 0.0F, false);
		Hair.texOffs(89, 25).addBox(-7.5F, -46.0F, 0.0F, 15.0F, 2.0F, 8.0F, 0.0F, false);
		Hair.texOffs(120, 100).addBox(-6.0F, -46.0F, -6.0F, 12.0F, 2.0F, 6.0F, 0.0F, false);
		Hair.texOffs(35, 104).addBox(-6.0F, -44.0F, -6.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);
		Hair.texOffs(38, 141).addBox(6.0F, -44.0F, -6.0F, 2.0F, 14.0F, 2.0F, 0.0F, false);
		Hair.texOffs(133, 146).addBox(-8.0F, -44.0F, -6.0F, 2.0F, 14.0F, 2.0F, 0.0F, false);
		Hair.texOffs(112, 128).addBox(6.0F, -33.0F, 6.0F, 5.0F, 15.0F, 5.0F, 0.0F, false);
		Hair.texOffs(133, 109).addBox(-11.0F, -33.0F, 6.0F, 5.0F, 15.0F, 5.0F, 0.0F, false);
		Hair.texOffs(142, 146).addBox(-9.0F, -36.0F, 6.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);
		Hair.texOffs(0, 149).addBox(6.0F, -36.0F, 6.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);
		Hair.texOffs(69, 152).addBox(8.0F, -33.0F, 1.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		Hair.texOffs(21, 141).addBox(-9.9F, -33.0F, 1.0F, 3.0F, 3.0F, 5.0F, 0.0F, false);
		Hair.texOffs(133, 130).addBox(-7.5F, -31.55F, -7.05F, 15.0F, 5.0F, 1.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, -6.0F, 0.0F);
		Body.texOffs(0, 36).addBox(-10.55F, -0.45F, -6.05F, 21.0F, 17.0F, 12.0F, 0.1F, false);
		Body.texOffs(0, 66).addBox(-10.55F, -0.45F, -6.05F, 21.0F, 17.0F, 12.0F, 0.25F, false);
		Body.texOffs(67, 36).addBox(-10.45F, 8.65F, -6.15F, 21.0F, 8.0F, 12.0F, 0.5F, false);

		tail = new ModelRenderer(this);
		tail.setPos(0.0F, 15.0F, 4.0F);
		Body.addChild(tail);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, 1.65F, 20.15F);
		tail.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.5847F, 0.0F, 0.0F);
		cube_r1.texOffs(67, 58).addBox(-5.2F, -6.7F, -1.8F, 10.0F, 10.0F, 16.0F, 0.6F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, 3.65F, 10.7F);
		tail.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.1309F, 0.0F, 0.0F);
		cube_r2.texOffs(120, 79).addBox(-4.325F, -5.825F, -1.675F, 9.0F, 9.0F, 11.0F, 0.35F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, 1.5F, 2.0F);
		tail.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.3054F, 0.0F, 0.0F);
		cube_r3.texOffs(120, 58).addBox(-4.5F, -6.0F, -1.5F, 9.0F, 9.0F, 11.0F, 0.0F, false);

		kimono = new ModelRenderer(this);
		kimono.setPos(0.0F, 0.0F, 3.0F);
		Body.addChild(kimono);
		kimono.texOffs(0, 0).addBox(-16.5F, -0.9F, -6.3F, 33.0F, 24.0F, 11.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(5.25F, 10.5F, 0.0F);
		LeftLeg.texOffs(112, 149).addBox(-1.1F, 5.5F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);
		LeftLeg.texOffs(35, 110).addBox(-5.35F, -0.5F, -6.0F, 11.0F, 5.0F, 12.0F, 0.0F, false);
		LeftLeg.texOffs(0, 128).addBox(-3.85F, 4.5F, -4.5F, 8.0F, 3.0F, 9.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-5.25F, 10.5F, 0.0F);
		RightLeg.texOffs(17, 150).addBox(-1.9F, 5.5F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);
		RightLeg.texOffs(82, 110).addBox(-5.65F, -0.5F, -6.0F, 11.0F, 5.0F, 12.0F, 0.0F, false);
		RightLeg.texOffs(35, 128).addBox(-4.15F, 4.5F, -4.5F, 8.0F, 3.0F, 9.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(11.0F, -5.0F, 0.0F);
		LeftArm.texOffs(91, 128).addBox(-0.2F, -1.5F, -3.0F, 4.0F, 17.0F, 6.0F, 0.0F, false);

		Gun = new ModelRenderer(this);
		Gun.setPos(-11.1F, 29.0F, 0.0F);
		LeftArm.addChild(Gun);
		Gun.texOffs(134, 36).addBox(10.35F, -19.65F, -3.05F, 5.0F, 8.0F, 6.0F, 0.1F, false);
		Gun.texOffs(136, 25).addBox(10.375F, -19.625F, -3.075F, 5.0F, 1.0F, 6.0F, 0.15F, false);
		Gun.texOffs(138, 0).addBox(10.375F, -16.125F, -3.075F, 5.0F, 1.0F, 6.0F, 0.15F, false);
		Gun.texOffs(138, 9).addBox(10.375F, -12.625F, -3.075F, 5.0F, 1.0F, 6.0F, 0.15F, false);
		Gun.texOffs(163, 50).addBox(13.7F, -14.9F, -2.05F, 2.0F, 5.0F, 4.0F, 0.1F, false);
		Gun.texOffs(82, 152).addBox(13.65F, -14.9F, -2.1F, 2.0F, 10.0F, 2.0F, 0.0F, false);
		Gun.texOffs(91, 152).addBox(13.65F, -14.9F, 0.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-11.0F, -5.0F, 0.0F);
		RightArm.texOffs(70, 128).addBox(-3.8F, -1.5F, -3.0F, 4.0F, 17.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		setRotation(Head, head);
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(Body, body);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(LeftLeg, leftLeg);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(RightLeg, rightLeg);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(LeftArm, leftArm);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		setRotation(RightArm, rightArm);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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