package hungteen.craid.common.codec;

import hungteen.craid.common.codec.position.HTLibPositionComponents;
import hungteen.craid.common.codec.position.HTLibPositionTypes;
import hungteen.craid.common.codec.raid.HTLibRaidComponents;
import hungteen.craid.common.codec.raid.HTLibRaidTypes;
import hungteen.craid.common.codec.result.HTLibResultComponents;
import hungteen.craid.common.codec.result.HTLibResultTypes;
import hungteen.craid.common.codec.spawn.HTLibSpawnComponents;
import hungteen.craid.common.codec.spawn.HTLibSpawnTypes;
import hungteen.craid.common.codec.wave.HTLibWaveComponents;
import hungteen.craid.common.codec.wave.HTLibWaveTypes;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 15:26
 **/
public class CRaidCodecRegistryHandler {

    public static void initialize() {
        HTLibPositionTypes.registry().initialize();
        HTLibPositionComponents.registry().initialize();
        HTLibResultTypes.registry().initialize();
        HTLibResultComponents.registry().initialize();
        HTLibSpawnTypes.registry().initialize();
        HTLibSpawnComponents.registry().initialize();
        HTLibWaveTypes.registry().initialize();
        HTLibWaveComponents.registry().initialize();
        HTLibRaidTypes.registry().initialize();
        HTLibRaidComponents.registry().initialize();
    }

}
