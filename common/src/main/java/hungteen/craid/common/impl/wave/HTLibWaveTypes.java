package hungteen.craid.common.impl.wave;

import com.mojang.serialization.MapCodec;
import hungteen.craid.api.raid.WaveComponent;
import hungteen.craid.api.raid.WaveType;
import hungteen.craid.api.registry.HTSimpleRegistry;
import hungteen.craid.common.impl.registry.HTRegistryManager;
import hungteen.craid.util.helper.impl.HTLibHelper;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/6/28 16:10
 */
public interface HTLibWaveTypes {

    HTSimpleRegistry<WaveType<?>> TYPES = HTRegistryManager.createSimple(HTLibHelper.prefix("wave_type"));

    WaveType<CommonWave> COMMON = register(new WaveTypeImpl<>("common", CommonWave.CODEC));

    static <T extends WaveComponent> WaveType<T> register(WaveType<T> type) {
        return registry().register(type);
    }

    static HTSimpleRegistry<WaveType<?>> registry() {
        return TYPES;
    }

    record WaveTypeImpl<P extends WaveComponent>(String name, MapCodec<P> codec) implements WaveType<P> {

        @Override
        public String getName() {
            return name();
        }

        @Override
        public String getModID() {
            return HTLibHelper.get().getModID();
        }
    }

}
