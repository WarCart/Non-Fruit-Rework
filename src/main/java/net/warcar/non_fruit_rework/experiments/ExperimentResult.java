package net.warcar.non_fruit_rework.experiments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.warcar.non_fruit_rework.init.ModRegistries;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class ExperimentResult extends ForgeRegistryEntry<ExperimentResult> {
    protected int ticks;

    protected ExperimentResult() {
        this.ticks = 0;
    }

    public abstract void apply(LivingEntity entity);

    public abstract void tick(LivingEntity entity);

    public abstract void remove(LivingEntity entity);

    public abstract Type getType();

    public CompoundNBT save(CompoundNBT tag) {
        tag.putInt("ticks", ticks);
        return tag;
    }

    public void load(CompoundNBT tag) {
        ticks = tag.getInt("ticks");
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public enum Type {
        FATAL,
        NEGATIVE,
        NEUTRAL,
        POSITIVE,
        SUCCESSFUL;
    }

    public static ArgumentType<ExperimentResult> commandArgumentType() {
        return new Argument();
    }

    private static class Argument implements ArgumentType<ExperimentResult> {
        public ExperimentResult parse(StringReader reader) throws CommandSyntaxException {
            return ModRegistries.EXPERIMENT_RESULTS.getValue(ResourceLocation.read(reader));
        }

        public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
            return ISuggestionProvider.suggestResource(ModRegistries.EXPERIMENT_RESULTS.getKeys(), builder);
        }

        public Collection<String> getExamples() {
            return ModRegistries.EXPERIMENT_RESULTS.getKeys().stream().map(ResourceLocation::toString).collect(Collectors.toList());
        }
    }
}
