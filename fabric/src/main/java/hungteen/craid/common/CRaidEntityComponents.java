package hungteen.craid.common;

import hungteen.craid.api.capability.RaidCapability;
import net.minecraft.world.entity.Entity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 22:11
 **/
public class CRaidEntityComponents implements EntityComponentInitializer {

    public static final ComponentKey<FabricRaidCapability> RAID = ComponentRegistryV3.INSTANCE.getOrCreate(RaidCapability.ID, FabricRaidCapability.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(Entity.class, RAID, t -> new FabricRaidCapability());
    }
}
