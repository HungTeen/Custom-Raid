package com.hungteen.craid.common.raid;

import com.google.common.collect.Maps;
import com.hungteen.craid.CRaidUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldRaidData extends SavedData {

	private static final String DATA_NAME = "CustomRaidData";
	private final Map<Integer, Raid> raidMap = Maps.newHashMap();
	private int currentRaidId = 1;
	private int tick = 0;

	public WorldRaidData() {
		super();
		this.setDirty();
	}

	public WorldRaidData(ServerLevel world, CompoundTag nbt) {
		if (nbt.contains("current_id")) {
			this.currentRaidId = nbt.getInt("current_id");
		}
		final ListTag raidList = nbt.getList("custom_raids", 10);
		for (int i = 0; i < raidList.size(); ++i) {
			final CompoundTag tmp = raidList.getCompound(i);
			final Raid raid = new Raid(world, tmp);
			this.raidMap.put(raid.getId(), raid);
		}
	}

	/**
	 * tick all raid in running.
	 * {@link RaidManager}
	 */
	public void tick(Level world) {
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
				world.getProfiler().push("Custom Raid Tick");
				raid.tick();
				world.getProfiler().pop();
			}
		}

		if (++ this.tick % 200 == 0) {
			this.setDirty();
		}
	}

	public Raid createRaid(ServerLevel world, ResourceLocation res, BlockPos pos) {
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
		return new ArrayList<>(this.raidMap.values());
	}




	@Nonnull
	@Override
	public CompoundTag save(CompoundTag nbt) {
		nbt.putInt("current_id", this.currentRaidId);

		final ListTag raidList = new ListTag();
		for (Raid raid : this.raidMap.values()) {
			final CompoundTag tmp = new CompoundTag();
			raid.save(tmp);
			raidList.add(tmp);
		}
		nbt.put("custom_raids", raidList);

		return nbt;
	}

	/*
	 * there is no world has invasion except overworld.
	 */
	public static WorldRaidData getInvasionData(Level worldIn) {
		if (!(worldIn instanceof ServerLevel)) {
			throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
		}
		return ((ServerLevel) worldIn).getDataStorage().computeIfAbsent((tag) -> new WorldRaidData((ServerLevel) worldIn, tag), WorldRaidData::new,
				DATA_NAME);
	}

}
