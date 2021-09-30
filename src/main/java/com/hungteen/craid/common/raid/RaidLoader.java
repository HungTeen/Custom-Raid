package com.hungteen.craid.common.raid;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hungteen.craid.CRaid;
import com.hungteen.craid.api.IRaidComponent;
import com.hungteen.craid.api.StringUtil;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class RaidLoader extends JsonReloadListener{

	public static final String NAME = "raid";
	private static final Map<ResourceLocation, IRaidComponent> RAID_MAP = Maps.newHashMap();
	private static final Gson GSON = (new GsonBuilder()).create();
	
	public RaidLoader() {
		super(GSON, NAME + "s");
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> map, IResourceManager manager, IProfiler profiler) {
		/* refresh */
		RAID_MAP.clear();
		
		/* load */
		map.forEach((res, jsonElement) -> {
			try {
	            JsonObject jsonObject = JSONUtils.convertToJsonObject(jsonElement, NAME);
	            String type = JSONUtils.getAsString(jsonObject, StringUtil.TYPE, "");
	            IRaidComponent raid = RaidManager.getRaidType(type);
	            if(! raid.readJson(jsonObject)) {
	            	CRaid.LOGGER.debug("Skipping loading custom raid {} as it's conditions were not met", res); 
	            	return;
	            }
	            RAID_MAP.put(res, raid);
	         } catch (IllegalArgumentException | JsonParseException e) {
	        	 CRaid.LOGGER.error("Parsing error loading custom raid {}: {}", res, e.getMessage());
	         }
		});
		
		/* finish */
		RaidManager.finishRaidMap(RAID_MAP);
		
		CRaid.LOGGER.info("Loaded {} Custom Raids", RAID_MAP.size());
	}

}
