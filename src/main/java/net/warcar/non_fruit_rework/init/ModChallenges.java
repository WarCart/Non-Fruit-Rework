package net.warcar.non_fruit_rework.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.challenges.easy.HodyJonesChallenge;
import net.warcar.non_fruit_rework.challenges.easy.MinkDukesChallenge;
import net.warcar.non_fruit_rework.challenges.hard.MinkDukesHardChallenge;
import net.warcar.non_fruit_rework.challenges.rushes.*;
import xyz.pixelatedw.mineminenomi.api.ModRegistries;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;

public class ModChallenges {
    public static final DeferredRegister<ChallengeCore<?>> CHALLENGES = DeferredRegister.create(ModRegistries.CHALLENGES, NonFruitReworkMod.MOD_ID);

    public static void register(IEventBus bus) {
        CHALLENGES.register(bus);
        //registerChallenge(KingChallenge.INSTANCE);
        registerChallenge(MinkDukesChallenge.INSTANCE);
        registerChallenge(MinkDukesHardChallenge.INSTANCE);
        registerChallenge(HodyJonesChallenge.INSTANCE);
        registerChallenge(ArlongParkChallenge.INSTANCE);
        registerChallenge(ArlongParkHardChallenge.INSTANCE);
        registerChallenge(BaroqueWorksChallenge.INSTANCE);
        registerChallenge(BaroqueWorksHardChallenge.INSTANCE);
    }

    public static void registerChallenge(ChallengeCore<?> core) {
        CHALLENGES.register(core.getId(), () -> core);
        //LangHelper.registerLine(String.format("challenges.%s.%s", NonFruitReworkMod.MOD_ID, core.getId()), core.getUnlocalizedTitle());
    }
}
