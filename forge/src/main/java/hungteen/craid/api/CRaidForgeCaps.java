package hungteen.craid.api;

import hungteen.craid.api.capability.ForgeRaidCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 20:27
 **/
public interface CRaidForgeCaps {

    Capability<ForgeRaidCapability> RAID_CAP = CapabilityManager.get(new CapabilityToken<>() {});
}
