package hungteen.craid.common;

import hungteen.craid.api.CRaidAPI;
import hungteen.craid.common.world.raid.DefaultRaid;
import hungteen.craid.platform.CRaidPlatformAPI;
import hungteen.htlib.util.helper.impl.EntityHelper;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 19:48
 **/
@EventBusSubscriber(modid = CRaidAPI.MOD_ID)
public class CRaidNeoRaidHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void damage(LivingDamageEvent event){
        if(EntityHelper.isServer(event.getEntity())) {
            CRaidPlatformAPI.get().getRaidCap(event.getEntity()).ifPresent(capability -> {
                if (capability.getRaid() instanceof DefaultRaid) {
                    ((DefaultRaid) capability.getRaid()).addDefender(event.getEntity());
                }
            });
        }
    }

}
