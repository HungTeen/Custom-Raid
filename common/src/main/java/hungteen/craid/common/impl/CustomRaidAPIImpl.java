package hungteen.craid.common.impl;

import hungteen.craid.api.CustomRaidAPI;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/22 9:02
 */
public class CustomRaidAPIImpl implements CustomRaidAPI {

    @Override
    public int apiVersion() {
        return 1;
    }

}
