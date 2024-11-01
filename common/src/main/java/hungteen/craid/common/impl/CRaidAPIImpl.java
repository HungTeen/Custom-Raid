package hungteen.craid.common.impl;

import hungteen.craid.api.CRaidAPI;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/22 9:02
 */
public class CRaidAPIImpl implements CRaidAPI {

    @Override
    public int apiVersion() {
        return 1;
    }

}
