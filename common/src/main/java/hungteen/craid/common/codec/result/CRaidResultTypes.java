package hungteen.craid.common.codec.result;

import com.mojang.serialization.MapCodec;
import hungteen.craid.api.raid.ResultComponent;
import hungteen.craid.api.raid.ResultType;
import hungteen.craid.api.CRaidHelper;
import hungteen.htlib.api.registry.HTCustomRegistry;
import hungteen.htlib.common.impl.registry.HTRegistryManager;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/6/28 15:47
 */
public interface CRaidResultTypes {

    HTCustomRegistry<ResultType<?>> TYPES = HTRegistryManager.custom(CRaidHelper.prefix("result_type"));

    ResultType<ItemStackResult> ITEM_STACK = register("item_stack", ItemStackResult.CODEC);
    ResultType<ChestResult> CHEST = register("chest", ChestResult.CODEC);
//    ResultType<EventResult> EVENT = register(new ResultTypeImpl<>("event", EventResult.CODEC));
    ResultType<FunctionResult> FUNCTION = register("function", FunctionResult.CODEC);
    ResultType<CommandResult> COMMAND = register("command", CommandResult.CODEC);

    static <T extends ResultComponent> ResultType<T> register(String name, MapCodec<T> codec){
        return registry().register(CRaidHelper.prefix(name), new ResultTypeImpl<>(codec));
    }

    static HTCustomRegistry<ResultType<?>> registry(){
        return TYPES;
    }

    record ResultTypeImpl<P extends ResultComponent>(MapCodec<P> codec) implements ResultType<P> {

    }

}
