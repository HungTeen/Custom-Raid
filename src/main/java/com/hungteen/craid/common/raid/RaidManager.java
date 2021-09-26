package com.hungteen.craid.common.raid;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.hungteen.craid.CRaid;
import com.hungteen.craid.api.IAmountComponent;
import com.hungteen.craid.api.IPlacementComponent;
import com.hungteen.craid.common.impl.CRaidAPIImpl;
import com.hungteen.craid.common.world.WorldRaidData;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RaidManager {

	public static final Map<ResourceLocation, RaidComponent> RAID_MAP = Maps.newHashMap();
	public static final Map<String, Class<? extends IAmountComponent>> AMOUNT_MAP = Maps.newHashMap();
	public static final Map<String, Class<? extends IPlacementComponent>> PLACEMENT_MAP = Maps.newHashMap();
	
	public static void tickRaids(World world) {
		if(! world.isClientSide) {
			final WorldRaidData data = WorldRaidData.getInvasionData(world);
			data.tick();
		}
	}
	
	public static void createRaid(ServerWorld world, ResourceLocation res, BlockPos pos) {
		final WorldRaidData data = WorldRaidData.getInvasionData(world);
		data.createRaid(world, res, pos);
	}
	
	public static boolean isRaider(ServerWorld world, Entity entity) {
		final WorldRaidData data = WorldRaidData.getInvasionData(world);
		for(Raid raid : data.getRaids()) {
			if(raid.isRaider(entity)) {
				return true;
			}
		}
		return false;
	}
	
	@Nullable
	public static RaidComponent getRaidContent(ResourceLocation res) {
		return RAID_MAP.getOrDefault(res, null);
	}
	
	/**
	 * {@link CRaidAPIImpl#registerSpawnAmount(String, Class)}
	 */
	public static void registerSpawnAmount(String name, Class<? extends IAmountComponent> c) {
		if(AMOUNT_MAP.containsKey(name)) {
			CRaid.LOGGER.warn("Register Spawn Amount : duplicate name, overwrited.");
		}
		AMOUNT_MAP.put(name, c);
	}
	
	@Nullable
	public static IAmountComponent getSpawnAmount(String name) {
		if(! AMOUNT_MAP.containsKey(name)) {
			CRaid.LOGGER.warn("Spawn Amount Missing : can not find {}", name);
			return null;
		}
		try {
			return AMOUNT_MAP.get(name).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * {@link CRaidAPIImpl#registerSpawnPlacement(String, Class)}
	 */
	public static void registerSpawnPlacement(String name, Class<? extends IPlacementComponent> c) {
		if(PLACEMENT_MAP.containsKey(name)) {
			CRaid.LOGGER.warn("Register Spawn Placement : duplicate name, overwrited.");
		}
		PLACEMENT_MAP.put(name, c);
	}
	
	@Nullable
	public static IPlacementComponent getSpawnPlacement(String name) {
		if(! PLACEMENT_MAP.containsKey(name)) {
			CRaid.LOGGER.warn("Spawn Placement Missing : can not find {}", name);
			return null;
		}
		try {
			return PLACEMENT_MAP.get(name).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
