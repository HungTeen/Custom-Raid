package com.hungteen.craid.common.impl.amount;

import com.google.gson.JsonElement;
import com.hungteen.craid.api.IAmountComponent;
import net.minecraft.util.GsonHelper;

public class ConstantAmount implements IAmountComponent {

	public static final String NAME = "count";
	private int cnt;

	public ConstantAmount() {
	}

	@Override
	public int getSpawnAmount() {
		return this.cnt;
	}

	@Override
	public void readJson(JsonElement json) {
		this.cnt = GsonHelper.convertToInt(json, NAME);
	}

}
