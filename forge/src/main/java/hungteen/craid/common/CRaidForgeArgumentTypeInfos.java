package hungteen.craid.common;

import hungteen.craid.api.CRaidAPI;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/2 19:16
 **/
public interface CRaidForgeArgumentTypeInfos {

    DeferredRegister<ArgumentTypeInfo<?, ?>> INFOS = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, CRaidAPI.id());

}
