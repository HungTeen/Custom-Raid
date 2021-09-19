package com.hungteen.craid.common.raid;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class AbstractRaid {

	private final int id;
	protected final ServerWorld world;
	protected BlockPos center;
	protected Status status;
	
	public AbstractRaid(int id, ServerWorld world, BlockPos pos) {
		this.id = id;
		this.world = world;
		this.center = pos;
		this.status = Status.RUNING;
	}
	
	public AbstractRaid(ServerWorld world, CompoundNBT nbt) {
		this.world = world;
		this.id = nbt.getInt("raid_id");
		this.status = Status.values()[nbt.getInt("raid_status")];
		{
			CompoundNBT tmp = nbt.getCompound("center_pos");
			this.center = new BlockPos(tmp.getInt("pos_x"), tmp.getInt("pos_y"), tmp.getInt("pos_z"));
		}
		
	}
	
	public void save(CompoundNBT nbt) {
		nbt.putInt("raid_id", this.id);
		nbt.putInt("raid_status", this.status.ordinal());
		{
			CompoundNBT tmp = new CompoundNBT();
			nbt.putInt("pos_x", this.center.getX());
			nbt.putInt("pos_y", this.center.getY());
			nbt.putInt("pos_z", this.center.getZ());
			nbt.put("center_pos", tmp);
		}
	}
	
	public void start() {
		
	}
	
	public void tick() {
		
	}
	
	public void remove() {
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public BlockPos getCenter() {
		return this.center;
	}
	
	public boolean isRunning() {
		return this.status == Status.RUNING;
	}
	
	public boolean isStopped() {
		return this.status == Status.STOPPED;
	}
	
	private static enum Status {
	      RUNING,
	      VICTORY,
	      LOSS,
	      STOPPED;
	}
	
}
