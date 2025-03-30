package net.warcar.non_fruit_rework.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.pixelatedw.mineminenomi.api.entities.ICommandReceiver;
import xyz.pixelatedw.mineminenomi.api.enums.NPCCommand;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.PacifistaEntity;

import javax.annotation.Nullable;

@Mixin(PacifistaEntity.class)
public abstract class PacifistaMixin extends OPEntity implements ICommandReceiver {
    @Shadow public abstract NPCCommand getCurrentCommand();

    @Shadow @Nullable public abstract LivingEntity getLastCommandSender();

    private PacifistaMixin(EntityType type, World world) {
        super(type, world);
    }

    @Override
    public boolean canReceiveCommandFrom(LivingEntity livingEntity) {
        IEntityStats props = EntityStatsCapability.get(livingEntity);
        boolean frendly = this.getEntityStats().getFaction().equals(props.getFaction());
        if (!frendly) {
            return false;
        }
        if (this.getCurrentCommand() != NPCCommand.IDLE && this.getLastCommandSender() != livingEntity && (this.getEntityStats().isMarine() || this.getEntityStats().isRevolutionary())) {
            IEntityStats senderProps = EntityStatsCapability.get(this.getLastCommandSender());
            if (this.getEntityStats().isMarine() && senderProps.getMarineRank().ordinal() >= props.getMarineRank().ordinal()) {
                return false;
            } else return !this.getEntityStats().isRevolutionary() || senderProps.getRevolutionaryRank().ordinal() < props.getRevolutionaryRank().ordinal();
        }
        return true;
    }
}
