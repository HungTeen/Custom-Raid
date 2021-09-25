package com.hungteen.craid.api;

import com.hungteen.craid.api.CRaidAPI.ICustomRaidAPI;

/**
 * fake dummy API when there is no Custom Raid mod.
 */
public class DummyAPI implements ICustomRaidAPI {

	public static final ICustomRaidAPI INSTANCE = new DummyAPI();

	@Override
	public void registerSpawnAmount(String name, Class<? extends ISpawnAmount> c) {
	}
}
