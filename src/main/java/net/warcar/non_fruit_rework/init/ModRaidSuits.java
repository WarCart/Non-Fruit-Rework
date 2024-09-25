package net.warcar.non_fruit_rework.init;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.warcar.non_fruit_rework.abilities.raid_suit.stealth_black.RyoseiOsobaKick;
import net.warcar.non_fruit_rework.abilities.raid_suit.stealth_black.StealthBlackInvisibility;
import net.warcar.non_fruit_rework.items.RaidSuitCapsule;
import net.warcar.non_fruit_rework.items.RaidSuitItem;
import net.warcar.non_fruit_rework.items.StealthBlackSet;
import net.warcar.non_fruit_rework.models.stealth_black.StealthBlackBootsModel;
import net.warcar.non_fruit_rework.models.stealth_black.StealthBlackLegsModel;
import xyz.pixelatedw.mineminenomi.api.GenericArmorMaterial;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.init.ModCreativeTabs;
import xyz.pixelatedw.mineminenomi.wypi.WyRegistry;

import java.util.HashMap;
import java.util.Map;

public class ModRaidSuits {
    public static final GenericArmorMaterial UNBREAKABLE = new GenericArmorMaterial("minecraft:unbreakable", 0,
            new int[]{4, 5, 7, 4}, 12, SoundEvents.ARMOR_EQUIP_GENERIC, 2, () -> Ingredient.EMPTY);

    public static final RegistryObject<Item> STEALTH_BLACK_HELMET;

    public static final RegistryObject<Item> STEALTH_BLACK_CHESTPLATE;

    public static final RegistryObject<Item> STEALTH_BLACK_LEGGINGS;

    public static final RegistryObject<Item> STEALTH_BLACK_BOOTS;

    public static final RegistryObject<Item> STEALTH_BLACK_CAPSULE;

    private static final Map<EquipmentSlotType, RaidSuitItem> STEALTH_BLACK_MAP = new HashMap<>();

    /*public static final RegistryObject<Item> SPARKING_RED_HELMET;

    public static final RegistryObject<Item> SPARKING_RED_CHESTPLATE;

    public static final RegistryObject<Item> SPARKING_RED_LEGGINGS;

    public static final RegistryObject<Item> SPARKING_RED_BOOTS;

    public static final RegistryObject<Item> SPARKING_RED_CAPSULE;

    private static final Map<EquipmentSlotType, RaidSuitItem> SPARKING_RED_MAP = new HashMap<>();*/

    /*public static final RegistryObject<Item> POISON_PINK_HELMET;

    public static final RegistryObject<Item> POISON_PINK_CHESTPLATE;

    public static final RegistryObject<Item> POISON_PINK_LEGGINGS;

    public static final RegistryObject<Item> POISON_PINK_BOOTS;

    public static final RegistryObject<Item> POISON_PINK_CAPSULE;

    private static final Map<EquipmentSlotType, RaidSuitItem> POISON_PINK_MAP = new HashMap<>();*/

    public static void register() {
    }

