package hungteen.craid.common.impl.raid;

import com.mojang.serialization.MapCodec;
import hungteen.craid.api.raid.RaidComponent;
import hungteen.craid.api.raid.RaidType;
import hungteen.craid.api.registry.HTSimpleRegistry;
import hungteen.craid.common.impl.registry.HTRegistryManager;
import hungteen.craid.util.helper.impl.HTLibHelper;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/6/28 15:40
 */
public interface HTLibRaidTypes {

    HTSimpleRegistry<RaidType<?>> TYPES = HTRegistryManager.simple(HTLibHelper.prefix("raid_type"));

    RaidType<CommonRaid> COMMON = register(new RaidTypeImpl<>("common", CommonRaid.CODEC));

    static <T extends RaidComponent> RaidType<T> register(RaidType<T> type) {
        return registry().register(type);
    }

    static HTSimpleRegistry<RaidType<?>> registry() {
        return TYPES;
    }

    record RaidTypeImpl<P extends RaidComponent>(String name, MapCodec<P> codec) implements RaidType<P> {

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
