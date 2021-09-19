package com.hungteen.craid.common.raid;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hungteen.craid.Util;

import net.minecraft.util.JSONUtils;

public class WaveContent {

	private final List<SpawnContent> spawns;
	
	private WaveContent(List<SpawnContent> spawns) {
		this.spawns = spawns;
	}
	
	public static class Builder {
		
		private List<SpawnContent> spawns = new ArrayList<>();
		
		public Builder wave(List<SpawnContent> spawns) {
			this.spawns = spawns;
			return this;
		}
		
		public WaveContent build() {
			return new WaveContent(this.spawns);
		}
		
		public static Builder waveFromJson(JsonObject jsonObject) {
			
			final List<SpawnContent> spawns = new ArrayList<>();
			JsonArray jsonSpawns = JSONUtils.getAsJsonArray(jsonObject, Util.JSON_SPAWNS, new JsonArray());
			for(int i = 0; i < jsonSpawns.size(); ++ i) {
				JsonElement e = jsonSpawns.get(i);
				if(e.isJsonObject()) {
					spawns.add(SpawnContent.Builder.spawnFromJson(e.getAsJsonObject()).build());
				}
			}
			if(spawns.isEmpty()) {
				throw new JsonSyntaxException("Spawn list cannot be empty");
			}
			
	        return new Builder().wave(spawns);
		}
	}
	
}
