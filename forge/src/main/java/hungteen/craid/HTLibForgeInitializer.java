package hungteen.craid;

import hungteen.craid.api.CustomRaidAPI;
import hungteen.craid.common.*;
import hungteen.craid.common.capability.PlayerCapabilityManager;
import hungteen.craid.common.capability.raid.RaidCapProvider;
import hungteen.craid.common.command.HTCommand;
import hungteen.craid.common.command.HTLibCommandArgumentInfos;
import hungteen.craid.common.entity.HTLibEntities;
import hungteen.craid.common.impl.BoatTypes;
import hungteen.craid.common.impl.position.HTLibPositionComponents;
import hungteen.craid.common.impl.position.HTLibPositionTypes;
import hungteen.craid.common.impl.raid.HTLibRaidComponents;
import hungteen.craid.common.impl.raid.HTLibRaidTypes;
import hungteen.craid.common.impl.result.HTLibResultComponents;
import hungteen.craid.common.impl.result.HTLibResultTypes;
import hungteen.craid.common.impl.spawn.HTLibSpawnComponents;
import hungteen.craid.common.impl.spawn.HTLibSpawnTypes;
import hungteen.craid.common.impl.wave.HTLibWaveComponents;
import hungteen.craid.common.impl.wave.HTLibWaveTypes;
import hungteen.craid.common.network.NetworkHandler;
import hungteen.craid.common.world.entity.HTLibDummyEntities;
import hungteen.craid.data.HTDataGenHandler;
import hungteen.craid.util.helper.impl.HTLibHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-09-24 23:04
 **/
@Mod(CustomRaidAPI.MOD_ID)
public class HTLibForgeInitializer {

    private static final Logger LOGGER = CustomRaidAPI.logger();
    public static final String MOD_ID = CustomRaidAPI.MOD_ID;

    public HTLibForgeInitializer(FMLJavaModLoadingContext context) {
        /* Mod Bus Events */
        IEventBus modBus = context.getModEventBus();

        modBus.addListener(HTLibForgeInitializer::setUp);
        modBus.addListener(HTRegistryHandler::postRegister);
        modBus.addListener(EventPriority.LOW, HTSuitHandler::register);
        modBus.addListener(HTSuitHandler::clear);
        modBus.addListener(HTDataGenHandler::gatherData);
        modBus.addListener(HTSuitHandler::fillInCreativeTab);
        modBus.addListener(HTSuitHandler::addAttributes);
//        modBus.addListener(HTSuitHandler::addSpawnPlacements);
        modBus.addListener(HTSuitHandler::clear);
        modBus.addListener(HTRegistryHandler::registerNewDatapack);
        HTRegistryHandler.register(modBus);

        /* Forge Bus Events */
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(DummyEntityHandler::tick);
        forgeBus.addListener(PlayerCapabilityManager::tick);
        forgeBus.addGenericListener(Entity.class, HTLibForgeInitializer::attachCapabilities);
        forgeBus.addListener(HTRegistryHandler::onDatapackSync);
        forgeBus.addListener((RegisterCommandsEvent event) -> HTCommand.register(event.getDispatcher(), event.getBuildContext()));

        initialize();
    }

    public void initialize() {
        HTLibEntities.registry().initialize();
        HTLibSounds.registry().initialize();
        HTLibCommandArgumentInfos.registry().initialize();
        HTLibDummyEntities.registry().initialize();

        HTLibPositionTypes.registry().initialize();
        HTLibPositionComponents.registry().initialize();
        HTLibResultTypes.registry().initialize();
        HTLibResultComponents.registry().initialize();
        HTLibSpawnTypes.registry().initialize();
        HTLibSpawnComponents.registry().initialize();
        HTLibWaveTypes.registry().initialize();
        HTLibWaveComponents.registry().initialize();
        HTLibRaidTypes.registry().initialize();
        HTLibRaidComponents.registry().initialize();
    }

    public static void setUp(FMLCommonSetupEvent event) {
        NetworkHandler.init();
        HTResourceManager.init();
        event.enqueueWork(() -> {
            BoatTypes.register();
            HTSuitHandler.setUp();
        });
    }

    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {

        } else {
            event.addCapability(HTLibHelper.prefix("raid"), new RaidCapProvider(event.getObject()));
        }
    }

}
