package com.hungteen.craid.common.impl.amount;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hungteen.craid.api.IAmountComponent;
import net.minecraft.util.GsonHelper;

import java.util.Random;

public class RandomAmount implements IAmountComponent {

	public static final String NAME = "random";
	private final Random rand = new Random();
	private int min = 1;
	private int max = 1;

	public RandomAmount() {
	}

	@Override
	public int getSpawnAmount() {
		this.max = Math.max(this.max, this.min);//prevent wrong json by others.
		return Math.max(0, this.rand.nextInt(this.max - this.min + 1) + this.min);
	}

	@Override
	public void readJson(JsonElement json) {
		JsonObject obj = json.getAsJsonObject();
		if(obj != null) {
			this.min = GsonHelper.getAsInt(obj, "min");
			this.max = GsonHelper.getAsInt(obj, "max");
		}
	}

}
