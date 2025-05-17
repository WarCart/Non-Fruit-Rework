package net.warcar.non_fruit_rework.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.enums.Questline;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.quests.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quests.QuestDataCapability;
import xyz.pixelatedw.mineminenomi.events.abilities.AbilityProgressionEvents;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.wypi.WyNetwork;

public class QuestlineCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("questline").requires(source -> source.hasPermission(2));
        LiteralArgumentBuilder<CommandSource> finish = Commands.literal("finish");
        finish.then(Commands.argument("questline", Questline.commandArgumentType()).executes(context -> run(context, true)));
        builder.then(finish);
        LiteralArgumentBuilder<CommandSource> reset = Commands.literal("reset");
        reset.then(Commands.argument("questline", Questline.commandArgumentType()).executes(context -> run(context, false)));
        builder.then(reset);
        dispatcher.register(builder);
    }

    private static int run(CommandContext<CommandSource> context, boolean finish) throws CommandSyntaxException {
        PlayerEntity player = context.getSource().getPlayerOrException();
        IQuestData data = QuestDataCapability.get(player);
        Questline questline = context.getArgument("questline", Questline.class);
        for (QuestId questId : questline.getQuests()) {
            if (finish) {
                data.addFinishedQuest(questId);
                data.removeInProgressQuest(questId);
            } else {
                data.removeFinishedQuest(questId);
                data.removeInProgressQuest(questId);
            }
        }
        WyNetwork.sendToAllTrackingAndSelf(new SSyncQuestDataPacket(player.getId(), data), player);
        AbilityProgressionEvents.checkAllForNewUnlocks(player);
        return 1;
    }
}
