package hungteen.craid.api;

import com.mojang.logging.LogUtils;
import hungteen.craid.api.util.ServiceUtil;
import org.slf4j.Logger;

/**
 * HTLib 对外提供的 API 接口，不依赖于平台，只有一个有效实现。
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/22 8:56
 */
public interface CustomRaidAPI {

    String MOD_ID = "craid";

    Logger LOGGER = LogUtils.getLogger();

    CustomRaidAPI INSTANCE = ServiceUtil.findService(CustomRaidAPI.class, () -> new CustomRaidAPI() {});

    /**
     * Obtain the Mod API, either a valid implementation if mod is present, else
     * a dummy instance instead if mod is absent.
     */
    static CustomRaidAPI get() {
        return INSTANCE;
    }

    static String id() {
        return MOD_ID;
    }

    /**
     * @return the log instance for the mod.
     */
    static Logger logger() {
        return LOGGER;
    }

    /**
     * @return A unique version number for this version of the API.
     */
    default int apiVersion() {
        return 0;
    }

}
