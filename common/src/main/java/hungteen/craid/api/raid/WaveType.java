package hungteen.craid.api.raid;

import com.mojang.serialization.MapCodec;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-11-27 20:21
 **/
public interface WaveType<P extends WaveComponent> {

    /**
     * Get the method to codec wave.
     * @return Codec method.
     */
    MapCodec<P> codec();

}
