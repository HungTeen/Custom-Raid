package hungteen.craid.platform;

import hungteen.craid.api.capability.RaidCapability;
import hungteen.craid.common.world.raid.HTRaidImpl;
import hungteen.htlib.api.util.helper.ServiceHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * 一个用来兼容不同平台方法的接口，在不同的平台有不同的实现。
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/22 8:56
 */
public interface CRaidPlatformAPI {

    CRaidPlatformAPI INSTANCE = ServiceHelper.findService(CRaidPlatformAPI.class);

    /**
     * Obtain the Mod API, either a valid implementation if mod is present, else
     * a dummy instance instead if mod is absent.
     */
    static CRaidPlatformAPI get() {
        return INSTANCE;
    }

    /* Event Related */

    void postWaveStartEvent(Level level, HTRaidImpl raid, int currentWave);

    void postWaveFinishEvent(Level level, HTRaidImpl raid, int currentWave);

    void postRaidFinishEvent(Level level, HTRaidImpl raid, boolean victory);

    /* Cap Related */

    Optional<? extends RaidCapability> getRaidCap(Entity entity);

}
