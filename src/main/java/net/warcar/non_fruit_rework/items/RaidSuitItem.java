package net.warcar.non_fruit_rework.items;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RaidSuitItem extends ArmorItem {
    public final Map<EquipmentSlotType, RaidSuitItem> armor;
    private RaidSuitCapsule capsule;
    public RaidSuitItem(IArmorMaterial material, EquipmentSlotType type, Properties properties, Map<EquipmentSlotType, RaidSuitItem> armor, RaidSuitCapsule capsule) {
        super(material, type, properties);
        this.capsule = capsule;
        armor.put(type, this);
        this.armor = armor;
    }

    public Item getCapsule() {
        return capsule;
    }

    public void setCapsule(RaidSuitCapsule capsule) {
        this.capsule = capsule;
    }

    public ItemStack getCapsuleStack(ItemStack suit) {
        ItemStack capsule = new ItemStack(this.getCapsule());
        capsule.setTag(suit.getOrCreateTag());
        return capsule;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void inventoryTick(ItemStack stack, World level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide) {
            return;
        }
        boolean remove = true;
        List<Item> stacks = new ArrayList<>();
        for (ItemStack itemStack : entity.getArmorSlots()) {
            stacks.add(itemStack.getItem());
            if (itemStack == stack) {
                remove = false;
            }
        }
        if (remove) {
            stack.setCount(0);
            return;
        }
        if (!stacks.containsAll(this.armor.values()) && entity instanceof PlayerEntity) {
            this.destroy((PlayerEntity) entity, stack);
            return;
        }
        if (entity instanceof PlayerEntity && !((PlayerEntity) entity).abilities.mayfly) {
            ((PlayerEntity) entity).abilities.mayfly = true;
            ((PlayerEntity) entity).abilities.setFlyingSpeed(0.1f);
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }

    public void destroy(PlayerEntity player, ItemStack stack) {
        ItemStack capsule = new ItemStack(this.getCapsule());
        capsule.setTag(stack.getOrCreateTag());
        player.addItem(capsule);
        for (EquipmentSlotType type : this.armor.keySet()) {
            if (((LivingEntity) player).getItemBySlot(type).getItem() == this.armor.get(type)) {
                player.setItemSlot(type, ItemStack.EMPTY);
            }
        }
        PlayerAbilities abilities = player.abilities;
        if (!abilities.invulnerable) {
            abilities.mayfly = false;
            abilities.flying = false;
            abilities.setFlyingSpeed(0.05f);
            player.onUpdateAbilities();
        }
        IAbilityData props = AbilityDataCapability.get(player);
        boolean i = false;
        for (AbilityCore<?> core : this.capsule.abilities) {
            i |= props.removeUnlockedAbility(core);
        }
        if (i) {
            WyNetwork.sendTo(new SSyncAbilityDataPacket(player.getId(), props), player);
        }
        RaidSuitCapsule.ATTRIBUTES.forEach(((attribute, attributeModifier) -> {
            ModifiableAttributeInstance instance = player.getAttribute(attribute);
            if (instance.hasModifier(attributeModifier))
                instance.removeModifier(attributeModifier);
        }));
    }

    @Nullable
    public String getArmorTexture(ItemStack itemStack, Entity entity, EquipmentSlotType slot, String type) {
        ResourceLocation core = ForgeRegistries.ITEMS.getKey(this);
        if (core == null) return null;
        return core.getNamespace() + ":textures/models/raid_suits/" + core.getPath() + ".png";
    }
}
