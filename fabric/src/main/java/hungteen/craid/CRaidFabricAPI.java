package hungteen.craid;

import hungteen.craid.api.capability.RaidCapability;
import hungteen.craid.api.event.RaidEvent;
import hungteen.craid.common.CRaidEntityComponents;
import hungteen.craid.common.world.raid.HTRaidImpl;
import hungteen.craid.platform.CRaidPlatformAPI;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * @program: HTLib
 * @author: PangTeen
 * @create: 2024/10/22 15:13
 **/
public class CRaidFabricAPI implements CRaidPlatformAPI {

    @Override
    public void postWaveStartEvent(Level level, HTRaidImpl raid, int currentWave) {
        RaidEvent.WAVE_START.invoker().handle(raid, level, currentWave);
    }

    @Override
    public void postWaveFinishEvent(Level level, HTRaidImpl raid, int currentWave) {
        RaidEvent.WAVE_FINISH.invoker().handle(raid, level, currentWave);
    }

    @Override
    public void postRaidFinishEvent(Level level, HTRaidImpl raid, boolean victory) {
        RaidEvent.RAID_FINISH.invoker().handle(raid, level, victory);
    }

    @Override
    public Optional<? extends RaidCapability> getRaidCap(Entity entity) {
        return Optional.of(CRaidEntityComponents.RAID.get(entity));
    }
}
