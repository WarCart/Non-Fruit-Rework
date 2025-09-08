package net.warcar.non_fruit_rework.enums;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.IExtensibleEnum;
import net.warcar.non_fruit_rework.init.ModEntityAttributes;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

import java.util.function.Supplier;

public enum ModifiableAttributes implements IExtensibleEnum {
    STRENGTH(0, 20, 200, new AttributeLink(Attributes.ATTACK_DAMAGE)),
    TOUGHNESS(0, 4, 40, new AttributeLink(ModAttributes.TOUGHNESS)),
    SIZE(-0.9, 2, 29, new AttributeLink(ModEntityAttributes.SIZE),
            new AttributeLink(ModAttributes.JUMP_HEIGHT, 0.5), new AttributeLink(ModAttributes.ATTACK_RANGE, val -> Math.max(val * 2.5, 0)),
            new AttributeLink(ModAttributes.STEP_HEIGHT, 0.375), new AttributeLink(ModAttributes.FALL_RESISTANCE, 2.5),
            new AttributeLink(ForgeMod.REACH_DISTANCE, val -> Math.max(val * 2.5, 0))),
    ENDURANCE(0, 150, 30, new AttributeLink(Attributes.MAX_HEALTH)),
    SKIN_TOUGHNESS(0, 20, 20, new AttributeLink(Attributes.ARMOR)),
    AGILITY(0, 20, 20, new AttributeLink(Attributes.MOVEMENT_SPEED, 0.1)),
    ;

    private final AttributeLink[] links;
    private final double min;
    private final double max;
    private final int step;

    ModifiableAttributes(double min, double max, int step, AttributeLink... supplier) {
        this.links = supplier;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public static ModifiableAttributes create(String name, double min, double max, int step, AttributeLink... supplier) {
        throw new IllegalStateException(name + " not created");
    }

    public AttributeLink[] getAttribute() {
        return this.links;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public int getSteps() {
        return step;
    }

    public static class AttributeLink {
        private final ITransformer scale;
        private final Supplier<Attribute> attributeSupplier;

        public AttributeLink(Supplier<Attribute> attributeSupplier) {
            this(attributeSupplier, 1);
        }

        public AttributeLink(Supplier<Attribute> attributeSupplier, ITransformer transformer) {
            this.scale = transformer;
            this.attributeSupplier = attributeSupplier;
        }

        public AttributeLink(Supplier<Attribute> attributeSupplier, double scale) {
            this(attributeSupplier, val -> val * scale);
        }

        public AttributeLink(Attribute attribute) {
            this(() -> attribute, 1);
        }

        public AttributeLink(Attribute attribute, double scale) {
            this(() -> attribute, scale);
        }

        public ITransformer getScale() {
            return scale;
        }

        public Attribute getAttribute() {
            return attributeSupplier.get();
        }

        @FunctionalInterface
        public interface ITransformer {
            double apply(double in);
        }
    }
}
