package hungteen.craid.api;


import hungteen.craid.api.capability.RaidCapability;
import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.minecraft.util.Unit;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 20:27
 **/
public interface CRaidFabricCaps {

    EntityApiLookup<RaidCapability, Unit> RAID = EntityApiLookup.get(RaidCapability.ID, RaidCapability.class, Unit.class);}
