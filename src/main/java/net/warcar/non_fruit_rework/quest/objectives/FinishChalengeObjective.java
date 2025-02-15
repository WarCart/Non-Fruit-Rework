package net.warcar.non_fruit_rework.quest.objectives;

import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ISurviveObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.challenges.ChallengesDataCapability;

import java.util.function.Supplier;

public class FinishChalengeObjective extends Objective implements ISurviveObjective {
    private final Supplier<ChallengeCore<?>> challenge;

    public FinishChalengeObjective(String title, Supplier<ChallengeCore<?>> challenge) {
        super(title);
        this.challenge = challenge;
        this.setMaxProgress(1);
    }

    public ChallengeCore<?> getChallenge() {
        return challenge.get();
    }

    @Override
    public boolean checkTime(PlayerEntity playerEntity) {
        if (ChallengesDataCapability.get(playerEntity).isChallengeCompleted(challenge.get())) {
            this.setProgress(playerEntity, 1, false);
        }
        return false;
    }
}
