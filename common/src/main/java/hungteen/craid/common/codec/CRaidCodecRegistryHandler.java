package hungteen.craid.common.codec;

import hungteen.craid.common.codec.position.CRaidPositionComponents;
import hungteen.craid.common.codec.position.CRaidPositionTypes;
import hungteen.craid.common.codec.raid.CRaidRaidComponents;
import hungteen.craid.common.codec.raid.CRaidRaidTypes;
import hungteen.craid.common.codec.result.CRaidResultComponents;
import hungteen.craid.common.codec.result.CRaidResultTypes;
import hungteen.craid.common.codec.spawn.CRaidSpawnComponents;
import hungteen.craid.common.codec.spawn.CRaidSpawnTypes;
import hungteen.craid.common.codec.wave.CRaidWaveComponents;
import hungteen.craid.common.codec.wave.CRaidWaveTypes;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 15:26
 **/
public class CRaidCodecRegistryHandler {

    public static void initialize() {
        CRaidPositionTypes.registry().initialize();
        CRaidPositionComponents.registry().initialize();
        CRaidResultTypes.registry().initialize();
        CRaidResultComponents.registry().initialize();
        CRaidSpawnTypes.registry().initialize();
        CRaidSpawnComponents.registry().initialize();
        CRaidWaveTypes.registry().initialize();
        CRaidWaveComponents.registry().initialize();
        CRaidRaidTypes.registry().initialize();
        CRaidRaidComponents.registry().initialize();
    }

}
