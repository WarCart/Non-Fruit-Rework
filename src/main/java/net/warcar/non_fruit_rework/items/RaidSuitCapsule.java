package net.warcar.non_fruit_rework.items;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.warcar.non_fruit_rework.abilities.raid_suit.CapeGuard;
import net.warcar.non_fruit_rework.data.entity.germa_genes.GeneDataCapability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RaidSuitCapsule extends Item {
    public final Map<EquipmentSlotType, RaidSuitItem> armor;
    public final AbilityCore<?>[] abilities;
    public static final Map<Attribute, AttributeModifier> ATTRIBUTES = new HashMap<>();

    public RaidSuitCapsule(Properties properties, Map<EquipmentSlotType, RaidSuitItem> armor, AbilityCore<?>[] abilities) {
        super(properties);
        this.armor = armor;
        this.abilities = addFirst(abilities, CapeGuard.INSTANCE);
    }

    @Override
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ActionResult<ItemStack> result = super.use(level, player, hand);
        if (!(Objects.equals(EntityStatsCapability.get(player).getRace(), "modified_human")))
            return result;
        ItemStack stack = result.getObject();
        for (EquipmentSlotType slotType : this.armor.keySet()) {
            player.addItem(player.getItemBySlot(slotType));
            RaidSuitItem item = this.armor.get(slotType);
            item.setCapsule(this);
            ItemStack put = item.getDefaultInstance();
            put.setTag(stack.getOrCreateTag());
            player.setItemSlot(slotType, put);
        }
        IAbilityData props = AbilityDataCapability.get(player);
        boolean i = false;
        for (AbilityCore<?> core : abilities) {
            i |= props.addUnlockedAbility(new AbilityCoreUnlockWrapper<>(player, core, AbilityUnlock.create("EQUIPMENT")));
        }
        if (i) {
            WyNetwork.sendTo(new SSyncAbilityDataPacket(player.getId(), props), player);
        }
        stack.shrink(1);

        PlayerAbilities abilities = player.abilities;
        abilities.mayfly = true;
        abilities.setFlyingSpeed(0.1f);
        player.onUpdateAbilities();
        GeneDataCapability.get(player).addSuitUse(1);
        ATTRIBUTES.forEach(((attribute, attributeModifier) -> {
            ModifiableAttributeInstance instance = player.getAttribute(attribute);
            if (!instance.hasModifier(attributeModifier))
                instance.addTransientModifier(attributeModifier);
        }));
        return result;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    private <T> T[] addFirst(T[] array, T addition) {
        T[] cores = Arrays.copyOf(array, array.length + 1);
        if (cores.length - 1 >= 0) System.arraycopy(array, 0, cores, 1, cores.length - 1);
        cores[0] = addition;
        return cores;
    }

    private <T> T[] addLast(T[] array, T addition) {
        T[] cores = Arrays.copyOf(array, array.length + 1);
        cores[array.length] = addition;
        return cores;
    }
}
