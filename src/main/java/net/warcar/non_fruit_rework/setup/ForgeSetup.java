package net.warcar.non_fruit_rework.setup;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.commands.ChallengePlayerCommand;
import net.warcar.non_fruit_rework.commands.MasteryCommand;
import net.warcar.non_fruit_rework.init.ModConfig;

@Mod.EventBusSubscriber(modid = NonFruitReworkMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeSetup {
    @SubscribeEvent
    public static void serverStarting(FMLServerStartingEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getServer().getCommands().getDispatcher();
        MasteryCommand.register(dispatcher);
        if (ModConfig.INSTANCE.isPlayerChallenges()) {
            ChallengePlayerCommand.register(dispatcher);
        }
    }
}
