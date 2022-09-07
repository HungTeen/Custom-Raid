package com.hungteen.craid.common.impl.placement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hungteen.craid.CRaidUtil;
import com.hungteen.craid.api.IPlacementComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class OuterPlacement implements IPlacementComponent {

	public static final String NAME = "outer";
	private boolean onSurface;
	private int min;
	private int max;

	@Override
	public BlockPos getPlacePosition(Level world, BlockPos origin) {
		this.max = Math.max(this.max, this.min);//prevent wrong json by others.
		final int dx = (world.getRandom().nextDouble() < 0.5 ? 1 : -1) * CRaidUtil.getRandomMinMax(world.getRandom(), this.min, this.max);
		final int dz = (world.getRandom().nextDouble() < 0.5 ? 1 : -1) * CRaidUtil.getRandomMinMax(world.getRandom(), this.min, this.max);
		final int height = this.onSurface ? world.getHeight(Heightmap.Types.WORLD_SURFACE, origin.getX() + dx, origin.getZ() + dz) : origin.getY();
		return new BlockPos(origin.getX() + dx, height, origin.getZ() + dz);
	}

	@Override
	public void readJson(JsonElement json) {
		JsonObject obj = json.getAsJsonObject();
		if(obj != null) {
			this.min = GsonHelper.getAsInt(obj, "min");
			this.max = GsonHelper.getAsInt(obj, "max");
			this.onSurface = GsonHelper.getAsBoolean(obj, "ground", true);
		}
	}

}
