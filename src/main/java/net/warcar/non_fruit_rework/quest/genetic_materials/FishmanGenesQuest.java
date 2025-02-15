package net.warcar.non_fruit_rework.quest.genetic_materials;

import net.minecraft.entity.player.PlayerEntity;
import net.warcar.non_fruit_rework.quest.objectives.FinishChalengeObjective;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.challenges.ChallengesDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenges.IChallengesData;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;

public class FishmanGenesQuest extends Quest {
    public static final QuestId<FishmanGenesQuest> INSTANCE = new QuestId.Builder<>("Genes: Fishman", FishmanGenesQuest::new).build();

    private FinishChalengeObjective objective;
    private boolean hadBefore = false;

    public FishmanGenesQuest(QuestId core) {
        super(core);
        objective = new FinishChalengeObjective("Defeat Arlong (Hard)", ModChallenges.ARLONG_HARD::get);
        this.addObjective(objective);
        this.onStartEvent = this::onStart;
        this.onCompleteEvent = this::onFinish;
    }

    private boolean onStart(PlayerEntity playerEntity) {
        IChallengesData data = ChallengesDataCapability.get(playerEntity);
        if (!data.hasChallenge(objective.getChallenge())) {
            data.addChallenge(objective.getChallenge());
            hadBefore = true;
        }
        return true;
    }

    private boolean onFinish(PlayerEntity player) {
        if (hadBefore) {
            ChallengesDataCapability.get(player).removeChallenge(objective.getChallenge());
        }
        return true;
    }
}
