package hungteen.craid.api.raid;

import com.mojang.serialization.MapCodec;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/24 10:48
 */
public interface PositionType<P extends PositionComponent> {

    /**
     * Get the method to codec placement.
     * @return Codec method.
     */
    MapCodec<P> codec();

}