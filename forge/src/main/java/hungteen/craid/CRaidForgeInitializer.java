package hungteen.craid;

import hungteen.craid.api.CRaidAPI;
import hungteen.craid.common.codec.CRaidCodecRegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-09-24 23:04
 **/
@Mod(CRaidAPI.MOD_ID)
public class CRaidForgeInitializer {

    public CRaidForgeInitializer(FMLJavaModLoadingContext context) {
        /* Mod Bus Events */
        IEventBus modBus = context.getModEventBus();

        /* Forge Bus Events */
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        CRaidCodecRegistryHandler.initialize();
    }

}
