package com.hungteen.craid.api;

import com.google.gson.JsonElement;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public interface IRewardComponent {

	void reward(ServerPlayer player);

	void rewardGlobally(Level world);

	/**
	 * make sure constructer has no argument,
	 * and use this method to initiate instance.
	 */
	void readJson(JsonElement json);
}
