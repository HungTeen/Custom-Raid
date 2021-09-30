package com.hungteen.craid;


import net.minecraftforge.common.ForgeConfigSpec;

public class CRaidConfig {

	public static Common COMMON_CONFIG;
	public static Client CLIENT_CONFIG;
	
	public static class Common{
		
		public Common(ForgeConfigSpec.Builder builder) {
			/* Raid Settings */
			builder.comment("Settings about raid.").push("Raid Settings");
			{
				GlobalSettings.EnableRaid = builder
						.comment("Turn to false, no new raid will happen, and old raids will be clear.")
						.translation("config.craid.enable_raid")
						.define("EnableRaid", true);
				
				GlobalSettings.RaidWaitTime = builder
						.comment("how many ticks will raid wait, when there is no player in it.")
						.translation("config.craid.raid_wait_time")
						.defineInRange("RaidWaitTime", 400, 1, 1000000);
				
				GlobalSettings.RaidRange = builder
						.comment("how far will a player join the raid.")
						.translation("config.craid.raid_range")
						.defineInRange("RaidRange", 50, 1, 1000);
			}
		}
		
		public GlobalSettings GlobalSettings = new GlobalSettings();
		
		public static class GlobalSettings{
			public ForgeConfigSpec.BooleanValue EnableRaid;
			
			public ForgeConfigSpec.IntValue RaidWaitTime;
			
			public ForgeConfigSpec.IntValue RaidRange;
		}		    
		
	}
	
	public static class Client{
		
		public Client(ForgeConfigSpec.Builder builder) {
//			builder.comment("Player Resource Bar Settings").push("Resource Render Settings");
//			{
//				ResourceRender.RenderSunNumBar = builder
//						.comment("Should Render SunNumBar")
//						.define("RenderSunNumBar", true);
//			}
//			builder.pop();
		}
		
//		public OtherSettings OtherSettings = new OtherSettings();
//		
//		public static class ResourceRender{
//			public ForgeConfigSpec.BooleanValue RenderGemBar;
//		}
		
	}
}
