package net.warcar.non_fruit_rework.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.warcar.non_fruit_rework.data.entity.mastery.MasteryDataCapability;
import net.warcar.non_fruit_rework.init.ModConfig;

public class MasteryCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> node = Commands.literal("mastery").requires(source -> source.hasPermission(2));
        node.then(Commands.literal("add").then(Commands.argument("quantity", FloatArgumentType.floatArg(-ModConfig.INSTANCE.getMaxMastery(), ModConfig.INSTANCE.getMaxMastery())).executes(context -> {
            ServerPlayerEntity player = context.getSource().getPlayerOrException();
            MasteryDataCapability.get(player).addMastery(FloatArgumentType.getFloat(context, "quantity"));
            return 1;
        })));
        node.then(Commands.literal("get").executes(context -> {
            ServerPlayerEntity player = context.getSource().getPlayerOrException();
            player.sendMessage(new StringTextComponent(Float.toString(MasteryDataCapability.get(player).getMastery())), Util.NIL_UUID);
            return 1;
        }));
        dispatcher.register(node);
    }
}
