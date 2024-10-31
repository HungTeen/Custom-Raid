package hungteen.craid.common;

import hungteen.craid.common.impl.registry.HTCodecRegistryImpl;
import hungteen.craid.common.impl.registry.HTForgeCustomRegistry;
import hungteen.craid.common.impl.registry.HTForgeVanillaRegistry;
import hungteen.craid.common.impl.registry.HTRegistryManager;
import hungteen.craid.util.helper.JavaHelper;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;

/**
 * @program: HTLib
 * @author: PangTeen
 * @create: 2024/10/22 21:08
 **/
public class HTRegistryHandler {

    public static void postRegister(RegisterEvent event) {
        // TODO group initialize
//        ItemHelper.getCodecRegistry().initialize(event);
//        BlockHelper.getCodecRegistry().initialize(event);
//        BlockHelper.entity().initialize(event);
//        BlockHelper.paint().initialize(event);
//        BlockHelper.banner().initialize(event);
//        EntityHelper.getCodecRegistry().initialize(event);
//        EffectHelper.getCodecRegistry().initialize(event);
//        ParticleHelper.getCodecRegistry().initialize(event);
//        SoundHelper.getCodecRegistry().initialize(event);
//        BiomeHelper.getCodecRegistry().initialize(event);
//        FluidHelper.getCodecRegistry().initialize(event);
//        GameEventHelper.getCodecRegistry().initialize(event);
//        PoiTypeHelper.getCodecRegistry().initialize(event);
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        HTRegistryManager.getCodecRegistries().forEach(registry -> registry.syncToClient(event.getPlayer()));
    }

    public static void registerNewDatapack(DataPackRegistryEvent.NewRegistry event) {
        JavaHelper.castStream(HTRegistryManager.getCodecRegistries().stream(), HTCodecRegistryImpl.class)
                .forEach(registry -> registry.addRegistry(event));
    }

    public static void register(IEventBus modBus) {
        JavaHelper.castStream(HTRegistryManager.getCustomRegistries().stream(), HTForgeCustomRegistry.class)
                .forEach(registry -> {
                    registry.register(modBus);
                });
        JavaHelper.castStream(HTRegistryManager.getVanillaRegistries().stream(), HTForgeVanillaRegistry.class)
                .forEach(registry -> {
                    registry.register(modBus);
                });
    }

}
