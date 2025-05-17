package net.warcar.non_fruit_rework.enums;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import net.minecraftforge.common.IExtensibleEnum;
import net.warcar.non_fruit_rework.helpers.QuestHelper;
import net.warcar.non_fruit_rework.init.ModQuests;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Questline implements IExtensibleEnum {
    //MMNMs
    ART_OF_WEATHER(() -> xyz.pixelatedw.mineminenomi.init.ModQuests.ART_OF_WEATHER_TRIALS),
    BLACK_LEG(() -> xyz.pixelatedw.mineminenomi.init.ModQuests.BLACK_LEG_TRIALS),
    BRAWLER(() -> xyz.pixelatedw.mineminenomi.init.ModQuests.BRAWLER_TRIALS),
    DOCTOR(() -> xyz.pixelatedw.mineminenomi.init.ModQuests.DOCTOR_TRIALS),
    SNIPER(() -> xyz.pixelatedw.mineminenomi.init.ModQuests.SNIPER_TRIALS),
    SWORDSMAN(() -> xyz.pixelatedw.mineminenomi.init.ModQuests.SWORDSMAN_TRIALS),

    //Racial
    ROKUSHIKI(() -> QuestHelper.getQuestsSorted(null, ModQuests.ROKUSHIKI_QUESTS)),
    FISHMAN_KARATE(() -> QuestHelper.getQuestsSorted(null, ModQuests.FISHMAN_KARATE_GENERIC_QUESTS, ModQuests.FISHMAN_KARATE_RACIAL_QUESTS)),
    ELECTRO(() -> QuestHelper.getQuestsSorted(null, ModQuests.ELECTRO_QUESTS)),
    //Not really racial, i hate this idea
    CYBORG_MODIFICATIONS(() -> QuestHelper.getQuestsSorted(null, ModQuests.CYBORG_QUESTS)),

    //Advanced
    ADVANCED_ROKUSHIKI(() -> QuestHelper.getQuestsSorted(null, ModQuests.ADV_ROKUSHIKI_QUESTS)),
    TEKKAI_KENPO(() -> QuestHelper.getQuestsSorted(null, ModQuests.TEKKAI_KENPO_QUESTS)),

    //Misc
    GENETIC_COLLECTION(() -> QuestHelper.getQuestsSorted(null, ModQuests.GEN_MODIFICATION_QUESTS))
    ;

    private final Supplier<List<QuestId>> quests;

    Questline(Supplier<List<QuestId>> supplier) {
        this.quests = supplier;
    }

    public List<QuestId> getQuests() {
        return quests.get();
    }

    public static ArgumentType<Questline> commandArgumentType() {
        return new QuestlineArgument();
    }

    public static Questline create(String name, Supplier<List<QuestId>> supplier) {
        throw new IllegalStateException("Questline cannot be created");
    }

    private static class QuestlineArgument implements ArgumentType<Questline> {
        public Questline parse(StringReader reader) throws CommandSyntaxException {
            return Questline.valueOf(reader.readUnquotedString());
        }

        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
            return ISuggestionProvider.suggest(Stream.of(Questline.values()).map(Object::toString), builder);
        }

        public Collection<String> getExamples() {
            return Stream.of(Questline.values()).map(Object::toString).collect(Collectors.toList());
        }
    }
}
