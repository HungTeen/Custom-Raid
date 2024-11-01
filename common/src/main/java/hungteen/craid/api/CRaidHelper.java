package hungteen.craid.api;

import hungteen.htlib.api.util.helper.HTModIDHelper;
import net.minecraft.resources.ResourceLocation;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 14:48
 **/
public interface CRaidHelper {

    HTModIDHelper HELPER = CRaidAPI::id;

    static HTModIDHelper get(){
        return HELPER;
    }

    static ResourceLocation prefix(String path) {
        return get().prefix(path);
    }
}
