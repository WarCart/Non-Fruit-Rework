package net.warcar.non_fruit_rework.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.warcar.non_fruit_rework.init.ModRaidSuits;
import net.warcar.non_fruit_rework.models.stealth_black.StealthBlackBootsModel;
import net.warcar.non_fruit_rework.models.stealth_black.StealthBlackChestModel;
import net.warcar.non_fruit_rework.models.stealth_black.StealthBlackHelmetModel;
import net.warcar.non_fruit_rework.models.stealth_black.StealthBlackLegsModel;

import javax.annotation.Nullable;
import java.util.Map;

public class StealthBlackSet {
    public static class StealthBlackHelmet extends RaidSuitItem {
        public StealthBlackHelmet(Map<EquipmentSlotType, RaidSuitItem> armor, RaidSuitCapsule capsule) {
            super(ModRaidSuits.UNBREAKABLE, EquipmentSlotType.HEAD, new Item.Properties(), armor, capsule);
        }

        @Nullable
        @Override
        @OnlyIn(Dist.CLIENT)
        public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
            return (A) new StealthBlackHelmetModel<>();
        }
    }

    public static class StealthBlackChest extends RaidSuitItem {
        public StealthBlackChest(Map<EquipmentSlotType, RaidSuitItem> armor, RaidSuitCapsule capsule) {
            super(ModRaidSuits.UNBREAKABLE, EquipmentSlotType.CHEST, new Item.Properties(), armor, capsule);
        }

        @Nullable
        @Override
        @OnlyIn(Dist.CLIENT)
        public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
            return (A) new StealthBlackChestModel<>();
        }
    }


    public static class StealthBlackLegs extends RaidSuitItem {
        public StealthBlackLegs(Map<EquipmentSlotType, RaidSuitItem> armor, RaidSuitCapsule capsule) {
            super(ModRaidSuits.UNBREAKABLE, EquipmentSlotType.LEGS, new Item.Properties(), armor, capsule);
        }

        @Nullable
        @Override
        @OnlyIn(Dist.CLIENT)
        public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
            return (A) new StealthBlackLegsModel<>();
        }
    }

    public static class StealthBlackBoots extends RaidSuitItem {
        public StealthBlackBoots(Map<EquipmentSlotType, RaidSuitItem> armor, RaidSuitCapsule capsule) {
            super(ModRaidSuits.UNBREAKABLE, EquipmentSlotType.FEET, new Item.Properties(), armor, capsule);
        }

        @Nullable
        @Override
        @OnlyIn(Dist.CLIENT)
        public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
            return (A) new StealthBlackBootsModel<>();
        }
    }
}
