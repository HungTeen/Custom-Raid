package hungteen.craid.common.codec.wave;

import com.mojang.serialization.MapCodec;
import hungteen.craid.api.raid.WaveComponent;
import hungteen.craid.api.raid.WaveType;
import hungteen.craid.api.CRaidHelper;
import hungteen.htlib.api.registry.HTCustomRegistry;
import hungteen.htlib.common.impl.registry.HTRegistryManager;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/6/28 16:10
 */
public interface CRaidWaveTypes {

    HTCustomRegistry<WaveType<?>> TYPES = HTRegistryManager.custom(CRaidHelper.prefix("wave_type"));

    WaveType<CommonWave> COMMON = register("common", CommonWave.CODEC);

    static <T extends WaveComponent> WaveType<T> register(String name, MapCodec<T> codec) {
        return registry().register(CRaidHelper.prefix(name), new WaveTypeImpl<>(codec));
    }

    static HTCustomRegistry<WaveType<?>> registry() {
        return TYPES;
    }

    record WaveTypeImpl<P extends WaveComponent>(MapCodec<P> codec) implements WaveType<P> {
    }

}
