package net.warcar.non_fruit_rework.items;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.warcar.non_fruit_rework.data.entity.medical_data.INonFruitData;
import net.warcar.non_fruit_rework.data.entity.medical_data.NonFruitDataCapability;
import net.warcar.non_fruit_rework.helpers.MiscHelper;
import net.warcar.non_fruit_rework.init.ModItems;
import net.warcar.non_fruit_rework.init.ModRaces;
import net.warcar.non_fruit_rework.init.ModTexts;
import net.warcar.non_fruit_rework.screens.ScientistScreen;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

import javax.annotation.Nullable;
import java.util.*;

public class SyringeItem extends Item {
    private static final HashMap<Effect, Boolean> MAX_EFFECTS = new HashMap<>();
    public SyringeItem() {
        super(new Item.Properties().tab(ItemGroup.TAB_BREWING).stacksTo(1));
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        if (isEmpty(stack) && !player.level.isClientSide/* && target instanceof PlayerEntity*/) {
            extractGenome(stack, player, target);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        if (!level.isClientSide && player.isSteppingCarefully()) {
            ItemStack stack = player.getItemInHand(hand);
            ItemStack potion = player.getOffhandItem();
            if (isEmpty(stack)) {
                if (false && !player.getOffhandItem().isEmpty() && potion.getItem() instanceof PotionItem) {//TODO: Maybe later might be too op
                    stack.getOrCreateTag().put("potion", potion.getOrCreateTag());
                    potion.shrink(1);
                    player.setItemInHand(Hand.OFF_HAND, new ItemStack(Items.GLASS_BOTTLE));
                } else {
                    extractGenome(stack, player, player);
                }
                return ActionResult.success(stack);
            } else if (potion.getItem() == Items.GLASS_BOTTLE && !MiscHelper.nullOrEmpty(stack.getTagElement("potion"))) {
                ItemStack newPotion = new ItemStack(Items.POTION);
                newPotion.setTag(stack.getOrCreateTagElement("potion"));
                stack.removeTagKey("potion");
                potion.shrink(1);
                player.setItemInHand(Hand.OFF_HAND, newPotion);
            }
        }
        return ActionResult.pass(player.getItemInHand(hand));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity user) {
        if (isEmpty(stack)) {
            extractGenome(stack, user, target);
            return true;
        }
        return false;
    }

    public static boolean isEmpty(ItemStack stack) {
        CompoundNBT genetics = stack.getTagElement("genetics");
        CompoundNBT potion = stack.getTagElement("potion");
        return MiscHelper.nullOrEmpty(genetics) && MiscHelper.nullOrEmpty(potion);
    }

    private static void extractGenome(ItemStack stack, LivingEntity player, LivingEntity target) {
        target.hurt(DamageSource.GENERIC, 1);
        CompoundNBT nbt = stack.getOrCreateTagElement("genetics");
        nbt.putString("blood", "human");
        nbt.putUUID("owner", target.getUUID());
        nbt.putString("ownerName", target.getName().getString());
        nbt.putFloat("researched", 0);
        IDevilFruit fruit = DevilFruitCapability.get(target);
        if (fruit.hasAnyDevilFruit()) {
            AkumaNoMiItem df = (AkumaNoMiItem) fruit.getDevilFruitItem();
            if (!df.is(ModTags.Items.LOGIA)) {
                nbt.putString("devil_fruit", fruit.getDevilFruit().get().toString());
                ListNBT abilities = new ListNBT();
                addRandom(player, target, abilities, Lists.newArrayList(df.getAbilities()));
                if (!abilities.isEmpty()) {
                    nbt.put("df_abilities", abilities);
                }
            }
        }
        INonFruitData medicalData = NonFruitDataCapability.get(target);
        ListNBT experiments = new ListNBT();
        if (!medicalData.getExperiments().isEmpty()) {
            addRandom(player, target, experiments, medicalData.getExperiments());
        }
        if (!experiments.isEmpty()) {
            nbt.put("deceases", experiments);
        }
        IEntityStats stats = EntityStatsCapability.get(target);
        nbt.putString("race", stats.getRace().toString());
        if (stats.getRace().equals(ModRaces.HYBRID.getId())) {
            CompoundNBT hybridTag = new CompoundNBT();
            medicalData.getGenome().forEach((race, amount) -> hybridTag.putFloat(race.toString(), amount));
            nbt.put("genome", hybridTag);
        }
    }

    private static <R extends ForgeRegistryEntry<R>> void addRandom(LivingEntity player, LivingEntity target, ListNBT experiments, List<R> list) {
        List<R> choose = new ArrayList<>(list);
        int amount = player.getRandom().nextInt(list.size() + 1);
        for (int i = 0; i < amount; i++) {
            int id = target.getRandom().nextInt(choose.size());
            R experiment = choose.remove(id);
            experiments.add(StringNBT.valueOf(experiment.getRegistryName().toString()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> info, ITooltipFlag flag) {
        super.appendHoverText(stack, world, info, flag);
        if (stack.hasTag()) {
            if (!MiscHelper.nullOrEmpty(stack.getTagElement("genetics"))) {
                CompoundNBT tag = stack.getOrCreateTagElement("genetics");
                float researched = tag.getFloat("researched");
                if (researched > 0) {
                    ListNBT experiments = tag.getList("deceases", 8);
                    if (experiments.isEmpty()) {
                        info.add(ModTexts.NO_DECEASES);
                    } else {
                        info.add(ModTexts.DECEASES_FOUND);
                        for (int i = 0; i < experiments.size(); i++) {
                            info.add(new StringTextComponent(experiments.getString(i)));
                        }
                    }
                    info.add(StringTextComponent.EMPTY);
                }
                if (researched > 1 / 3f) {
                    if (tag.contains("race")) {
                        info.add(new TranslationTextComponent(String.format("race.%s.%s", (Object[]) tag.getString("race").split(":"))));
                        if (flag.isAdvanced()) {
                            info.add(new StringTextComponent("  " + tag.getString("race")).withStyle(TextFormatting.DARK_GRAY));
                        }
                        info.add(StringTextComponent.EMPTY);
                    }
                    if (tag.contains("ownerName")) {
                        info.add(new StringTextComponent(String.format("Genes owner: %s", tag.getString("ownerName"))));
                        info.add(StringTextComponent.EMPTY);
                    }
                }
                if (researched > 2 / 3f) {
                    if (tag.contains("devil_fruit")) {
                        ResourceLocation fruit = new ResourceLocation(tag.getString("devil_fruit"));
                        info.add(new TranslationTextComponent(String.format("item.%s.%s", fruit.getNamespace(), fruit.getPath())));
                        Item item = ForgeRegistries.ITEMS.getValue(fruit);
                        if (item instanceof AkumaNoMiItem) {
                            FruitType type = ((AkumaNoMiItem) item).getType();
                            info.add(new StringTextComponent(type.getName()).withStyle(type.getColor()));
                            float abilitiesFound = researched * 3f - 2;
                            ListNBT abilities = tag.getList("df_abilities", 8);
                            for (int i = 0; i < abilities.size(); i++) {
                                TextComponent name = new StringTextComponent("  " + new TranslationTextComponent(String.format("ability.%s.%s", (Object[]) abilities.getString(i).split(":"))).getString());
                                if (abilitiesFound * abilities.size() < i) {
                                    name = (TextComponent) name.withStyle(TextFormatting.GRAY, TextFormatting.OBFUSCATED);
                                }
                                info.add(name);
                            }
                        }
                    } else {
                        info.add(ModTexts.NO_FRUIT);
                    }
                    info.add(StringTextComponent.EMPTY);
                }
                info.add(new StringTextComponent(new TranslationTextComponent(ModI18n.GUI_QUEST_PROGRESS).getString() + String.format(": %.1f%%", researched * 100)));
            }
            if (!MiscHelper.nullOrEmpty(stack.getTagElement("potion"))) {
                addPotionTooltip(stack, info);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void addPotionTooltip(ItemStack stack, List<ITextComponent> info) {
        List<EffectInstance> list = PotionUtils.getAllEffects(stack.getOrCreateTagElement("potion"));
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (list.isEmpty()) {
            info.add(new TranslationTextComponent("effect.none").withStyle(TextFormatting.GRAY));
        } else {
            for(EffectInstance effectinstance : list) {
                IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(effectinstance.getDescriptionId());
                Effect effect = effectinstance.getEffect();
                Map<Attribute, AttributeModifier> map = effect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierValue(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (effectinstance.getAmplifier() > 0) {
                    TextComponent component = new TranslationTextComponent("potion.potency." + effectinstance.getAmplifier());
                    if (effectinstance.getAmplifier() > 5) {
                        component = new StringTextComponent(String.valueOf(effectinstance.getAmplifier()));
                    }
                    iformattabletextcomponent = new TranslationTextComponent("potion.withAmplifier", iformattabletextcomponent, component);
                }

                if (effectinstance.getDuration() > 20) {
                    iformattabletextcomponent = new TranslationTextComponent("potion.withDuration", iformattabletextcomponent, EffectUtils.formatDuration(effectinstance, (float) 1.0));
                }

                info.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }

        if (!list1.isEmpty()) {
            info.add(StringTextComponent.EMPTY);
            info.add((new TranslationTextComponent("potion.whenDrank")).withStyle(TextFormatting.DARK_PURPLE));

            for(Pair<Attribute, AttributeModifier> pair : list1) {
                AttributeModifier attributemodifier2 = pair.getSecond();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributemodifier2.getAmount();
                } else {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D) {
                    info.add((new TranslationTextComponent("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(pair.getFirst().getDescriptionId()))).withStyle(TextFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 = d1 * -1.0D;
                    info.add((new TranslationTextComponent("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(pair.getFirst().getDescriptionId()))).withStyle(TextFormatting.RED));
                }
            }
        }

    }

    public static ItemStack mix(ItemStack base, ItemStack addition, PlayerEntity player) {
        if (base.getItem() != ModItems.SYRINGE.get() || addition.getItem() != ModItems.SYRINGE.get()) {
            return ItemStack.EMPTY;
        }
        ItemStack mixed = new ItemStack(ModItems.SYRINGE.get());
        CompoundNBT tag = mixed.getOrCreateTagElement("genetics");
        CompoundNBT baseTag = base.getOrCreateTagElement("genetics");
        CompoundNBT additionTag = addition.getOrCreateTagElement("genetics");
        tag.putString("blood", baseTag.getString("blood"));
        if (baseTag.contains("race") && additionTag.contains("race") &&
                (!baseTag.getString("race").equalsIgnoreCase(additionTag.getString("race")) ||
                baseTag.getString("race").equalsIgnoreCase(ModRaces.HYBRID.getId().toString()))
        ) {
            Integer pristineRace = null;
            for (ScientistScreen.PristineRaces race : ScientistScreen.PristineRaces.values()) {
                if (race.getId().toString().equals(baseTag.getString("race"))) {
                    pristineRace = pristineRace == null ? 1 : pristineRace + 1;
                }
                if (race.getId().toString().equals(additionTag.getString("race"))) {
                    pristineRace = pristineRace == null ? -1 : pristineRace - 1;
                }
            }
            if (pristineRace == null) {
                tag.putString("race", ModRaces.HYBRID.getId().toString());
                CompoundNBT hybridTag = new CompoundNBT();
                CompoundNBT baseHybridTag = baseTag.getCompound("genome");
                CompoundNBT additionHybridTag = additionTag.getCompound("genome");
                for (ScientistScreen.HybridRaces race : ScientistScreen.HybridRaces.values()) {
                    float val = 0;
                    if (race.getId().toString().equals(baseTag.getString("race"))) {
                        val += 0.5f;
                    } else if (baseHybridTag.contains(race.getId().toString())) {
                        val += 0.5f * baseHybridTag.getFloat(race.getId().toString());
                    }
                    if (race.getId().toString().equals(additionTag.getString("race"))) {
                        val += 0.5f;
                    } else if (additionHybridTag.contains(race.getId().toString())) {
                        val += 0.5f * additionHybridTag.getFloat(race.getId().toString());
                    }
                    hybridTag.putFloat(race.getId().toString(), val);
                }
            } else {
                if (pristineRace == 0) {
                    tag.putString("race", player.getRandom().nextBoolean() ? baseTag.getString("race") : additionTag.getString("race"));
                } else if (pristineRace == 1) {
                    tag.putString("race", baseTag.getString("race"));
                } else {
                    tag.putString("race", additionTag.getString("race"));
                }
            }
        } else if (baseTag.contains("race") || baseTag.getString("race").equalsIgnoreCase(additionTag.getString("race"))) {
            tag.putString("race", baseTag.getString("race"));
        } else {
            tag.putString("race", additionTag.getString("race"));
        }
        ListNBT experiments = new ListNBT();
        addAll(experiments, baseTag, "deceases");
        addAll(experiments, additionTag, "deceases");
        tag.put("deceases", experiments);
        tag.putFloat("researched", (baseTag.getFloat("researched") + additionTag.getFloat("researched")) / 2);
        if (baseTag.contains("devil_fruit") && additionTag.contains("devil_fruit") && !baseTag.getString("devil_fruit").equalsIgnoreCase(additionTag.getString("devil_fruit"))) {
            //TODO: sophisticated system
        } else if (baseTag.contains("devil_fruit") && additionTag.contains("devil_fruit")) {
            tag.putString("devil_fruit", baseTag.getString("devil_fruit"));
            ListNBT abilities = new ListNBT();
            addAll(abilities, baseTag, "df_abilities");
            addAll(abilities, additionTag, "df_abilities");
            tag.put("df_abilities", abilities);
        } else if (baseTag.contains("devil_fruit")) {
            tag.putString("devil_fruit", baseTag.getString("devil_fruit"));
            tag.put("df_abilities", baseTag.getList("df_abilities", 8));
        } else if (additionTag.contains("devil_fruit")) {
            tag.putString("devil_fruit", additionTag.getString("devil_fruit"));
            tag.put("df_abilities", additionTag.getList("df_abilities", 8));
        }
        if (baseTag.contains("owner")) {
            tag.putUUID("owner", baseTag.getUUID("owner"));
            tag.putString("ownerName", baseTag.getString("ownerName"));
        } else if (additionTag.contains("owner")) {
            tag.putUUID("owner", additionTag.getUUID("owner"));
            tag.putString("ownerName", additionTag.getString("ownerName"));
        }
        if (!MiscHelper.nullOrEmpty(base.getTagElement("potion")) || !MiscHelper.nullOrEmpty(addition.getTagElement("potion"))) {
            CompoundNBT potionTag = mixed.getOrCreateTagElement("potion");
            CompoundNBT basePotionTag = base.getOrCreateTagElement("potion");
            CompoundNBT additionPotionTag = addition.getTagElement("potion");
            Map<Effect, EffectInstance> effects = new HashMap<>();
            for (EffectInstance instance : PotionUtils.getAllEffects(basePotionTag)) {
                effects.put(instance.getEffect(), instance);
            }
            for (EffectInstance instance : PotionUtils.getAllEffects(additionPotionTag)) {
                if (effects.containsKey(instance.getEffect())) {
                    EffectInstance otherInstance = effects.get(instance.getEffect());
                    float randomDuration = getRandom(otherInstance.getDuration(), instance.getDuration(), player.getRandom().nextFloat());
                    float randomAmplifier = getRandom(otherInstance.getAmplifier() + 1, instance.getAmplifier() + 1, player.getRandom().nextFloat());
                    if (inMaxAmp0(instance.getEffect())) {
                        randomAmplifier = 0;
                    }
                    EffectInstance newEffect = new EffectInstance(instance.getEffect(), (int) randomDuration, (int) randomAmplifier - 1, instance.isAmbient() && otherInstance.isAmbient(), instance.isVisible() && otherInstance.isVisible());
                    effects.put(instance.getEffect(), newEffect);
                } else {
                    effects.put(instance.getEffect(), instance);
                }
            }
            setCustomEffects(effects.values(), potionTag);
        }
        return mixed;
    }

    private static boolean inMaxAmp0(Effect effect) {
        if (MAX_EFFECTS.containsKey(effect)) {
            return MAX_EFFECTS.get(effect);
        }
        int maxAmp = 0;
        for (Potion potion : ForgeRegistries.POTION_TYPES.getValues()) {
            for (EffectInstance potionEffect : potion.getEffects()) {
                if (potionEffect.getEffect() == effect) {
                    maxAmp = Math.max(maxAmp, potionEffect.getAmplifier());
                }
            }
        }
        MAX_EFFECTS.put(effect, maxAmp == 0);
        return maxAmp == 0;
    }

    private static float getRandom(float val1, float val2, float rand) {
        float duration;
        if (rand < 0.1f) {
            duration = Math.min(val1, val2);
        } else if (rand < 0.9f) {
            duration = Math.max(val1, val2);
        } else {
            duration = Math.max(val1, val2) + Math.min(val2, val1) * (rand - 0.9f) * 10;
        }
        return duration;
    }

    public static void setCustomEffects(Collection<EffectInstance> p_185184_1_, CompoundNBT potion) {
        ListNBT listnbt = potion.getList("CustomPotionEffects", 9);

        for (EffectInstance effectinstance : p_185184_1_) {
            listnbt.add(effectinstance.save(new CompoundNBT()));
        }

        potion.put("CustomPotionEffects", listnbt);
    }

    private static void addAll(ListNBT experiments, CompoundNBT baseTag, String name) {
        ListNBT baseExperiments = baseTag.getList(name, 8);
        for (INBT experiment : baseExperiments) {
            if (!experiments.contains(experiment)) {
                experiments.add(experiment);
            }
        }
    }
}
