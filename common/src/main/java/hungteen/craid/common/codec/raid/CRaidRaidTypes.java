package hungteen.craid.common.codec.raid;

import com.mojang.serialization.MapCodec;
import hungteen.craid.api.raid.RaidComponent;
import hungteen.craid.api.raid.RaidType;
import hungteen.craid.api.CRaidHelper;
import hungteen.htlib.api.registry.HTCustomRegistry;
import hungteen.htlib.common.impl.registry.HTRegistryManager;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/6/28 15:40
 */
public interface CRaidRaidTypes {

    HTCustomRegistry<RaidType<?>> TYPES = HTRegistryManager.custom(CRaidHelper.get().prefix("raid_type"));

    RaidType<CommonRaid> COMMON = register("common", CommonRaid.CODEC);

    static <T extends RaidComponent> RaidType<T> register(String name, MapCodec<T> codec) {
        return registry().register(CRaidHelper.get().prefix(name), new RaidTypeImpl<>(codec));
    }

    static HTCustomRegistry<RaidType<?>> registry() {
        return TYPES;
    }

    record RaidTypeImpl<P extends RaidComponent>(MapCodec<P> codec) implements RaidType<P> {

    }
}
