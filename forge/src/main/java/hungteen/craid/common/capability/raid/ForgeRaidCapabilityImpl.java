package hungteen.craid.common.capability.raid;

import hungteen.craid.api.CRaidForgeCaps;
import hungteen.craid.api.capability.ForgeRaidCapability;
import hungteen.craid.common.capability.RaidCapabilityImpl;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-01 09:48
 **/
public class ForgeRaidCapabilityImpl extends RaidCapabilityImpl implements ForgeRaidCapability {

    public static Optional<ForgeRaidCapability> getRaid(Entity entity){
        return entity.getCapability(CRaidForgeCaps.RAID_CAP).resolve();
    }

}
