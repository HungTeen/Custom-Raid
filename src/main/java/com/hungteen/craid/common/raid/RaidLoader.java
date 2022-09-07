package com.hungteen.craid.common.raid;

import com.google.common.collect.Maps;
import com.google.gson.*;
import com.hungteen.craid.CRaid;
import com.hungteen.craid.api.IRaidComponent;
import com.hungteen.craid.api.StringUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class RaidLoader extends SimpleJsonResourceReloadListener {

	public static final String NAME = "raid";
	private static final Map<ResourceLocation, IRaidComponent> RAID_MAP = Maps.newHashMap();
	private static final Gson GSON = (new GsonBuilder()).create();

	public RaidLoader() {
		super(GSON, NAME + "s");
	}


	@Override
	protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
		/* refresh */
		RAID_MAP.clear();

		/* load */
		map.forEach((res, jsonElement) -> {
			try {
	            JsonObject jsonObject = GsonHelper.convertToJsonObject(jsonElement, NAME);
	            String type = GsonHelper.getAsString(jsonObject, StringUtil.TYPE, "");
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
