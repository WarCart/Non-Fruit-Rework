package net.warcar.non_fruit_rework.init;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.abilities.cyborg.WeaponsLeftAbility;
import net.warcar.non_fruit_rework.enums.ModifiableAttributes;
import net.warcar.non_fruit_rework.enums.PacifistaModel;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import java.util.function.Function;

public class ModTexts {
    public static final ITextComponent SHOP = LangHelper.registerLine("gui.text.shop", "Shop");
    public static final TranslationTextComponent BUY_PACIFISTA_LVL = LangHelper.registerLine("gui.text.buy_pacifista_lvl", "Buy %s (%s belly)");
    public static final ITextComponent BUY_SERAPHIM = LangHelper.registerLine("gui.text.buy_seraphim", "Buy Seraphim (1000000-50000000 belly)");
    public static final TranslationTextComponent BUY_SERAPHIM_LVL = LangHelper.registerLine("gui.text.buy_seraphim_lvl", "Buy %s (1000000 belly)");
    public static final ITextComponent CYBORG_UPGRADES = LangHelper.registerLine("gui.text.quests_cyborg", "Cyborg Upgrades");
    public static final ITextComponent GENETIC_COLLECTION = LangHelper.registerLine("gui.text.quests_genetics", "Genetic Collection");
    public static final ITextComponent BROKE = LangHelper.registerLine("gui.text.not_enough_money", "You don't have enough moneys for that");
    public static final TranslationTextComponent GENOME_NOT_INCLUDED = LangHelper.registerLine("gui.text.need_genome", "You need to collect %s genome for that\n(Go look %s quest)");
    public static final ITextComponent CUSTOM_SERAPHIM = LangHelper.registerLine("gui.text.custom_seraphim", "I want a custom Seraphim! (50000000 belly)");
    public static final ITextComponent MODIFY_ME = LangHelper.registerLine("gui.text.modify_me", "I want a new body with genetic modifications!");
    public static final TranslationTextComponent FINISH = LangHelper.registerLine("gui.text.finish", "Finish (%s belly)");
    public static final ITextComponent HYBRID_RACES = LangHelper.registerLine("gui.text.genetic_states.hybrid_races", "Hybrid Races");
    public static final ITextComponent PRISTINE_RACES = LangHelper.registerLine("gui.text.genetic_states.pristine_races", "Pristine Races");
    public static final ITextComponent OTHER_GENES = LangHelper.registerLine("gui.text.genetic_states.other_genes", "Other genes");
    public static final ITextComponent GENOME_DAMAGED = LangHelper.registerLine("gui.text.genome_damaged", "Hybrid genes should add up to 100%");
    public static final ITextComponent WIP = LangHelper.registerLine("gui.text.wip", "WIP");

    public static void init() {
        registerEnum(PacifistaModel.class, "entity.pacifista.", PacifistaModel::getName);
        registerEnum(ModifiableAttributes.class, "gui.gene.", e -> getName(e.name()));
        registerEnum(WeaponsLeftAbility.Mode.class, "ability.non_fruit_rework.weapons_left.mode.", e -> getName(e.name()));
        LangHelper.registerLine("gui.text.genetic_modification", "Genetic Modifications");
    }

    private static <E extends Enum<E>> void registerEnum(Class<E> enumClass, String string, Function<E, String> toNameConverter) {
        for (E val : enumClass.getEnumConstants()) {
            LangHelper.registerLine(string + WyHelper.getResourceName(val.name()), toNameConverter.apply(val));
        }
    }

    private static String getName(String name) {
        String lowerCase = name.replace('_', ' ').toLowerCase();
        return name.charAt(0) + lowerCase.substring(1);
    }
}
