package net.warcar.non_fruit_rework.init;

import net.minecraftforge.fml.RegistryObject;
import net.warcar.non_fruit_rework.chalenges.TestChallenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;;

public class ReworkedChallenges {
    public static final RegistryObject<ChallengeCore<TestChallenge>> TEST = ModChallenges.registerChallenge(TestChallenge.INSTANCE);

    public static void init() {
    }
}
