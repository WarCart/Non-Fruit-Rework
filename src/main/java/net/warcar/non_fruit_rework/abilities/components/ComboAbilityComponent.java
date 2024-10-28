package net.warcar.non_fruit_rework.abilities.components;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.init.ReworkedParticleEffects;
import net.warcar.non_fruit_rework.init.ModNetwork;
import net.warcar.non_fruit_rework.init.ReworkedComponents;
import net.warcar.non_fruit_rework.packets.combo.SComboActivateAnim;
import net.warcar.non_fruit_rework.packets.combo.SSetComboHasCaller;
import net.warcar.non_fruit_rework.packets.combo.SSetComboHasTarget;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityKeys;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComboAbilityComponent extends AbilityComponent<Ability> {
    private Pair<Ability, LivingEntity> caller = null;
    private LivingEntity target = null;

    public void setClientHaveTarget(boolean clientHaveTarget) {
        this.clientHaveTarget = clientHaveTarget;
    }

    public void setClientHaveCaller(boolean clientHaveCaller) {
        this.clientHaveCaller = clientHaveCaller;
    }

    private boolean clientHaveCaller = false;
    private boolean clientHaveTarget = false;
    private final List<Pair<Ability, LivingEntity>> calledAbilities = new ArrayList<>();
    private final PriorityEventPool<IComboUse> onUseEvents = new PriorityEventPool<>();
    private final ComboCore core;
    private final boolean[] abilitiesFulfilled;
    private float readyAnim = 0;

    public void startClientAnim() {
        this.readyAnim = 30;
    }

    public ComboAbilityComponent(Ability ability, ComboCore core) {
        super(ReworkedComponents.COMBO, ability);
        this.core = core;
        if (core.isOneOfEach()) {
            this.abilitiesFulfilled = new boolean[core.getCores().length];
        } else {
            this.abilitiesFulfilled = new boolean[0];
        }
    }

    public void addUseEvent(IComboUse event) {
        this.addUseEvent(100, event);
    }

    public void addUseEvent(int priority, IComboUse event) {
        this.onUseEvents.addEvent(priority, event);
    }

    @Override
    public void postInit(IAbility ability) {
        ability.getComponent(ModAbilityKeys.SLOT_DECORATION).ifPresent(slotDecorationComponent -> {
            slotDecorationComponent.addPreRenderEvent(10, (entity, client, martixStack, posX, posY, partialTicks) -> {
                if ((clientHaveTarget && !clientHaveCaller) || (clientHaveCaller && !clientHaveTarget)) {
                    slotDecorationComponent.setSlotColor(0.1f, 1, 0);
                    slotDecorationComponent.setIconColor(0.1f, 1, 0);
                    slotDecorationComponent.setMaxValue(1);
                    slotDecorationComponent.setCurrentValue(1);
                    slotDecorationComponent.setDisplayText(" ");
                } else if (clientHaveCaller) {
                    slotDecorationComponent.setSlotColor(0.5f, 1, 0);
                    slotDecorationComponent.setIconColor(0, 0.5f, 0);
                    slotDecorationComponent.setMaxValue(1);
                    slotDecorationComponent.setCurrentValue(1);
                    slotDecorationComponent.setDisplayText(" ");
                } else {
                    slotDecorationComponent.resetDecoration();
                }
            });
            slotDecorationComponent.addPostRenderEvent(100, (entity, minecraft, matrix, posX, posY, partialTicks) -> {
                if (this.readyAnim > 0.0F) {
                    this.readyAnim = (float)((double)this.readyAnim - 1.75 * (double)minecraft.getDeltaFrameTime());
                    if (this.readyAnim > 0.0F) {
                        float anim = this.readyAnim * 0.03F;
                        anim = Math.max(0.0F, anim);
                        float scale = 1.8F * EasingFunctionHelper.easeOutSine(anim);
                        float alpha = EasingFunctionHelper.easeOutSine(anim);
                        scale = MathHelper.clamp(scale, 1.0F, scale);
                        matrix.pushPose();
                        matrix.translate(posX, posY, 1.0);
                        matrix.translate(12.0, 12.0, 0.0);
                        matrix.scale(scale, scale, 1.0F);
                        matrix.translate(-12.0, -12.0, 0.0);
                        minecraft.getTextureManager().bind(ModResources.WIDGETS);
                        RendererHelper.drawTexturedModalRect(matrix, 0.0F, 0.0F, 0.0F, 0.0F, 23.0F, 23.0F, -1.0F, 1.0F, 0.95F, 0.0F, alpha);
                        matrix.popPose();
                    }
                }
            });
        });
    }

    public void use(LivingEntity entity) {
        if (!entity.isSteppingCarefully()) {
            LivingEntity target = (LivingEntity) WyHelper.rayTraceEntities(entity, 50).getEntity();
            if (this.getTarget() != null && this.getCaller() == null) {
                //Uses ability with all of called
                for (Pair<Ability, LivingEntity> called : calledAbilities) {
                    called.getLeft().getComponent(ReworkedComponents.COMBO).ifPresent(component -> component.useMoves(called.getRight(), calledAbilities));
                }
                this.useMoves(entity);
            } else if (this.getCaller() != null && this.getTarget() == null) {
                //Tries to accept call or create own depending on what entity are you targeting
                if (target != null) {
                    ComboAbilityComponent component = this.getCaller().getLeft().getComponent(ReworkedComponents.COMBO).get();
                    if (component.getTarget() == target) {
                        //Accepts offer
                        component.calledAbilities.add(new ImmutablePair<>(this.getAbility(), entity));//Problem somewhere here
                        //Starts Animation
                        if (this.getCaller().getRight() instanceof PlayerEntity) {
                            ModNetwork.sendTo(new SComboActivateAnim(this.getCaller().getRight(), this.getAbility()), (PlayerEntity) this.getCaller().getRight());
                        }
                        if (component.getCore().isOneOfEach()) {
                            component.abilitiesFulfilled[Arrays.asList(component.getCore().getCores()).indexOf(this.getAbility().getCore())] = true;
                        }
                        this.setTarget(target);
                        ModNetwork.sendToAllTrackingAndSelf(new SSetComboHasTarget(entity, this.getAbility(), true), entity);
                    } else {
                        //Declines offer and creates own
                        this.targetAndInvitations(entity, target);
                        this.setCaller(null);
                        ModNetwork.sendToAllTrackingAndSelf(new SSetComboHasCaller(entity, this.getAbility(), false), entity);
                    }
                }
            } else if (this.getTarget() == null && target != null) {
                this.targetAndInvitations(entity, target);
            }
        } else {
            //Skips combo completely
            this.useMoves(entity);
        }
    }

    /**
     * Calls for other players in range of 5 blocks to join the attack
     * @param owner
     * Player that calls for combo
     * @param target
     * Target of Combo
    **/
    private void targetAndInvitations(LivingEntity owner, LivingEntity target) {
        this.setTarget(target);
        if (owner instanceof ServerPlayerEntity) {
            WyHelper.spawnParticleEffectForOwner(ReworkedParticleEffects.COMBO_ABILITY_TARGET.get(), (PlayerEntity) owner, target.getX(), target.getY() + target.getBbHeight(), target.getZ(), null);
        }
        ModNetwork.sendToAllTrackingAndSelf(new SSetComboHasTarget(owner, this.getAbility(), true), owner);
        List<LivingEntity> nearbyPlayers = WyHelper.getNearbyLiving(owner.position(), owner.level, 5, ModEntityPredicates.getFriendlyFactions(owner).and(entity -> entity != owner).and(PlayerEntity.class::isInstance));
        for (LivingEntity player : nearbyPlayers) {
            IAbilityData abilityData = AbilityDataCapability.get(player);
            for (int i = 0; i < this.getCore().getCores().length; i++) {
                if (abilityData.hasEquippedAbility(this.getCore().getCores()[i]) && !(this.getCore().isOneOfEach() && this.abilitiesFulfilled[i])) {
                    //Send single invitation to player
                    abilityData.getEquippedAbility(this.getCore().getCores()[i]).getComponent(ReworkedComponents.COMBO).ifPresent(comboAbilityComponent -> {
                        comboAbilityComponent.setCaller(new ImmutablePair<>(this.getAbility(), owner));
                    });
                    int place = abilityData.getEquippedAbilitySlot(this.getCore().getCores()[i]);
                    if (player instanceof ServerPlayerEntity) {
                        WyHelper.spawnParticleEffectForOwner(ReworkedParticleEffects.COMBO_ABILITY_TARGET.get(), (PlayerEntity) player, target.getX(), target.getY() + target.getBbHeight(), target.getZ(), null);
                    }
                    ModNetwork.sendTo(new SSetComboHasCaller(player, abilityData.getEquippedAbility(this.getCore().getCores()[i]), true), (PlayerEntity) player);
                    TranslationTextComponent textComponent = new TranslationTextComponent("ability.message.combo_invitation", owner.getName(),  place / 8 + 1, place % 8 + 1);
                    player.sendMessage(textComponent, Util.NIL_UUID);
                }
            }
        }
    }

    protected void doTick(LivingEntity entity) {
        super.doTick(entity);
        if (entity instanceof ServerPlayerEntity && this.getCaller() != null) {
            LivingEntity target = this.getCaller().getLeft().getComponent(ReworkedComponents.COMBO).get().getTarget();
            WyHelper.spawnParticleEffectForOwner(ReworkedParticleEffects.COMBO_ABILITY_TARGET.get(), (PlayerEntity) entity, target.getX(), target.getY() + target.getBbHeight(), target.getZ(), null);
        } else if (entity instanceof ServerPlayerEntity && this.getTarget() != null) {
            WyHelper.spawnParticleEffectForOwner(ReworkedParticleEffects.COMBO_ABILITY_TARGET.get(), (PlayerEntity) entity, this.getTarget().getX(), this.getTarget().getY() + this.getTarget().getBbHeight(), this.getTarget().getZ(), null);
        }
    }

    private void useMoves(LivingEntity entity) {
        useMoves(entity, this.calledAbilities);
    }

    private void useMoves(LivingEntity entity, List<Pair<Ability, LivingEntity>> calledAbilities) {
        this.onUseEvents.dispatch((event) -> event.use(entity, this.getTarget(), this.getCaller() == null, calledAbilities));
        this.setTarget(null);
        this.setCaller(null);
        this.calledAbilities.clear();
        if (entity instanceof PlayerEntity) {
            ModNetwork.sendTo(new SSetComboHasCaller(entity, this.getAbility(), false), (PlayerEntity) entity);
            ModNetwork.sendTo(new SSetComboHasTarget(entity, this.getAbility(), false), (PlayerEntity) entity);
        }
    }

    public Pair<Ability, LivingEntity> getCaller() {
        return caller;
    }

    public void setCaller(Pair<Ability, LivingEntity> caller) {
        this.caller = caller;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    public ComboCore getCore() {
        return core;
    }

    @Nullable
    @Override
    public CompoundNBT save() {
        CompoundNBT nbt = super.save();
        nbt.putBoolean("savedHaveTarget", this.target != null);
        nbt.putBoolean("savedHaveCaller", this.caller != null);
        return nbt;
    }

    @Override
    public void load(CompoundNBT nbt) {
        super.load(nbt);
        this.clientHaveCaller = nbt.getBoolean("savedHaveCaller");
        this.clientHaveTarget = nbt.getBoolean("savedHaveTarget");
    }

    @FunctionalInterface
    public interface IComboUse {
        void use(LivingEntity user, LivingEntity target, boolean main, List<Pair<Ability, LivingEntity>> calledAbilities);
    }

    public static class ComboCore {
        private final boolean oneOfEach;
        private final AbilityCore<?>[] cores;

        public ComboCore(boolean oneOfEach, AbilityCore<?>[] cores) {
            this.oneOfEach = oneOfEach;
            this.cores = cores;
        }

        public boolean isOneOfEach() {
            return oneOfEach;
        }

        public AbilityCore<?>[] getCores() {
            return cores;
        }
    }
}
