package com.hungteen.craid;

public class Util {

	public static final String JSON_RAID_NAME = "name";
	public static final String JSON_WAVES = "waves";
	public static final String JSON_SPAWNS = "spawns";
	public static final String JSON_ENTITY_TYPE = "entity_type";
	public static final String JSON_ENTITY_SPAWN_COUNT = "count";
	
	public static boolean isRaidEnable() {
		return CRaidConfig.COMMON_CONFIG.GlobalSettings.EnableRaid.get();
	}
}
