package hungteen.craid.common;

import hungteen.craid.api.CRaidHelper;
import hungteen.craid.common.world.raid.DefaultRaid;
import hungteen.htlib.common.world.entity.DummyEntityType;
import hungteen.htlib.common.world.entity.HTLibDummyEntities;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/2 8:31
 **/
public interface CRaidDummyEntities {

    DummyEntityType<DefaultRaid> DEFAULT_RAID = HTLibDummyEntities.register(new DummyEntityType<>(CRaidHelper.prefix("raid"), DefaultRaid::new));

    /**
     * Load the class.
     */
    static void initialize() {
    }
}
