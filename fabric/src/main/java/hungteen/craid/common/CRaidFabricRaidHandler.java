package hungteen.craid.common;

import hungteen.craid.CRaidPlatformAPI;
import hungteen.craid.common.world.raid.DefaultRaid;
import hungteen.htlib.util.helper.impl.EntityHelper;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 19:48
 **/
public class CRaidFabricRaidHandler {

    public static void register(){
        handleDamage();
    }

    private static void handleDamage(){
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseAmount, amount, blocked) -> {
            if(EntityHelper.isServer(entity) && source.getEntity() != null){
                CRaidPlatformAPI.get().getRaidCap(entity).ifPresent(capability -> {
                    if(capability.getRaid() instanceof DefaultRaid){
                        ((DefaultRaid)capability.getRaid()).addDefender(source.getEntity());
                    }
                });
            }
        });
    }

}
