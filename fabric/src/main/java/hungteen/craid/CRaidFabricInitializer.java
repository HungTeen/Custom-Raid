package hungteen.craid;

import hungteen.craid.common.CRaidDummyEntities;
import hungteen.craid.common.CRaidFabricRaidHandler;
import hungteen.craid.common.CRaidSounds;
import hungteen.craid.common.codec.CRaidCodecRegistryHandler;
import hungteen.craid.common.command.CRaidCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

/**
 * @program: HTLib
 * @author: PangTeen
 * @create: 2024/10/28 8:46
 **/
public class CRaidFabricInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        // Vanilla Registry.
        CRaidSounds.registry().initialize();

        // Custom Registry.
        CRaidDummyEntities.initialize();

        // Codec Registry.
        CRaidCodecRegistryHandler.initialize();


        CommandRegistrationCallback.EVENT.register((dispatcher, context, environment) -> {
            CRaidCommand.register(dispatcher, context);
        });
        CRaidFabricRaidHandler.register();

    }
}