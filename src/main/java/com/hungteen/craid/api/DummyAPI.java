package com.hungteen.craid.api;

import com.hungteen.craid.api.CRaidAPI.ICustomRaidAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

/**
 * fake dummy API when there is no Custom Raid mod.
 */
public class DummyAPI implements ICustomRaidAPI {

	public static final ICustomRaidAPI INSTANCE = new DummyAPI();

	@Override
	public void registerSpawnAmount(String name, Class<? extends IAmountComponent> c) {
	}

	@Override
	public void registerSpawnPlacement(String name, Class<? extends IPlacementComponent> c) {
	}

	@Override
	public void registerRaidType(String name, Class<? extends IRaidComponent> c) {
	}

	@Override
	public void registerWaveType(String name, Class<? extends IWaveComponent> c) {
	}

	@Override
	public void registerSpawnType(String name, Class<? extends ISpawnComponent> c) {
	}

	@Override
	public void registerReward(String name, Class<? extends IRewardComponent> c) {
	}

	@Override
	public void createRaid(ServerLevel world, ResourceLocation res, BlockPos pos) {
	}

	@Override
	public boolean isRaider(ServerLevel world, Entity entity) {
		return false;
	}

}
