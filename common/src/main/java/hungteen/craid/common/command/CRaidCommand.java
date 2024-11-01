package hungteen.craid.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import hungteen.craid.common.entity.SeatEntity;
import hungteen.craid.common.codec.raid.HTLibRaidComponents;
import hungteen.craid.common.world.entity.DummyEntity;
import hungteen.craid.common.world.entity.DummyEntityManager;
import hungteen.craid.common.world.entity.HTLibDummyEntities;
import hungteen.craid.util.helper.CodecHelper;
import hungteen.craid.util.helper.CommandHelper;
import hungteen.craid.util.helper.impl.HTLibHelper;
import hungteen.craid.util.helper.MathHelper;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-01 12:51
 **/
public class CRaidCommand {

    private static final DynamicCommandExceptionType ERROR_INVALID_FEATURE = new DynamicCommandExceptionType((msg) -> {
        return Component.translatable("commands.place.feature.invalid", msg);
    });
    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(Component.translatable("commands.summon.failed"));
    private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(Component.translatable("commands.summon.invalidPosition"));
    private static final SuggestionProvider<CommandSourceStack> ALL_CUSTOM_RAIDS = SuggestionProviders.register(HTLibHelper.prefix("all_custom_raids"), (commandContext, builder) -> {
//        Optional<HolderLookup.RegistryLookup<IRaidComponent>> raids = commandContext.getSource().registryAccess().lookup(HTRaidComponents.registry().getRegistryKey());
//        if(raids.isPresent()){
//            return SharedSuggestionProvider.suggestResource(raids.getCodecRegistry().listElementIds().map(ResourceKey::location), builder);
//        }
        return SharedSuggestionProvider.suggestResource(HTLibRaidComponents.registry().getCachedKeys(), builder);
    });
    private static final SuggestionProvider<CommandSourceStack> ALL_DUMMY_ENTITIES = SuggestionProviders.register(HTLibHelper.prefix("all_dummy_entities"), (commandContext, builder) -> {
        return SharedSuggestionProvider.suggestResource(HTLibDummyEntities.getIds(), builder);
    });

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("craid").requires((ctx) -> ctx.hasPermission(2));
        builder.then(Commands.literal("raid")
                .then(Commands.literal("codec")
                                .then(Commands.argument("type", ResourceLocationArgument.id())
                                        .suggests(ALL_CUSTOM_RAIDS)
                                        .then(Commands.argument("position", Vec3Argument.vec3())
                                                .executes(context -> createRaid(context.getSource(), ResourceLocationArgument.getId(context, "type"), Vec3Argument.getVec3(context, "position")))
                                        )
                                )
                        )
                )
        );
        dispatcher.register(builder);
    }

//    private static Holder<RaidComponent> getRaid(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
//        return CommandHelper.getHolder(context, HTLibRaidComponents.registry().getRegistryKey(), name, ERROR_INVALID_FEATURE);
//    }
//
//    public static int createRaid(CommandSourceStack sourceStack, ResourceLocation raidId, Vec3 position) throws CommandSyntaxException {
//        final CompoundTag nbt = new CompoundTag();
//        RaidComponent raidComponent = HTLibRaidComponents.registry().getValue(sourceStack.getLevel(), HTLibRaidComponents.registry().createKey(raidId));
//        CodecHelper.encodeNbt(HTLibRaidComponents.getDirectCodec(), raidComponent)
//                .result().ifPresent(tag -> {
//                    nbt.put(AbstractRaid.RAID_TAG, tag);
//                });
//        return createDummyEntity(sourceStack, dummyType, position, nbt);
//    }


}
