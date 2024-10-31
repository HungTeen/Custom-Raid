package hungteen.craid.common.impl.position;

import com.mojang.serialization.MapCodec;
import hungteen.craid.api.raid.PositionComponent;
import hungteen.craid.api.raid.PositionType;
import hungteen.craid.api.registry.HTSimpleRegistry;
import hungteen.craid.common.impl.registry.HTRegistryManager;
import hungteen.craid.util.helper.impl.HTLibHelper;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/6/28 15:51
 */
public interface HTLibPositionTypes {

    HTSimpleRegistry<PositionType<?>> TYPES = HTRegistryManager.simple(HTLibHelper.prefix("position_type"));

    PositionType<CenterAreaPosition> CENTER_AREA = register("center_area", CenterAreaPosition.CODEC);
    PositionType<AbsoluteAreaPosition> ABSOLUTE_AREA = register("absolute_area", AbsoluteAreaPosition.CODEC);

    static <T extends PositionComponent> PositionType<T> register(PositionType<T> type) {
        return registry().register(type);
    }

    private static <T extends PositionComponent> PositionType<T> register(String name, MapCodec<T> codec) {
        return register(new PositionTypeImpl<>(name, codec));
    }

    static HTSimpleRegistry<PositionType<?>> registry() {
        return TYPES;
    }

    record PositionTypeImpl<P extends PositionComponent>(String name, MapCodec<P> codec) implements PositionType<P> {

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
