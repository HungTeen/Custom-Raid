package com.hungteen.craid.common.world;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.hungteen.craid.Util;
import com.hungteen.craid.common.raid.AbstractRaid;
import com.hungteen.craid.common.raid.RaidManager;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

public class RaidData extends WorldSavedData {

	private static final String DATA_NAME = "CustomRaidData";
	private final Map<Integer, AbstractRaid> raidMap = Maps.newHashMap();
	private final ServerWorld world;
	private int currentRaidId = 1;
	private int tick = 0;

	public RaidData(ServerWorld world) {
		super(DATA_NAME);
		this.world = world;
	}

	/**
	 * tick all raid in running.
	 * {@link RaidManager#tickRaids(World)}
	 */
	public void tick() {
		Iterator<AbstractRaid> iterator = this.raidMap.values().iterator();
		while (iterator.hasNext()) {
			AbstractRaid raid = iterator.next();
			if (! Util.isRaidEnable()) {
				raid.remove();;
			}
			if (raid.isStopped()) {
				iterator.remove();
				this.setDirty();
			} else {
				raid.tick();
			}
		}

		if (++ this.tick % 200 == 0) {
			this.setDirty();
		}
	}

	public int getUniqueId() {
		this.setDirty();
		return this.currentRaidId++;
	}

	public List<AbstractRaid> getRaids() {
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
			final AbstractRaid raid = new AbstractRaid(world, tmp);
			this.raidMap.put(raid.getId(), raid);
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		nbt.putInt("current_id", this.currentRaidId);

		final ListNBT raidList = new ListNBT();
		for (AbstractRaid raid : this.raidMap.values()) {
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
	public static RaidData getInvasionData(World worldIn) {
		if (!(worldIn instanceof ServerWorld)) {
			throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
		}
		return ((ServerWorld) worldIn).getDataStorage().computeIfAbsent(() -> new RaidData((ServerWorld) worldIn),
				DATA_NAME);
	}

}
