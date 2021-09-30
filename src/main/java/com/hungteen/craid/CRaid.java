package com.hungteen.craid;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hungteen.craid.api.CRaidAPI;
import com.hungteen.craid.api.CRaidAPI.ICustomRaidAPI;
import com.hungteen.craid.common.advancement.AdvancementHandler;
import com.hungteen.craid.common.command.CRaidCommand;
import com.hungteen.craid.common.impl.RaidComponent;
import com.hungteen.craid.common.impl.SpawnComponent;
import com.hungteen.craid.common.impl.WaveComponent;
import com.hungteen.craid.common.impl.amount.ConstantAmount;
import com.hungteen.craid.common.impl.amount.RandomAmount;
import com.hungteen.craid.common.impl.placement.CenterPlacement;
import com.hungteen.craid.common.impl.placement.OffsetPlacement;
import com.hungteen.craid.common.impl.placement.OuterPlacement;
import com.hungteen.craid.common.impl.reward.AdvancementRewardComponent;
import com.hungteen.craid.common.network.PacketHandler;
import com.hungteen.craid.common.raid.RaidLoader;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("craid")
public class CRaid
{
	
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "craid";
    
    public CRaid() {
    	{
    		final Pair<CRaidConfig.Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CRaidConfig.Common::new);
    		ModLoadingContext.get().registerConfig(Type.COMMON, specPair.getRight());
    		CRaidConfig.COMMON_CONFIG = specPair.getLeft();
    	}
    	{
    		final Pair<CRaidConfig.Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CRaidConfig.Client::new);
    		ModLoadingContext.get().registerConfig(Type.CLIENT, specPair.getRight());
    		CRaidConfig.CLIENT_CONFIG = specPair.getLeft();
    	}
    	IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    	modBus.addListener(EventPriority.NORMAL, PacketHandler::init);
    	
    	IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    	forgeBus.addListener(EventPriority.NORMAL, CRaid::addReloadListenerEvent);
    	forgeBus.addListener(EventPriority.NORMAL, CRaid::registerCommonds);
    	
    	AdvancementHandler.init();
    	
    	registerMisc();
    }
    
    public static void registerMisc() {
    	final ICustomRaidAPI api = CRaidAPI.get();
    	
    	api.registerRaidType(RaidComponent.NAME, RaidComponent.class);
    	
    	api.registerWaveType(WaveComponent.NAME, WaveComponent.class);
    	
    	api.registerSpawnType(SpawnComponent.NAME, SpawnComponent.class);
    	
    	api.registerSpawnAmount(ConstantAmount.NAME, ConstantAmount.class);
    	api.registerSpawnAmount(RandomAmount.NAME, RandomAmount.class);
    	
    	api.registerSpawnPlacement(CenterPlacement.NAME, CenterPlacement.class);
    	api.registerSpawnPlacement(OuterPlacement.NAME, OuterPlacement.class);
    	api.registerSpawnPlacement(OffsetPlacement.NAME, OffsetPlacement.class);
    	
    	api.registerReward(AdvancementRewardComponent.NAME, AdvancementRewardComponent.class);
    }
    
    public static void registerCommonds(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        CRaidCommand.register(dispatcher);
    }
    
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
		event.addListener(new RaidLoader());
	}


}
