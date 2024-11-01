package hungteen.craid.api.raid;

import com.mojang.serialization.MapCodec;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/24 10:48
 */
public interface ResultType<P extends ResultComponent> {

    /**
     * Get Codec.
     * @return Codec.
     */
    MapCodec<P> codec();

}