package com.hungteen.craid.common.impl;

import com.hungteen.craid.api.CRaidAPI.ICustomRaidAPI;
import com.hungteen.craid.api.IAmountComponent;
import com.hungteen.craid.api.IPlacementComponent;
import com.hungteen.craid.common.raid.RaidManager;

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

}
