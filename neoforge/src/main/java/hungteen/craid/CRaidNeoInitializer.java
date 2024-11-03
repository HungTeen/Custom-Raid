package hungteen.craid;

import hungteen.craid.api.CRaidAPI;
import hungteen.craid.common.CRaidDummyEntities;
import hungteen.craid.common.CRaidSounds;
import hungteen.craid.common.attachment.CRaidAttachments;
import hungteen.craid.common.codec.CRaidCodecRegistryHandler;
import hungteen.craid.common.command.CRaidCommand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(CRaidAPI.MOD_ID)
public class CRaidNeoInitializer {

    public CRaidNeoInitializer(IEventBus modEventBus, ModContainer modContainer) {
        /* Mod Bus Events */
        CRaidAttachments.ATTACHMENTS.register(modEventBus);

        /* Forge Bus Events */
        IEventBus forgeBus = NeoForge.EVENT_BUS;
        forgeBus.addListener((RegisterCommandsEvent event) -> {
            CRaidCommand.register(event.getDispatcher(), event.getBuildContext());
        });

        // Vanilla Registry.
        CRaidSounds.registry().initialize();

        // Custom Registry.
        CRaidDummyEntities.initialize();

        // Codec Registry.
        CRaidCodecRegistryHandler.initialize();
    }


}
