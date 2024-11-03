package hungteen.craid.common.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import hungteen.craid.api.raid.RaidComponent;
import hungteen.craid.common.codec.raid.CRaidRaidComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/2 18:33
 **/
public class RaidArgument {

    private static final DynamicCommandExceptionType ERROR_INVALID_RAID = new DynamicCommandExceptionType((p_304101_) -> {
        return Component.translatableEscape("command.craid.raid.invalid", new Object[]{p_304101_});
    });

    public static ResourceKeyArgument<RaidComponent> raid() {
        return ResourceKeyArgument.key(CRaidRaidComponents.registry().getRegistryKey());
    }

    public static Holder.Reference<RaidComponent> getRaid(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
        return ResourceKeyArgument.resolveKey(context, name, CRaidRaidComponents.registry().getRegistryKey(), ERROR_INVALID_RAID);
    }

}
