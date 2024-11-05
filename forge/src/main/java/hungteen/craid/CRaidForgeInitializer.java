package hungteen.craid;

import hungteen.craid.api.CRaidAPI;
import hungteen.craid.common.CRaidDummyEntities;
import hungteen.craid.common.CRaidSounds;
import hungteen.craid.common.codec.CRaidCodecRegistryHandler;
import hungteen.craid.common.command.CRaidCommand;
import hungteen.craid.data.CRaidDatapackEntriesGen;
import hungteen.htlib.util.ForgeHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
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

        modBus.addListener((GatherDataEvent event) -> {
            event.getGenerator().addProvider(event.includeServer(), new CRaidDatapackEntriesGen(event.getGenerator().getPackOutput(), event.getLookupProvider()));
        });

        /* Forge Bus Events */
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener((RegisterCommandsEvent event) -> {
            CRaidCommand.register(event.getDispatcher(), event.getBuildContext());
        });

        // Vanilla Registry.
        ForgeHelper.initRegistry(CRaidSounds.registry(), modBus);

        // Custom Registry.
        CRaidDummyEntities.initialize();

        // Codec Registry.
        CRaidCodecRegistryHandler.initialize();

    }

}
