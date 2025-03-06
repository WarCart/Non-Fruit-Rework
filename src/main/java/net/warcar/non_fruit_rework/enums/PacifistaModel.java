package net.warcar.non_fruit_rework.enums;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.IExtensibleEnum;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.PacifistaEntity;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.UUID;

public enum PacifistaModel implements IExtensibleEnum {
    PROTOTYPE(10000, "Prototype", -0.5, -0.5),
    MK1(25000, "MK. I", -0.25, -0.33),
    MK2(100000, "MK. II", 0, 0),
    MK3(500000, "MK. III", 0.25, 0.33);

    private final int price;
    private final String name;
    private final double armorMod;
    private final double hpMod;

    PacifistaModel(int price, String name, double armorMod, double hpMod) {
        this.price = price;
        this.name = name == null ? this.name() : name;
        this.armorMod = armorMod;
        this.hpMod = hpMod;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ITextComponent getLocalizedName() {
        return new TranslationTextComponent("entity.pacifista." + WyHelper.getResourceName(this.name()));
    }

    public void modify(PacifistaEntity entity) {
        entity.getAttribute(Attributes.ARMOR).addPermanentModifier(new AttributeModifier(UUID.fromString("88c69957-324b-405a-9355-1579d5971678"),
                "Model armor modifier", armorMod, AttributeModifier.Operation.MULTIPLY_TOTAL));
        entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("2272c4ce-47f8-4e26-b494-335dee94727b"),
                "Model HP Modifier", hpMod, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    public static PacifistaModel create(String regName, int price, String name, double armorMod, double hpMod) {
        throw new IllegalStateException(regName + " PacifistaModel isn't created");
    }
}
