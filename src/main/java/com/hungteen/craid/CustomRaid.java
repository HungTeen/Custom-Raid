package com.hungteen.craid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hungteen.craid.common.command.CRaidCommand;
import com.hungteen.craid.common.raid.RaidManager;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

@Mod("craid")
public class CustomRaid
{
	
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "craid";
    
    public CustomRaid() {
    	IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    	forgeBus.addListener(EventPriority.NORMAL, CustomRaid::addReloadListenerEvent);
    	forgeBus.addListener(EventPriority.NORMAL, CustomRaid::registerCommonds);
    }
    
    public static void registerCommonds(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        CRaidCommand.register(dispatcher);
    }
    
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
		event.addListener(new RaidManager());
	}


}
