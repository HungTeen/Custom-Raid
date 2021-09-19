package com.hungteen.craid.common.raid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hungteen.craid.CustomRaid;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class RaidManager extends JsonReloadListener{

	public static final String NAME = "raid";
	public static final Map<ResourceLocation, RaidContent> RAID_MAP = Maps.newHashMap();
	public static final List<RaidContent> RAID_LIST = new ArrayList<>();
	private static final Gson GSON = (new GsonBuilder()).create();
	
	public RaidManager() {
		super(GSON, NAME + "s");
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> map, IResourceManager manager, IProfiler profiler) {
		/* refresh */
		RAID_MAP.clear();
		
		/* load */
		map.forEach((res, jsonElement) -> {
			try {
	            JsonObject jsonObject = JSONUtils.convertToJsonObject(jsonElement, "advancement");
	            RaidContent.Builder builder = RaidContent.Builder.fromJson(jsonObject);
	            if (builder == null) {
	                CustomRaid.LOGGER.debug("Skipping loading custom raid {} as it's conditions were not met", res);
	                return;
	            }
	            RAID_MAP.put(res, builder.build());
	         } catch (IllegalArgumentException | JsonParseException e) {
	        	 CustomRaid.LOGGER.error("Parsing error loading custom raid {}: {}", res, e.getMessage());
	         }
		});
		
		CustomRaid.LOGGER.info("Loaded {} Custom Raids", RAID_MAP.size());
	}

}
