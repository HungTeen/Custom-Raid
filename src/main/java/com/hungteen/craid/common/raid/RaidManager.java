package com.hungteen.craid.common.raid;

import com.hungteen.craid.common.world.RaidData;

import net.minecraft.world.World;

public class RaidManager {

	public static void tickRaids(World world) {
		if(! world.isClientSide) {
			final RaidData data = RaidData.getInvasionData(world);
			data.tick();
		}
	}
}
