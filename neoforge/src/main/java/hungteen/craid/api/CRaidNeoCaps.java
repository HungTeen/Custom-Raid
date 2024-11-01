package hungteen.craid.api;


import hungteen.craid.api.capability.RaidCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 20:27
 **/
public interface CRaidNeoCaps {

    EntityCapability<RaidCapability, Void> RAID_CAP = EntityCapability.createVoid(RaidCapability.ID, RaidCapability.class);
}
