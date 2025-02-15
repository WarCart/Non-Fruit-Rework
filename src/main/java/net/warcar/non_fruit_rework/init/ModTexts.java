package net.warcar.non_fruit_rework.init;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.enums.PacifistaModel;
import net.warcar.non_fruit_rework.helpers.LangHelper;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

public class ModTexts {
    public static final ITextComponent BUY_PACIFISTA = LangHelper.registerLine("gui.text.buy_pacifista", "Buy Pacifista (10000-500000 belly)");
    public static final TranslationTextComponent BUY_PACIFISTA_LVL = LangHelper.registerLine("gui.text.buy_pacifista_lvl", "Buy %s (%s belly)");
    public static final ITextComponent BUY_SERAPHIM = LangHelper.registerLine("gui.text.buy_seraphim", "Buy Seraphim (1000000-50000000 belly)");
    public static final TranslationTextComponent BUY_SERAPHIM_LVL = LangHelper.registerLine("gui.text.buy_seraphim_lvl", "Buy %s (1000000 belly)");
    public static final ITextComponent CYBORG_UPGRADES = LangHelper.registerLine("gui.text.quests_cyborg", "Cyborg Upgrades");
    public static final ITextComponent GENETIC_MODIFICATION = LangHelper.registerLine("gui.text.genetic_modification", "Genetic Modifications");
    public static final ITextComponent GENETIC_COLLECTION = LangHelper.registerLine("gui.text.quests_genetics", "Genetic Collection");
    public static final ITextComponent BROKE = LangHelper.registerLine("gui.text.not_enough_money", "You don't have enough moneys for that");
    public static final TranslationTextComponent GENOME_NOT_INCLUDED = LangHelper.registerLine("gui.text.need_genome", "You need to collect %s genome for that\n(Go look %s quest)");
    public static final ITextComponent CUSTOM_SERAPHIM = LangHelper.registerLine("gui.text.custom_seraphim", "I want a custom Seraphim! (50000000 belly)");

    public static void init() {
        for (PacifistaModel model : PacifistaModel.values()) {
            LangHelper.registerLine("entity.pacifista." + WyHelper.getResourceName(model.name()), model.getName());
        }
    }
}
