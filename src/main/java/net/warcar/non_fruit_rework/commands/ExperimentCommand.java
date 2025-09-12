package net.warcar.non_fruit_rework.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.experiments.ExperimentResult;
import net.warcar.non_fruit_rework.init.ModTexts;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.server.SSyncNonFruitDataPacket;

import java.util.List;

public class ExperimentCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("experiment_result").requires(source -> source.hasPermission(2));
        LiteralArgumentBuilder<CommandSource> finish = Commands.literal("give");
        RequiredArgumentBuilder<CommandSource, ExperimentResult> experimentGive = Commands.argument("experiment", ExperimentResult.commandArgumentType()).executes(context -> run(context, true));
        experimentGive.then(Commands.argument("player", EntityArgument.player()).executes(context -> run(context, true)));
        finish.then(experimentGive);
        builder.then(finish);
        LiteralArgumentBuilder<CommandSource> reset = Commands.literal("remove");
        RequiredArgumentBuilder<CommandSource, ExperimentResult> experimentReset = Commands.argument("experiment", ExperimentResult.commandArgumentType()).executes(context -> run(context, false));
        experimentReset.then(Commands.argument("player", EntityArgument.player()).executes(context -> run(context, false)));
        reset.then(experimentReset);
        builder.then(reset);
        LiteralArgumentBuilder<CommandSource> check = Commands.literal("check").executes(ExperimentCommand::checkPlayer);
        check.then(Commands.argument("player", EntityArgument.player()).executes(ExperimentCommand::checkPlayer));
        builder.then(check);
        dispatcher.register(builder);
    }

    private static int checkPlayer(CommandContext<CommandSource> context) throws CommandSyntaxException {
        PlayerEntity sender = context.getSource().getPlayerOrException();
        PlayerEntity player;
        try {
            player = EntityArgument.getPlayer(context, "player");
        } catch (Exception e) {
            player = sender;
        }
        List<ExperimentResult> experiments = NonFruitDataCapability.get(player).getExperiments();
        if (experiments.isEmpty()) {
            sender.sendMessage(ModTexts.NO_EXPERIMENTS, Util.NIL_UUID);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < experiments.size(); i++) {
                ExperimentResult result = experiments.get(i);
                if (i != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(result.getRegistryName());
            }
            sender.sendMessage(new StringTextComponent(new TranslationTextComponent(ModTexts.CURRENT_EXPERIMENTS.getKey(), player.getName()).getString() + "\n" + stringBuilder), Util.NIL_UUID);
        }
        return 1;
    }

    private static int run(CommandContext<CommandSource> context, boolean finish) throws CommandSyntaxException {
        PlayerEntity sender = context.getSource().getPlayerOrException();
        PlayerEntity player;
        try {
            player = EntityArgument.getPlayer(context, "player");
        } catch (Exception e) {
            player = sender;
        }
        INonFruitData data = NonFruitDataCapability.get(player);
        ExperimentResult experiment = context.getArgument("experiment", ExperimentResult.class);
        if (!finish) {
            for (ExperimentResult experimentResult : data.getExperiments()) {
                if (experimentResult.getRegistryName().equals(experiment.getRegistryName())) {
                    data.removeExperiment(experimentResult);
                    sender.sendMessage(new TranslationTextComponent(ModTexts.REMOVED_EXPERIMENT.getKey(), experimentResult.getRegistryName(), player.getName()), Util.NIL_UUID);
                    ModNetwork.sendToAllTrackingAndSelf(new SSyncNonFruitDataPacket(player.getId(), data), player);
                    return 1;
                }
            }
            sender.sendMessage(new TranslationTextComponent(ModTexts.CANT_REMOVE_EXPERIMENT.getKey(), player.getName()), Util.NIL_UUID);
        } else {
            data.addExperiment(experiment);
            ModNetwork.sendToAllTrackingAndSelf(new SSyncNonFruitDataPacket(player.getId(), data), player);
            sender.sendMessage(new TranslationTextComponent(ModTexts.ADDED_EXPERIMENT.getKey(), experiment.getRegistryName(), player.getName()), Util.NIL_UUID);
        }
        return 1;
    }
}
