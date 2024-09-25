package net.warcar.non_fruit_rework.mixins.ccr;

import net.warcar.non_fruit_rework.enums.Faction;
import net.warcar.non_fruit_rework.enums.Race;
import net.warcar.non_fruit_rework.enums.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.screens.extra.CharacterCreatorSelectionMap;

import java.util.HashMap;

@Mixin(CharacterCreatorSelectionMap.class)
public class CharacterCreatorMapMixin {
    @Inject(method = "createSelectionMap", at = @At("RETURN"), remap = false)
    private static void addInfos(CallbackInfoReturnable<HashMap<String, CharacterCreatorSelectionMap.SelectionInfo>> callbackInfo) {
        HashMap<String, CharacterCreatorSelectionMap.SelectionInfo> map = callbackInfo.getReturnValue();
        if (map == null) {
            map = new HashMap<>();
        }
        else {
            map.clear();
        }
        map.put("random", new CharacterCreatorSelectionMap.SelectionInfo(ModResources.RANDOM, ModI18n.GUI_RANDOM));
        for (String s : Faction.registryNames()) {
            if (!s.equals("random")) map.put(s, Faction.getFromName(s).getInfo());
        }
        for (String s : Race.registryNames()) {
            if (!s.equals("random")) map.put(s, Race.getFromName(s).getInfo());
        }
        for (String s : Style.registryNames()) {
            if (!s.equals("random")) map.put(s, Style.getFromName(s).getInfo());
        }
    }
}
