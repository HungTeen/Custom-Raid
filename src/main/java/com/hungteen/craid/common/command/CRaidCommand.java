package com.hungteen.craid.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;

public class CRaidCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("craid").requires(ctx -> ctx.hasPermission(2));
        builder.then(Commands.literal("add")
        		.then(Commands.argument("type", StringArgumentType.string())
        		.then(Commands.argument("pos", BlockPosArgument.blockPos())
        		.executes((command) -> {
        			return addRaid(command.getSource(), StringArgumentType.getString(command, "type"), BlockPosArgument.getLoadedBlockPos(command, "pos"));
        		}))));
        builder.then(Commands.literal("list")
        		.then(Commands.literal("all")
        		.executes((command) -> {
        			return showAllRaid(command.getSource());
        		})));
        dispatcher.register(builder);
    }
	
	public static int addRaid(CommandSource source, String type, BlockPos pos) {
		return 1;
	}
	
	public static int showAllRaid(CommandSource source) {
		return 1;
	}
	
	
	
}
