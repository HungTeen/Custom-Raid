package com.hungteen.craid.common.impl.amount;

import com.google.gson.JsonElement;
import com.hungteen.craid.api.ISpawnAmount;

import net.minecraft.util.JSONUtils;

public class ConstantSpawn implements ISpawnAmount {
	
	public static final String NAME = "count";
	private int cnt;
	
	public ConstantSpawn() {
	}
	
	@Override
	public int getSpawnAmount() {
		return this.cnt;
	}

	@Override
	public void readJson(JsonElement json) {
		this.cnt = JSONUtils.convertToInt(json, NAME);
	}

}
