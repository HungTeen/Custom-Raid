package com.hungteen.craid.common.event;

import com.hungteen.craid.CRaid;
import com.hungteen.craid.common.raid.RaidManager;

import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CRaid.MOD_ID)
public class LivingEvents {

	@SubscribeEvent
	public static void preventRaiderDespawn(LivingSpawnEvent.AllowDespawn ev) {
		if(ev.getWorld() instanceof ServerWorld && RaidManager.isRaider((ServerWorld) ev.getWorld(), ev.getEntityLiving())) {
			ev.setResult(Result.DENY);
		}
	}
	
}
