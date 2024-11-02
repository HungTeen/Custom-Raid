package hungteen.craid.common.codec.position;

import com.mojang.serialization.MapCodec;
import hungteen.craid.api.raid.PositionComponent;
import hungteen.craid.api.raid.PositionType;
import hungteen.craid.api.CRaidHelper;
import hungteen.htlib.api.registry.HTCustomRegistry;
import hungteen.htlib.common.impl.registry.HTRegistryManager;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/6/28 15:51
 */
public interface CRaidPositionTypes {

    HTCustomRegistry<PositionType<?>> TYPES = HTRegistryManager.custom(CRaidHelper.prefix("position_type"));

    PositionType<CenterAreaPosition> CENTER_AREA = register("center_area", CenterAreaPosition.CODEC);
    PositionType<AbsoluteAreaPosition> ABSOLUTE_AREA = register("absolute_area", AbsoluteAreaPosition.CODEC);

    private static <T extends PositionComponent> PositionType<T> register(String name, MapCodec<T> codec) {
        return registry().register(CRaidHelper.prefix(name), new PositionTypeImpl<>(codec));
    }

    static HTCustomRegistry<PositionType<?>> registry() {
        return TYPES;
    }

    record PositionTypeImpl<P extends PositionComponent>(MapCodec<P> codec) implements PositionType<P> {

    }

}