    static {
        STEALTH_BLACK_CAPSULE = WyRegistry.registerItem("Stealth Black Capsule", () -> new RaidSuitCapsule(new Item.Properties().tab(ModCreativeTabs.EQUIPMENT), STEALTH_BLACK_MAP, new AbilityCore[]{StealthBlackInvisibility.INSTANCE, RyoseiOsobaKick.INSTANCE}));
        STEALTH_BLACK_HELMET = WyRegistry.registerItem("Stealth Black Helmet", () -> new StealthBlackSet.StealthBlackHelmet(STEALTH_BLACK_MAP, (RaidSuitCapsule) STEALTH_BLACK_CAPSULE.get()));
        STEALTH_BLACK_CHESTPLATE = WyRegistry.registerItem("Stealth Black Chestplate", () -> new StealthBlackSet.StealthBlackChest(STEALTH_BLACK_MAP, (RaidSuitCapsule) STEALTH_BLACK_CAPSULE.get()));
        STEALTH_BLACK_LEGGINGS = WyRegistry.registerItem("Stealth Black Leggings", () -> new StealthBlackSet.StealthBlackLegs(STEALTH_BLACK_MAP, (RaidSuitCapsule) STEALTH_BLACK_CAPSULE.get()));
        STEALTH_BLACK_BOOTS = WyRegistry.registerItem("Stealth Black Boots", () -> new StealthBlackSet.StealthBlackBoots(STEALTH_BLACK_MAP, (RaidSuitCapsule) STEALTH_BLACK_CAPSULE.get()));

        /*SPARKING_RED_CAPSULE = WyRegistry.registerItem("Sparking Red Capsule", () -> new RaidSuitCapsule(new Item.Properties().tab(ModCreativeTabs.EQUIPMENT), SPARKING_RED_MAP, new AbilityCore[]{SparkingFigureAbility.INSTANCE}));
        SPARKING_RED_HELMET = WyRegistry.registerItem("Sparking Red Helmet", () -> new RaidSuitItem(UNBREAKABLE, EquipmentSlotType.HEAD, new Item.Properties(), SPARKING_RED_MAP, (RaidSuitCapsule) SPARKING_RED_CAPSULE.get(), new StealthBlackHelmetModel<>()));
        SPARKING_RED_CHESTPLATE = WyRegistry.registerItem("Sparking Red Chestplate", () -> new RaidSuitItem(UNBREAKABLE, EquipmentSlotType.CHEST, new Item.Properties(), SPARKING_RED_MAP, (RaidSuitCapsule) SPARKING_RED_CAPSULE.get(), new StealthBlackChestModel<>()));
        SPARKING_RED_LEGGINGS = WyRegistry.registerItem("Sparking Red Leggings", () -> new RaidSuitItem(UNBREAKABLE, EquipmentSlotType.LEGS, new Item.Properties(), SPARKING_RED_MAP, (RaidSuitCapsule) SPARKING_RED_CAPSULE.get(), new StealthBlackLegsModel<>()));
        SPARKING_RED_BOOTS = WyRegistry.registerItem("Sparking Red Boots", () -> new RaidSuitItem(UNBREAKABLE, EquipmentSlotType.FEET, new Item.Properties(), SPARKING_RED_MAP, (RaidSuitCapsule) SPARKING_RED_CAPSULE.get(), new StealthBlackBoots<>()));*/

        /*POISON_PINK_CAPSULE = WyRegistry.registerItem("Poison Pink Capsule", () -> new RaidSuitCapsule(new Item.Properties().tab(ModCreativeTabs.EQUIPMENT), POISON_PINK_MAP, new AbilityCore[]{PoisonPinkInvulnerability.INSTANCE, PoisonHitAbility.INSTANCE, DetoxicatingKissAbility.INSTANCE}));
        POISON_PINK_HELMET = WyRegistry.registerItem("Poison Pink Helmet", () -> new RaidSuitItem(UNBREAKABLE, EquipmentSlotType.HEAD, new Item.Properties(), POISON_PINK_MAP, (RaidSuitCapsule) POISON_PINK_CAPSULE.get(), new StealthBlackHelmetModel<>()));
        POISON_PINK_CHESTPLATE = WyRegistry.registerItem("Poison Pink Chestplate", () -> new RaidSuitItem(UNBREAKABLE, EquipmentSlotType.CHEST, new Item.Properties(), POISON_PINK_MAP, (RaidSuitCapsule) POISON_PINK_CAPSULE.get(), new StealthBlackChestModel<>()));
        POISON_PINK_LEGGINGS = WyRegistry.registerItem("Poison Pink Leggings", () -> new RaidSuitItem(UNBREAKABLE, EquipmentSlotType.LEGS, new Item.Properties(), POISON_PINK_MAP, (RaidSuitCapsule) POISON_PINK_CAPSULE.get(), new StealthBlackLegsModel<>()));
        POISON_PINK_BOOTS = WyRegistry.registerItem("Poison Pink Boots", () -> new RaidSuitItem(UNBREAKABLE, EquipmentSlotType.FEET, new Item.Properties(), POISON_PINK_MAP, (RaidSuitCapsule) POISON_PINK_CAPSULE.get(), new StealthBlackBoots<>()));*/
    }
}
