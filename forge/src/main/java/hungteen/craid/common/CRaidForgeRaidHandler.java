package hungteen.craid.common;

import hungteen.craid.api.CRaidAPI;
import hungteen.craid.api.capability.RaidCapability;
import hungteen.craid.common.capability.raid.ForgeRaidCapProvider;
import hungteen.craid.common.world.raid.DefaultRaid;
import hungteen.craid.CRaidPlatformAPI;
import hungteen.htlib.util.helper.impl.EntityHelper;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 19:48
 **/
@Mod.EventBusSubscriber(modid = CRaidAPI.MOD_ID)
public class CRaidForgeRaidHandler {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(RaidCapability.ID, new ForgeRaidCapProvider(event.getObject()));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void damage(LivingDamageEvent event){
        if(EntityHelper.isServer(event.getEntity()) && event.getSource().getEntity() != null){
            CRaidPlatformAPI.get().getRaidCap(event.getEntity()).ifPresent(capability -> {
                if(capability.getRaid() instanceof DefaultRaid){
                    ((DefaultRaid)capability.getRaid()).addDefender(event.getSource().getEntity());
                }
            });
        }
    }

}
