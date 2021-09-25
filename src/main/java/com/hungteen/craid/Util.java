package com.hungteen.craid;

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
	public static final String JSON_ENTITY_COUNT = "count";
	public static final String JSON_ENTITY_START_TICK = "start_tick";
	
	public static boolean isRaidEnable() {
		return CRaidConfig.COMMON_CONFIG.GlobalSettings.EnableRaid.get();
	}
}
