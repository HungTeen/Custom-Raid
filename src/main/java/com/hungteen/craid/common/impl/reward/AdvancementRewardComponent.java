package com.hungteen.craid.common.impl.reward;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hungteen.craid.api.IRewardComponent;

import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;

public class AdvancementRewardComponent implements IRewardComponent {

	public static final String NAME = "advancements";
	private AdvancementRewards reward = AdvancementRewards.EMPTY;
	
	@Override
	public void reward(ServerPlayerEntity player) {
		this.reward.grant(player);
	}

	@Override
	public void rewardGlobally(World world) {
	}

	@Override
	public void readJson(JsonElement json) {
		final JsonObject obj = json.getAsJsonObject();
		if(obj != null) {
			this.reward = AdvancementRewards.deserialize(obj);
		}
	}

}
