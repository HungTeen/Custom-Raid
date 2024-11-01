package hungteen.craid.api.raid;

import com.mojang.serialization.MapCodec;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-11-27 16:56
 **/
public interface SpawnType<P extends SpawnComponent> {

    /**
     * Get the method to codec spawn component.
     * @return Spawn codec.
     */
    MapCodec<P> codec();

}
