package net.warcar.non_fruit_rework.mixins.ccr.client;

import com.google.common.base.Strings;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.warcar.non_fruit_rework.NonFruitReworkMod;
import net.warcar.non_fruit_rework.data.entity.mastery.IMasteryData;
import net.warcar.non_fruit_rework.data.entity.mastery.MasteryDataCapability;
import net.warcar.non_fruit_rework.enums.Faction;
import net.warcar.non_fruit_rework.enums.Race;
import net.warcar.non_fruit_rework.enums.ranks.IRank;
import net.warcar.non_fruit_rework.init.ModConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.HakiHelper;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.events.devilfruits.RandomFruitEvents;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.screens.PlayerStatsScreen;
import xyz.pixelatedw.mineminenomi.wypi.WyHelper;

@Mixin(PlayerStatsScreen.class)
public abstract class PlayerStatsScreenMixin extends Screen {
    @Shadow private IEntityStats entityStatsProps;

    @Shadow private IDevilFruit devilFruitProps;

    @Shadow protected abstract void drawItemStack(ItemStack itemStack, int x, int y, String string);

    @Shadow @Final private PlayerEntity player;

    private IMasteryData masteryDataProps;

    protected PlayerStatsScreenMixin(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addInit(CallbackInfo ci) {
        this.masteryDataProps = MasteryDataCapability.get(this.player);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void reRender(MatrixStack matrixStack, int x, int y, float f, CallbackInfo ci) {
        ci.cancel();
        this.renderBackground(matrixStack);
        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;
        String colaLabel = (new TranslationTextComponent(ModI18n.GUI_COLA)).getString();
        String dorikiLabel = (new TranslationTextComponent(ModI18n.GUI_DORIKI)).getString();
        String faction = WyHelper.getResourceName(this.entityStatsProps.getFaction());
        if (Strings.isNullOrEmpty(faction)) {
            faction = "empty";
        }

        String race = WyHelper.getResourceName(this.entityStatsProps.getRace().toLowerCase());
        if (Strings.isNullOrEmpty(race)) {
            race = "empty";
        }

        String subRace = "";
        Race actualRace = Race.getFromName(this.entityStatsProps.getRace());
        if (actualRace != null && actualRace.hasSubRaces()) {
            subRace =  " - " + (new TranslationTextComponent("sub_race." + this.entityStatsProps.getSubRace())).getString();
        }

        String style = WyHelper.getResourceName(this.entityStatsProps.getFightingStyle().toLowerCase());
        if (Strings.isNullOrEmpty(style)) {
            style = "empty";
        }
        HakiHelper.HakiRank masteryRank = HakiHelper.HakiRank.BEGINNER;
        float per = (this.masteryDataProps.getMastery() / (float) ModConfig.INSTANCE.getMaxMastery()) * 100.0F;
        NonFruitReworkMod.LOGGER.info(this.masteryDataProps.getMastery());
        if (per >= 15.0F) {
            if (per >= 15.0F && per < 30.0F) {
                masteryRank = HakiHelper.HakiRank.INITIATE;
            } else if (per >= 30.0F && per < 50.0F) {
                masteryRank = HakiHelper.HakiRank.ADEPT;
            } else if (per >= 50.0F && per < 90.0F) {
                masteryRank = HakiHelper.HakiRank.PROFICIENT;
            } else if (per >= 90.0F) {
                masteryRank = HakiHelper.HakiRank.MASTER;
            }
        }

        String actualRank = "";
        Faction actualFaction = Faction.getFromName(this.entityStatsProps.getFaction());
        if (actualFaction != null && actualFaction.isRanked()) {
            IRank rank = actualFaction.getRank(this.player);
            actualRank = rank != null ? " - " + rank.getLocalizedName() : "";
        }

        String factionActual = (new TranslationTextComponent("faction." + faction)).getString() + actualRank;
        String raceActual = (new TranslationTextComponent("race." + race)).getString() + subRace;
        String styleActual = (new TranslationTextComponent("style." + style)).getString()/* + " - " + masteryRank*/;
        if (this.entityStatsProps.isCyborg()) {
            WyHelper.drawStringWithBorder(this.font, matrixStack, TextFormatting.BOLD + colaLabel + ": " + TextFormatting.RESET + this.entityStatsProps.getCola() + " / " + this.entityStatsProps.getMaxCola(), posX - 50, posY + 50, -1);
        }

        WyHelper.drawStringWithBorder(this.font, matrixStack, TextFormatting.BOLD + dorikiLabel + ": " + TextFormatting.RESET + Math.round(this.entityStatsProps.getDoriki()), posX - 50, posY + 70, -1);
        WyHelper.drawStringWithBorder(this.font, matrixStack, TextFormatting.BOLD + ModI18n.FACTION_NAME.getString() + ": " + TextFormatting.RESET + factionActual, posX - 50, posY + 90, -1);
        WyHelper.drawStringWithBorder(this.font, matrixStack, TextFormatting.BOLD + ModI18n.RACE_NAME.getString() + ": " + TextFormatting.RESET + raceActual, posX - 50, posY + 110, -1);
        WyHelper.drawStringWithBorder(this.font, matrixStack, TextFormatting.BOLD + ModI18n.STYLE_NAME.getString() + ": " + TextFormatting.RESET + styleActual, posX - 50, posY + 130, -1);
        if (this.entityStatsProps.getBelly() > 0L) {
            WyHelper.drawStringWithBorder(this.font, matrixStack, "" + this.entityStatsProps.getBelly(), posX + 215, posY + 72, -1);
            this.minecraft.textureManager.bind(ModResources.CURRENCIES);
            this.blit(matrixStack, posX + 190, posY + 60, 0, 32, 32, 64);
        }

        if (this.entityStatsProps.getExtol() > 0L) {
            WyHelper.drawStringWithBorder(this.font, matrixStack, "" + this.entityStatsProps.getExtol(), posX + 215, posY + 102, -1);
            this.minecraft.textureManager.bind(ModResources.CURRENCIES);
            this.blit(matrixStack, posX + 190, posY + 89, 34, 32, 64, 86);
        }

        if (this.devilFruitProps.hasAnyDevilFruit()) {
            ItemStack yamiFruit = new ItemStack(ModAbilities.YAMI_YAMI_NO_MI);
            ItemStack df;
            boolean doubleYamiCheck;
            if (this.devilFruitProps.hasDevilFruit(ModAbilities.YAMI_YAMI_NO_MI)) {
                df = new ItemStack(this.devilFruitProps.getDevilFruitItem());
                ResourceLocation dfKey = df.getItem().getRegistryName();
                doubleYamiCheck = this.devilFruitProps.hasYamiPower() && !dfKey.equals(ModAbilities.YAMI_YAMI_NO_MI.getRegistryName());
                int color = -1;
                if (this.devilFruitProps.hasAwakenedFruit()) {
                    color = WyHelper.hexToRGB("#ECA629").getRGB();
                }

                if (doubleYamiCheck) {
                    this.minecraft.font.drawShadow(matrixStack, TextFormatting.BOLD + "" + yamiFruit.getHoverName().getString() + " + " + df.getHoverName().getString(), (float)(posX - 28), (float)(posY + 194), color);
                } else {
                    this.minecraft.font.drawShadow(matrixStack, TextFormatting.BOLD + "" + yamiFruit.getHoverName().getString(), (float)(posX - 28), (float)(posY + 194), color);
                }

                if (doubleYamiCheck) {
                    this.drawItemStack(df, posX - 56, posY + 187, "");
                }

                this.drawItemStack(yamiFruit, posX - 50, posY + 190, "");
            } else {
                df = new ItemStack(this.devilFruitProps.getDevilFruitItem());
                if (!df.isEmpty()) {
                    String fruitName = df.getHoverName().getString();
                    if (RandomFruitEvents.Client.HAS_RANDOMIZED_FRUIT) {
                        AkumaNoMiItem item = ((AkumaNoMiItem)df.getItem()).getReverseShiftedFruit(this.player.level);
                        df = new ItemStack(item);
                    }

                    doubleYamiCheck = false;
                    String dfKey = ((AkumaNoMiItem)df.getItem()).getFruitKey();
                    if (dfKey.equalsIgnoreCase("yami_yami") && this.devilFruitProps.hasYamiPower()) {
                        doubleYamiCheck = true;
                    }

                    int color = -1;
                    if (this.devilFruitProps.hasAwakenedFruit()) {
                        color = WyHelper.hexToRGB("#ECA629").getRGB();
                    }

                    if (this.devilFruitProps.hasYamiPower() && !doubleYamiCheck) {
                        this.minecraft.font.drawShadow(matrixStack, TextFormatting.BOLD + "" + yamiFruit.getHoverName().getString() + " + " + df.getHoverName().getString(), (float)(posX - 28), (float)(posY + 194), color);
                    } else {
                        this.minecraft.font.drawShadow(matrixStack, TextFormatting.BOLD + "" + fruitName, (float)(posX - 28), (float)(posY + 194), color);
                    }

                    if (this.devilFruitProps.hasYamiPower() && !doubleYamiCheck) {
                        this.drawItemStack(yamiFruit, posX - 56, posY + 187, "");
                    }

                    this.drawItemStack(df, posX - 50, posY + 190, "");
                } else {
                    this.minecraft.font.drawShadow(matrixStack, "§4§lUnknown Fruit§r", (float)(posX - 28), (float)(posY + 194), -1);
                }
            }
        }

        super.render(matrixStack, x, y, f);
    }
}
