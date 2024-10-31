package hungteen.craid.common.command;

import hungteen.craid.api.CustomRaidAPI;
import hungteen.craid.common.impl.registry.HTRegistryManager;
import hungteen.craid.common.impl.registry.HTVanillaRegistry;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-01 19:40
 **/
public interface HTLibCommandArgumentInfos {

    HTVanillaRegistry<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = HTRegistryManager.vanilla(Registries.COMMAND_ARGUMENT_TYPE, CustomRaidAPI.id());

    Supplier<ArgumentTypeInfo<?, ?>> DUMMY_ENTITY = ARGUMENT_TYPES.register("dummy_entity", () -> {
        return SingletonArgumentInfo.contextFree(DummyEntityArgument::id);
    });

    static HTVanillaRegistry<ArgumentTypeInfo<?, ?>> registry(){
        return ARGUMENT_TYPES;
    }
}
