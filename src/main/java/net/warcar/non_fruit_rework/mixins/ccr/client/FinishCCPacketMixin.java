package net.warcar.non_fruit_rework.mixins.ccr.client;

import net.warcar.non_fruit_rework.enums.Faction;
import net.warcar.non_fruit_rework.enums.Race;
import net.warcar.non_fruit_rework.enums.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.packets.client.CFinishCCPacket;

import java.util.function.Supplier;

@Mixin(CFinishCCPacket.class)
public abstract class FinishCCPacketMixin {
    @Shadow
    @Final
    private static final String[] FACTIONS = Faction.registryNames();

    @Shadow
    @Final
    private static final String[] RACES = Race.registryNames();

    @Shadow
    @Final
    private static final String[] STYLES = Style.registryNames();
}
