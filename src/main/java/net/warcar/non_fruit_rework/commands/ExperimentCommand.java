package net.warcar.non_fruit_rework.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.experiments.ExperimentResult;
import net.warcar.non_fruit_rework.network.ModNetwork;
import net.warcar.non_fruit_rework.network.packets.server.SSyncNonFruitDataPacket;

public class ExperimentCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("experiment_result").requires(source -> source.hasPermission(2));
        LiteralArgumentBuilder<CommandSource> finish = Commands.literal("give");
        finish.then(Commands.argument("experiment", ExperimentResult.commandArgumentType()).executes(context -> run(context, true)));
        builder.then(finish);
        LiteralArgumentBuilder<CommandSource> reset = Commands.literal("remove");
        reset.then(Commands.argument("experiment", ExperimentResult.commandArgumentType()).executes(context -> run(context, false)));
        builder.then(reset);
        dispatcher.register(builder);
    }

    private static int run(CommandContext<CommandSource> context, boolean finish) throws CommandSyntaxException {
        PlayerEntity player = context.getSource().getPlayerOrException();
        INonFruitData data = NonFruitDataCapability.get(player);
        ExperimentResult experiment = context.getArgument("experiment", ExperimentResult.class);
        if (!finish) {
            for (ExperimentResult experimentResult : data.getExperiments()) {
                if (experimentResult.getRegistryName().equals(experiment.getRegistryName())) {
                    data.removeExperiment(experimentResult);
                    experimentResult.remove(player);
                    break;
                }
            }
        } else {
            data.addExperiment(experiment);
        }
        ModNetwork.sendToAllTrackingAndSelf(new SSyncNonFruitDataPacket(player.getId(), data), player);
        return 1;
    }
}
