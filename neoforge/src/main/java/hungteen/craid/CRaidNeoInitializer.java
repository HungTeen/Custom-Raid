package hungteen.craid;

import hungteen.craid.api.CRaidAPI;
import hungteen.craid.common.attachment.CRaidAttachments;
import hungteen.craid.common.codec.CRaidCodecRegistryHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CRaidAPI.MOD_ID)
public class CRaidNeoInitializer {

    public CRaidNeoInitializer(IEventBus modEventBus, ModContainer modContainer) {
        /* Mod Bus Events */
        CRaidAttachments.ATTACHMENTS.register(modEventBus);

        /* Forge Bus Events */
        IEventBus forgeBus = NeoForge.EVENT_BUS;

        CRaidCodecRegistryHandler.initialize();
    }


}
