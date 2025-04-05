package net.warcar.non_fruit_rework.renderers.layers;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import org.apache.commons.lang3.StringUtils;

@OnlyIn(Dist.CLIENT)
public class HeadLayer<T extends LivingEntity, M extends EntityModel<T> & IHasHead> extends LayerRenderer<T, M> {
   private final float scaleX;
   private final float scaleY;
   private final float scaleZ;

   public HeadLayer(IEntityRenderer<T, M> p_i50946_1_) {
      this(p_i50946_1_, 1.25F, 1.25F, 1.25F);
   }

   public HeadLayer(IEntityRenderer<T, M> p_i232475_1_, float p_i232475_2_, float p_i232475_3_, float p_i232475_4_) {
      super(p_i232475_1_);
      this.scaleX = p_i232475_2_;
      this.scaleY = p_i232475_3_;
      this.scaleZ = p_i232475_4_;
   }

   public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      INonFruitData data = NonFruitDataCapability.get(entity);
      ItemStack itemstack = data.getAdditionalInventory().getStackInSlot(0);
      if (!itemstack.isEmpty()) {
         Item item = itemstack.getItem();
         stack.pushPose();
         stack.scale(this.scaleX, this.scaleY, this.scaleZ);
         boolean flag = entity instanceof VillagerEntity || entity instanceof ZombieVillagerEntity;
         if (entity.isBaby() && !(entity instanceof VillagerEntity)) {
            stack.translate(0.0D, 0.03125D, 0.0D);
            stack.scale(0.7F, 0.7F, 0.7F);
            stack.translate(0.0D, 1.0D, 0.0D);
         }

         this.getParentModel().getHead().translateAndRotate(stack);
         if (item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
             stack.scale(1.1875F, -1.1875F, -1.1875F);
            if (flag) {
               stack.translate(0.0D, 0.0625D, 0.0D);
            }

            GameProfile gameprofile = null;
            if (itemstack.hasTag()) {
               CompoundNBT compoundnbt = itemstack.getTag();
               if (compoundnbt.contains("SkullOwner", 10)) {
                  gameprofile = NBTUtil.readGameProfile(compoundnbt.getCompound("SkullOwner"));
               } else if (compoundnbt.contains("SkullOwner", 8)) {
                  String s = compoundnbt.getString("SkullOwner");
                  if (!StringUtils.isBlank(s)) {
                     gameprofile = SkullTileEntity.updateGameprofile(new GameProfile(null, s));
                     compoundnbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
                  }
               }
            }

            stack.translate(-0.5D, 0.0D, -0.5D);
            SkullTileEntityRenderer.renderSkull(null, 180.0F, ((AbstractSkullBlock)((BlockItem)item).getBlock()).getType(), gameprofile, limbSwing, stack, buffer, packedLight);
         } else if (!(item instanceof ArmorItem) || ((ArmorItem)item).getSlot() != EquipmentSlotType.HEAD) {
             stack.translate(0.0D, -0.25D, 0.0D);
            stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            stack.scale(0.625F, -0.625F, -0.625F);
            if (flag) {
               stack.translate(0.0D, 0.1875D, 0.0D);
            }

            Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, itemstack, ItemCameraTransforms.TransformType.HEAD, false, stack, buffer, packedLight);
         }

         stack.popPose();
      }
   }
}