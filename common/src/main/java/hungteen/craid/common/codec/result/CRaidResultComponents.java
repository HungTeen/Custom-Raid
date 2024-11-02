package hungteen.craid.common.codec.result;

import com.mojang.serialization.Codec;
import hungteen.craid.api.CRaidHelper;
import hungteen.craid.api.raid.ResultComponent;
import hungteen.craid.api.raid.ResultType;
import hungteen.htlib.api.registry.HTCodecRegistry;
import hungteen.htlib.common.impl.registry.HTRegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Optional;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-04 09:47
 **/
public interface CRaidResultComponents {

    HTCodecRegistry<ResultComponent> RESULTS = HTRegistryManager.codec(CRaidHelper.prefix("result"), CRaidResultComponents::getDirectCodec);

    ResourceKey<ResultComponent> TEST = create("test");
    ResourceKey<ResultComponent> COMMON_FUNCTION = create("common_function");
    ResourceKey<ResultComponent> GIVE_APPLE_COMMAND = create("give_apple_command");

    static void register(BootstrapContext<ResultComponent> context) {
        context.register(TEST, new ItemStackResult(
                true, false,
                List.of(new ItemStack(Items.ACACIA_BOAT, 3))
        ));
        context.register(COMMON_FUNCTION, new FunctionResult(
                List.of(),
                List.of(CRaidHelper.get().prefix("test")),
                List.of()
        ));
        context.register(GIVE_APPLE_COMMAND, new CommandResult(
                Optional.empty(), Optional.of("give @s apple 1"), Optional.empty()
        ));
    }

    static Codec<ResultComponent> getDirectCodec() {
        return CRaidResultTypes.registry().byNameCodec().dispatch(ResultComponent::getType, ResultType::codec);
    }

    static Codec<Holder<ResultComponent>> getCodec() {
        return registry().getHolderCodec(getDirectCodec());
    }

    static ResourceKey<ResultComponent> create(String name) {
        return registry().createKey(CRaidHelper.prefix(name));
    }

    static HTCodecRegistry<ResultComponent> registry() {
        return RESULTS;
    }

}
