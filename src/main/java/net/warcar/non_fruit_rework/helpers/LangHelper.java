package net.warcar.non_fruit_rework.helpers;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.ReferenceTextComponent;

import java.util.HashMap;

public final class LangHelper {
    private LangHelper() {} //Don't initialize
    private static final HashMap<String, String> langMap = new HashMap<>();

    public static HashMap<String, String> getLangMap() {
        return langMap;
    }

    public static IFormattableTextComponent[] registerDescriptionText(String abilityName, Pair<String, Object[]>... pairs) {
        return registerDescriptionText(NonFruitReworkMod.MOD_ID, abilityName, pairs);
    }

    public static IFormattableTextComponent[] registerDescriptionText(String modid, String abilityName, Pair<String, Object[]>... pairs) {
        IFormattableTextComponent[] components = new IFormattableTextComponent[pairs.length];

        for(int i = 0; i < pairs.length; ++i) {
            String key = String.format("ability.%s.%s.description.%s", modid, abilityName, i);
            registerLine(key, pairs[i].getKey());
            Object[] args = pairs[i].getValue();
            if (args != null) {
                for(int j = 0; j < args.length; ++j) {
                    Object o = args[j];
                    if (o instanceof RegistryObject) {
                        args[j] = new ReferenceTextComponent((RegistryObject)o);
                    }
                }
            } else {
                args = new Object[0];
            }

            TranslationTextComponent comp = new TranslationTextComponent(key, args);
            components[i] = comp;
        }

        return components;
    }

    public static TranslationTextComponent registerLine(String resourceName, String name) {
        langMap.put(resourceName, name);
        return new TranslationTextComponent(resourceName);
    }
}
