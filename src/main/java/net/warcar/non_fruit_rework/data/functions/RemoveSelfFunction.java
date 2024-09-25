package net.warcar.non_fruit_rework.data.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.text.StringTextComponent;
import net.warcar.non_fruit_rework.init.ReworkedLootTypes;
import xyz.pixelatedw.mineminenomi.data.entity.challenges.ChallengesDataCapability;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

import javax.annotation.ParametersAreNonnullByDefault;

public class RemoveSelfFunction extends LootFunction {
    protected RemoveSelfFunction(ILootCondition[] conditions) {
        super(conditions);
    }

    @ParametersAreNonnullByDefault
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (context.getParamOrNull(LootParameters.THIS_ENTITY) instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) context.getParamOrNull(LootParameters.THIS_ENTITY);
            ChallengesDataCapability.get(player).removeChallenge(context.getParamOrNull(ModLootTypes.COMPLETED_CHALLENGE));
            stack.setHoverName(new StringTextComponent("_rewards"));
        }
        return stack;
    }

    public LootFunctionType getType() {
        return ReworkedLootTypes.RESET;
    }

    public static class Serializer extends LootFunction.Serializer<RemoveSelfFunction> {
        public Serializer() {
        }

        public RemoveSelfFunction deserialize(JsonObject object, JsonDeserializationContext context, ILootCondition[] cond) {
            return new RemoveSelfFunction(cond);
        }
    }
}
