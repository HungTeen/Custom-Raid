package hungteen.craid.api.raid;

import com.mojang.serialization.MapCodec;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-11-27 20:48
 **/
public interface RaidType<P extends RaidComponent> {

    /**
     * Get the method to codec raid.
     * @return Codec method.
     */
    MapCodec<P> codec();

}
