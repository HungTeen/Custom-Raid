package com.hungteen.craid;

import java.util.Random;

import net.minecraft.util.math.BlockPos;

public class Util {

	public static final String JSON_RAID_NAME = "name";
	public static final String JSON_RAID_PRE_CD = "pre_tick";
	public static final String JSON_RAID_WIN_CD = "win_tick";
	public static final String JSON_RAID_LOSS_CD = "loss_tick";
	public static final String JSON_WAVES = "waves";
	public static final String JSON_WAVE_DURATION = "duration";
	public static final String JSON_SPAWNS = "spawns";
	public static final String JSON_ENTITY_TYPE = "entity_type";
	public static final String JSON_ENTITY_NBT = "nbt";
	public static final String JSON_ENTITY_SPAWN_AMOUNT = "spawn_amount";
	public static final String JSON_ENTITY_PLACEMENT = "placement";
	public static final String JSON_ENTITY_COUNT = "count";
	public static final String JSON_ENTITY_START_TICK = "start_tick";
	
	/**
	 * gen random from min to max.
	 */
	public static int getRandomMinMax(Random rand, int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
	
	/**
	 * get random from - range to range.
	 * {@link EntityUtil#onMobEntityRandomPosSpawn(net.minecraft.world.IWorld, net.minecraft.entity.MobEntity, BlockPos, int)}
	 */
	public static int getRandomInRange(Random rand, int range) {
		return rand.nextInt(range << 1 | 1) - range;
	}
	
	public static boolean isRaidEnable() {
		return CRaidConfig.COMMON_CONFIG.GlobalSettings.EnableRaid.get();
	}
}
