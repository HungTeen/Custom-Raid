package com.hungteen.craid.common.raid;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class AbstractRaid {

	private final int id;
	protected final ServerWorld world;
	protected BlockPos center;
	
	
	public AbstractRaid(int id, ServerWorld world, BlockPos pos) {
		this.id = id;
		this.world = world;
		this.center = pos;
	}
	
	public void tick() {
		
	}
	
}
