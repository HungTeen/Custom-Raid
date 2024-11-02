package hungteen.craid;

import hungteen.craid.api.capability.RaidCapability;
import hungteen.craid.api.event.RaidEvent;
import hungteen.craid.common.attachment.CRaidAttachments;
import hungteen.craid.common.world.raid.HTRaidImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Optional;

/**
 * @program: HTLib
 * @author: PangTeen
 * @create: 2024/10/22 15:13
 **/
public class CRaidNeoAPI implements CRaidPlatformAPI {

    @Override
    public void postWaveStartEvent(Level level, HTRaidImpl raid, int currentWave) {
        NeoForge.EVENT_BUS.post(new RaidEvent.WaveStartEvent(level, raid, currentWave));
    }

    @Override
    public void postWaveFinishEvent(Level level, HTRaidImpl raid, int currentWave) {
        NeoForge.EVENT_BUS.post(new RaidEvent.WaveFinishEvent(level, raid, currentWave));
    }

    @Override
    public void postRaidFinishEvent(Level level, HTRaidImpl raid, boolean victory) {
        NeoForge.EVENT_BUS.post(new RaidEvent.RaidFinishEvent(level, raid, victory));
    }

    @Override
    public Optional<? extends RaidCapability> getRaidCap(Entity entity) {
        return Optional.of(entity.getData(CRaidAttachments.RAID));
    }
}
