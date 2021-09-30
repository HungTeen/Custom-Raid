package com.hungteen.craid.common.raid;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.hungteen.craid.CRaidUtil;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

public class WorldRaidData extends WorldSavedData {

	private static final String DATA_NAME = "CustomRaidData";
	private final Map<Integer, Raid> raidMap = Maps.newHashMap();
	private final ServerWorld world;
	private int currentRaidId = 1;
	private int tick = 0;

	public WorldRaidData(ServerWorld world) {
		super(DATA_NAME);
		this.world = world;
	}

	/**
	 * tick all raid in running.
	 * {@link RaidManager#tickRaids(World)}
	 */
	public void tick() {
		Iterator<Raid> iterator = this.raidMap.values().iterator();
		while (iterator.hasNext()) {
			Raid raid = iterator.next();
			if (! CRaidUtil.isRaidEnable()) {
				raid.remove();
			}
			if (raid.isRemoving()) {
				iterator.remove();
				this.setDirty();
			} else {
				this.world.getProfiler().push("Custom Raid Tick");
				raid.tick();
				this.world.getProfiler().pop();
			}
		}

		if (++ this.tick % 200 == 0) {
			this.setDirty();
		}
	}
	
	public Raid createRaid(ServerWorld world, ResourceLocation res, BlockPos pos) {
		final int id = this.getUniqueId();
		final Raid raid = new Raid(id, world, res, pos);
		this.addRaid(id, raid);
		return raid;
	}
	
	public void addRaid(int id, Raid raid) {
		this.raidMap.put(id, raid);
		this.setDirty();
	}

	public int getUniqueId() {
		this.setDirty();
		return this.currentRaidId ++;
	}

	public List<Raid> getRaids() {
		return this.raidMap.values().stream().collect(Collectors.toList());
	}

	@Override
	public void load(CompoundNBT nbt) {
		if (nbt.contains("current_id")) {
			this.currentRaidId = nbt.getInt("current_id");
		}
		final ListNBT raidList = nbt.getList("custom_raids", 10);
		for (int i = 0; i < raidList.size(); ++i) {
			final CompoundNBT tmp = raidList.getCompound(i);
			final Raid raid = new Raid(world, tmp);
			this.raidMap.put(raid.getId(), raid);
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt.putInt("current_id", this.currentRaidId);

		final ListNBT raidList = new ListNBT();
		for (Raid raid : this.raidMap.values()) {
			final CompoundNBT tmp = new CompoundNBT();
			raid.save(tmp);
			raidList.add(tmp);
		}
		nbt.put("custom_raids", raidList);

		return nbt;
	}

	/*
	 * there is no world has invasion except overworld.
	 */
	public static WorldRaidData getInvasionData(World worldIn) {
		if (!(worldIn instanceof ServerWorld)) {
			throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
		}
		return ((ServerWorld) worldIn).getDataStorage().computeIfAbsent(() -> new WorldRaidData((ServerWorld) worldIn),
				DATA_NAME);
	}

}
