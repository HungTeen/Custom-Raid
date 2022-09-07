package com.hungteen.craid.common.command;

import com.hungteen.craid.CRaidUtil;
import com.hungteen.craid.common.raid.RaidManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class CRaidCommand {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("craid").requires(ctx -> ctx.hasPermission(2));
		builder.then(Commands.literal("add").then(Commands.argument("type", ResourceLocationArgument.id())
				.then(Commands.argument("pos", BlockPosArgument.blockPos()).executes((command) -> {
					return addRaid(command.getSource(), getResourceLocation(command, "type"),
							BlockPosArgument.getLoadedBlockPos(command, "pos"));
				}))));
		builder.then(Commands.literal("remove").then(Commands.literal("nearby")
				.then(Commands.argument("pos", BlockPosArgument.blockPos()).executes((command) -> {
					return removeNearby(command.getSource(), BlockPosArgument.getLoadedBlockPos(command, "pos"));
				}))).then(Commands.literal("all").executes((command) -> {
							return removeAll(command.getSource());
				})));
		builder.then(Commands.literal("list").then(Commands.argument("targets", EntityArgument.players()).executes((command) -> {
			return showAllRaid(command.getSource(), EntityArgument.getPlayers(command, "targets"));
		})));
		dispatcher.register(builder);
	}

	private static int addRaid(CommandSourceStack source, ResourceLocation res, BlockPos pos) {
		if(RaidManager.getRaidComponent(res) != null) {
			if(! RaidManager.hasRaidNearby(source.getLevel(), pos)) {
				RaidManager.createRaid(source.getLevel(), res, pos);
			} else {
				source.sendFailure(new TranslatableComponent("info.craid.has_raid"));
			}
		} else {
			source.sendFailure(new TranslatableComponent("info.craid.search_fail"));
		}
		return 1;
	}

	private static int removeNearby(CommandSourceStack source, BlockPos pos) {
		RaidManager.getRaids(source.getLevel()).forEach(raid -> {
			if(raid.getCenter().closerThan(pos, CRaidUtil.getRaidRange())) {
				raid.remove();
			}
		});
		return 1;
	}

	private static int removeAll(CommandSourceStack  source) {
		RaidManager.getRaids(source.getLevel()).forEach(raid -> {
			raid.remove();
		});
		return 1;
	}

	private static int showAllRaid(CommandSourceStack source, Collection<? extends ServerPlayer> targets) {
		RaidManager.getRaids(source.getLevel()).forEach(raid -> {
			targets.forEach(p -> CRaidUtil.sendMsgTo(p, new TextComponent(raid.getCenter().toString())));
		});
		return 1;
	}

	private static ResourceLocation getResourceLocation(CommandContext<CommandSourceStack> source, String string) {
		return source.getArgument(string, ResourceLocation.class);
	}

}
