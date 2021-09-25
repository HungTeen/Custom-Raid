package com.hungteen.craid.common.raid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hungteen.craid.Util;

import net.minecraft.util.JSONUtils;

public class WaveComponent {

	private final List<SpawnComponent> spawns;
	private final int duration;
	
	private WaveComponent(List<SpawnComponent> spawns, int duration) {
		this.spawns = spawns;
		this.duration = duration;
		
		this.spawns.sort(new Sorter());
	}
	
	public List<SpawnComponent> getSpawns(){
		return this.spawns;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	private static class Sorter implements Comparator<SpawnComponent> {

		public int compare(SpawnComponent a, SpawnComponent b) {
			final double d0 = a.getStartTick();
			final double d1 = b.getStartTick();
			return d0 < d1 ? -1 : d0 > d1 ? 1 : 0;
		}
		
	}
	
	public static class Builder {
		
		private List<SpawnComponent> spawns = new ArrayList<>();
		private int duration;
		
		public Builder wave(List<SpawnComponent> spawns) {
			this.spawns = spawns;
			return this;
		}
		
		public Builder len(int duration) {
			this.duration = duration;
			return this;
		}
		
		public WaveComponent build() {
			return new WaveComponent(this.spawns, this.duration);
		}
		
		public static Builder waveFromJson(JsonObject jsonObject) {
			
			final int duration = JSONUtils.getAsInt(jsonObject, Util.JSON_WAVE_DURATION, 0);
			if(duration == 0) {
				throw new JsonSyntaxException("Wave duration cannot be empty or zero");
			}
			
			final List<SpawnComponent> spawns = new ArrayList<>();
			JsonArray jsonSpawns = JSONUtils.getAsJsonArray(jsonObject, Util.JSON_SPAWNS, new JsonArray());
			for(int i = 0; i < jsonSpawns.size(); ++ i) {
				JsonElement e = jsonSpawns.get(i);
				if(e.isJsonObject()) {
					spawns.add(SpawnComponent.Builder.spawnFromJson(e.getAsJsonObject()).build());
				}
			}
			if(spawns.isEmpty()) {
				throw new JsonSyntaxException("Spawn list cannot be empty");
			}
			
	        return new Builder().wave(spawns).len(duration);
		}
	}
	
}
