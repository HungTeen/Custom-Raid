package com.hungteen.craid.common.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AdvancementHandler {

	public static void init() {
		CriteriaTriggers.register(RaidTrigger.INSTANCE);
	}
	
}
