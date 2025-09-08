package net.warcar.non_fruit_rework.experiments;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.warcar.non_fruit_rework.init.ModDamages;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.UUID;

public class StoneSkinResult extends ExperimentResult {
    public static final UUID MOVEMENT_SPEED_MODIFIER = UUID.fromString("9d2f6e0b-ede7-4001-8b29-45401f0d70e5");
    public static final UUID ARMOR_MODIFIER = UUID.fromString("34366a51-c3a3-481c-a0ad-c8de235429f3");
    private int maxTicks;

    @Override
    public void apply(LivingEntity entity) {
        maxTicks = (int) WyHelper.randomWithRange(entity.getRandom(), 6000, 12000);
    }

    @Override
    public void tick(LivingEntity entity) {
        updateMods(entity);
        if (this.ticks > maxTicks) {
            entity.level.setBlock(entity.blockPosition(), Blocks.STONE.defaultBlockState(), 3);
            entity.level.setBlock(entity.blockPosition().above(), Blocks.STONE.defaultBlockState(), 3);
            entity.hurt(ModDamages.STONE, Float.MAX_VALUE);
        }
    }

    private void updateMods(LivingEntity entity) {
        float timePercentage = MathHelper.clamp((float) this.ticks / maxTicks, 0.0F, 1.0F);
        ModifiableAttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.removeModifier(MOVEMENT_SPEED_MODIFIER);
            speed.addPermanentModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER, "Stone Skin Speed Modifier", -timePercentage, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        ModifiableAttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
        if (armor != null) {
            armor.removeModifier(ARMOR_MODIFIER);
            armor.addPermanentModifier(new AttributeModifier(ARMOR_MODIFIER, "Stone Skin Armor Modifier", timePercentage * 50, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void remove(LivingEntity entity) {
        ModifiableAttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.removeModifier(MOVEMENT_SPEED_MODIFIER);
        }
        ModifiableAttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
        if (armor != null) {
            armor.removeModifier(ARMOR_MODIFIER);
        }
    }

    @Override
    public Type getType() {
        return Type.FATAL;
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        CompoundNBT save = super.save(tag);
        tag.putInt("maxTicks", maxTicks);
        return save;
    }

    @Override
    public void load(CompoundNBT tag) {
        super.load(tag);
        maxTicks = tag.getInt("maxTicks");
    }
}
