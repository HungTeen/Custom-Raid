package com.hungteen.craid.common.event;

import com.hungteen.craid.CustomRaid;
import com.hungteen.craid.common.raid.RaidManager;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CustomRaid.MOD_ID)
public class WorldEvents {

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent ev) {
		if (ev.phase == TickEvent.Phase.END) {
			RaidManager.tickRaids(ev.world);
		}
	}
	
}
