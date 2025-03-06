package net.warcar.non_fruit_rework.abilities;

import com.google.common.base.Predicates;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.warcar.non_fruit_rework.enums.ModifiableAttributes;
import net.warcar.non_fruit_rework.enums.ModifiableAttributes.AttributeLink;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

public class GenesAbility extends PassiveStatBonusAbility {
    public static final AbilityCore<GenesAbility> INSTANCE = new AbilityCore.Builder<>("Genes", AbilityCategory.RACIAL, AbilityType.PASSIVE, GenesAbility::new)
            .setUnlockCheck(e -> true).setHidden().build();

    private final Map<ModifiableAttributes, Double> genes = new HashMap<>();

    public GenesAbility(AbilityCore<GenesAbility> core) {
        super(core);
        for (ModifiableAttributes modifiableAttribute : ModifiableAttributes.values()) {
            AttributeLink[] attributeLinks = modifiableAttribute.getAttribute();
            for (int i = 0; i < attributeLinks.length; i++) {
                AttributeLink attribute = attributeLinks[i];
                int id = i;
                this.pushDynamicAttribute(attribute.getAttribute(), entity -> new AttributeModifier(MathHelper.createInsecureUUID(new Random(modifiableAttribute.ordinal() * 14954L + id * 1934L)), attribute.getAttribute().getDescriptionId(), attribute.getScale().apply(genes.computeIfAbsent(modifiableAttribute, g -> 0d)), AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public Predicate<LivingEntity> getCheck() {
        return Predicates.alwaysTrue();
    }

    public Map<ModifiableAttributes, Double> getGenes() {
        return genes;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        CompoundNBT genes = new CompoundNBT();
        for (Map.Entry<ModifiableAttributes, Double> entry : this.genes.entrySet()) {
            genes.putDouble(entry.getKey().toString(), entry.getValue());
        }
        nbt.put("genes", genes);
        return super.save(nbt);
    }

    @Override
    public void load(CompoundNBT nbt) {
        super.load(nbt);
        CompoundNBT genes = nbt.getCompound("genes");
        for (String gene : genes.getAllKeys()) {
            this.genes.put(ModifiableAttributes.valueOf(gene), genes.getDouble(gene));
        }
    }
}
