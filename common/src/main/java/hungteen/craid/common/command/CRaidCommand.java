package hungteen.craid.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import hungteen.craid.api.CRaidHelper;
import hungteen.craid.api.raid.RaidComponent;
import hungteen.craid.common.CRaidDummyEntities;
import hungteen.craid.common.codec.raid.CRaidRaidComponents;
import hungteen.craid.common.world.raid.HTRaidImpl;
import hungteen.htlib.common.world.entity.DummyEntity;
import hungteen.htlib.common.world.entity.DummyEntityManager;
import hungteen.htlib.common.world.entity.DummyEntityType;
import hungteen.htlib.common.world.entity.HTLibDummyEntities;
import hungteen.htlib.util.helper.CodecHelper;
import hungteen.htlib.util.helper.MathHelper;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-01 12:51
 **/
public class CRaidCommand {

    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(Component.translatable("command.craid.create.failed"));
    private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(Component.translatable("commands.summon.invalidPosition"));
    private static final SuggestionProvider<CommandSourceStack> ALL_CUSTOM_RAIDS = SuggestionProviders.register(CRaidHelper.prefix("all_custom_raids"), (commandContext, builder) -> {
        return SharedSuggestionProvider.suggestResource(CRaidRaidComponents.registry().getCachedKeys(), builder);
    });
    private static final SuggestionProvider<CommandSourceStack> ALL_DUMMY_ENTITIES = SuggestionProviders.register(CRaidHelper.prefix("all_dummy_entities"), (commandContext, builder) -> {
        return SharedSuggestionProvider.suggestResource(HTLibDummyEntities.getIds(), builder);
    });

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("craid").requires((ctx) -> ctx.hasPermission(2));
        builder.then(Commands.literal("raid")
                        .then(Commands.literal("create")
                                        .then(Commands.argument("type", RaidArgument.raid())
//                                        .suggests(ALL_CUSTOM_RAIDS)
                                                        .then(Commands.argument("position", Vec3Argument.vec3())
                                                                .executes(context -> createRaid(context.getSource(), RaidArgument.getRaid(context, "type"), Vec3Argument.getVec3(context, "position")))
                                                        )
                                        )
                        )
        );
        dispatcher.register(builder);
    }

    public static int createRaid(CommandSourceStack sourceStack, Holder<RaidComponent> raidComponent, Vec3 position) throws CommandSyntaxException {
        final CompoundTag nbt = new CompoundTag();
        CodecHelper.encodeNbt(CRaidRaidComponents.getDirectCodec(), raidComponent.value())
                .result().ifPresent(tag -> {
                    nbt.put(HTRaidImpl.RAID_TAG, tag);
                });
        return createDummyEntity(sourceStack, CRaidDummyEntities.DEFAULT_RAID, position, nbt);
    }

    public static int createDummyEntity(CommandSourceStack sourceStack, DummyEntityType<?> type, Vec3 position, CompoundTag tag) throws CommandSyntaxException {
        final BlockPos blockpos = MathHelper.toBlockPos(position);
        if (!Level.isInSpawnableBounds(blockpos)) {
            throw INVALID_POSITION.create();
        }
        final DummyEntity dummyEntity = DummyEntityManager.createDummyEntity(sourceStack.getLevel(), type.getLocation(), position, tag);
        if (dummyEntity != null) {
            sourceStack.sendSuccess(() -> Component.translatable("command.craid.create.success", dummyEntity.getEntityType().getRegistryName()), true);
            return 1;
        }
        throw ERROR_FAILED.create();
    }

}
