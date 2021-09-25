package com.hungteen.craid.common.command;

import com.hungteen.craid.common.raid.RaidManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class CRaidCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal("craid").requires(ctx -> ctx.hasPermission(2));
		builder.then(Commands.literal("add").then(Commands.argument("type", ResourceLocationArgument.id())
				.then(Commands.argument("pos", BlockPosArgument.blockPos()).executes((command) -> {
					return addRaid(command.getSource(), getResourceLocation(command, "type"),
							BlockPosArgument.getLoadedBlockPos(command, "pos"));
				}))));
		builder.then(Commands.literal("list").then(Commands.literal("all").executes((command) -> {
			return showAllRaid(command.getSource());
		})));
		dispatcher.register(builder);
	}

	public static int addRaid(CommandSource source, ResourceLocation res, BlockPos pos) {
		if(RaidManager.getRaidContent(res) != null) {
			RaidManager.createRaid(source.getLevel(), res, pos);
		} else {
			source.sendFailure(new StringTextComponent("fail"));
		}
		return 1;
	}

	public static int showAllRaid(CommandSource source) {
		return 1;
	}
	
	private static ResourceLocation getResourceLocation(CommandContext<CommandSource> source, String string) {
		return source.getArgument(string, ResourceLocation.class);
	}

}
