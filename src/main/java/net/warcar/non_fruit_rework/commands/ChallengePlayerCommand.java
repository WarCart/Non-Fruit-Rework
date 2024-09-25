package net.warcar.non_fruit_rework.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.warcar.non_fruit_rework.chalenges.TestChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.data.entity.challenges.ChallengesDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenges.IChallengesData;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;

import java.util.List;
import java.util.UUID;

public class ChallengePlayerCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> node = Commands.literal("challenge_player").requires(source -> source.hasPermission(2));
        node.then(Commands.argument("target", EntityArgument.player()).executes(context -> {
            ServerPlayerEntity player = context.getSource().getPlayerOrException();
            IChallengesData data = ChallengesDataCapability.get(player);
            if (data.hasChallenge(TestChallenge.INSTANCE)) {
                data.removeChallenge(TestChallenge.INSTANCE);
            }
            TestChallenge challenge = TestChallenge.INSTANCE.createChallenge();
            PlayerEntity target = EntityArgument.getPlayer(context, "target");
            /*Crew crew = ExtendedWorldData.get().getCrewWithCaptain(target.getUUID());
            if (crew != null) {
                List<Crew.Member> members = crew.getMembers();
                UUID[] uuids = new UUID[members.size()];
                for (int i = 0; i < members.size(); i++) {
                    uuids[i] = members.get(i).getUUID();
                }
                challenge.setTargetGroup(uuids);
            } else {*/
                challenge.setTargetGroup(target);
            //}
            data.addChallenge(challenge);
            return 1;
        }));
        node.then(Commands.literal("cancel").executes(context -> {
            ServerPlayerEntity player = context.getSource().getPlayerOrException();
            ChallengesDataCapability.get(player).removeChallenge(TestChallenge.INSTANCE);
            return 1;
        }));
        dispatcher.register(node);
    }
}
