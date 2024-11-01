package hungteen.craid.common.codec.spawn;

import com.mojang.serialization.MapCodec;
import hungteen.craid.api.raid.SpawnComponent;
import hungteen.craid.api.raid.SpawnType;
import hungteen.craid.api.CRaidHelper;
import hungteen.htlib.api.registry.HTCustomRegistry;
import hungteen.htlib.common.impl.registry.HTRegistryManager;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/6/28 16:05
 */
public interface HTLibSpawnTypes {

    HTCustomRegistry<SpawnType<?>> TYPES = HTRegistryManager.custom(CRaidHelper.prefix("spawn_type"));

    SpawnType<OnceSpawn> ONCE = register("once", OnceSpawn.CODEC);
    SpawnType<DurationSpawn> DURATION = register("duration", DurationSpawn.CODEC);

    static <T extends SpawnComponent> SpawnType<T> register(String name, MapCodec<T> codec) {
        return registry().register(CRaidHelper.prefix(name), new SpawnTypeImpl<>(codec));
    }

    static HTCustomRegistry<SpawnType<?>> registry() {
        return TYPES;
    }

    record SpawnTypeImpl<P extends SpawnComponent>(MapCodec<P> codec) implements SpawnType<P> {

    }
}
