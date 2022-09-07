package com.hungteen.craid.common.impl;

import com.hungteen.craid.api.CRaidAPI.ICustomRaidAPI;
import com.hungteen.craid.api.*;
import com.hungteen.craid.common.raid.RaidManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

/**
 * correct real API.
 */
public class CRaidAPIImpl implements ICustomRaidAPI{

	@Override
	public void registerSpawnAmount(String name, Class<? extends IAmountComponent> c) {
		RaidManager.registerSpawnAmount(name, c);
	}

	@Override
	public void registerSpawnPlacement(String name, Class<? extends IPlacementComponent> c) {
		RaidManager.registerSpawnPlacement(name, c);
	}

	@Override
	public void registerReward(String name, Class<? extends IRewardComponent> c) {
		RaidManager.registerReward(name, c);
	}

	@Override
	public void registerRaidType(String name, Class<? extends IRaidComponent> c) {
		RaidManager.registerRaidType(name, c);
	}

	@Override
	public void registerWaveType(String name, Class<? extends IWaveComponent> c) {
		RaidManager.registerWaveType(name, c);
	}

	@Override
	public void registerSpawnType(String name, Class<? extends ISpawnComponent> c) {
		RaidManager.registerSpawnType(name, c);
	}

	@Override
	public void createRaid(ServerLevel world, ResourceLocation res, BlockPos pos) {
		if(! RaidManager.hasRaidNearby(world, pos)) {
			RaidManager.createRaid(world, res, pos);
		}
	}

	@Override
	public boolean isRaider(ServerLevel world, Entity entity) {
		return RaidManager.isRaider(world, entity);
	}

}
