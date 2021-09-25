package com.hungteen.craid.common.impl;

import com.hungteen.craid.api.CRaidAPI.ICustomRaidAPI;
import com.hungteen.craid.api.ISpawnAmount;
import com.hungteen.craid.common.raid.RaidManager;

/**
 * correct real API.
 */
public class CRaidAPIImpl implements ICustomRaidAPI{

	@Override
	public void registerSpawnAmount(String name, Class<? extends ISpawnAmount> c) {
		RaidManager.registerSpawnAmount(name, c);
	}

}
