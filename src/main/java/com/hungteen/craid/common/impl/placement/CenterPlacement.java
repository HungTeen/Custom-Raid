package com.hungteen.craid.common.impl.placement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hungteen.craid.Util;
import com.hungteen.craid.api.IPlacementComponent;

import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

public class CenterPlacement implements IPlacementComponent {
	
	public static final String NAME = "center";
	private boolean onSurface;
	private int radius;
	
	@Override
	public BlockPos getPlacePosition(World world, BlockPos origin) {
		final int dx = Util.getRandomInRange(world.getRandom(), this.radius);
		final int dz = Util.getRandomInRange(world.getRandom(), this.radius);
		final int height = this.onSurface ? world.getHeight(Heightmap.Type.WORLD_SURFACE, origin.getX() + dx, origin.getZ() + dz) : origin.getY();
		return new BlockPos(origin.getX() + dx, height, origin.getZ() + dz);
	}
	
	@Override
	public void readJson(JsonElement json) {
		JsonObject obj = json.getAsJsonObject();
		if(obj != null) {
			this.radius = JSONUtils.getAsInt(obj, "radius", 1);
			this.onSurface = JSONUtils.getAsBoolean(obj, "ground", true);
		}
	}

}
