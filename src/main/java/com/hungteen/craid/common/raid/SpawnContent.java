package com.hungteen.craid.common.raid;

import com.google.gson.JsonObject;
import com.hungteen.craid.Util;

import net.minecraft.util.JSONUtils;

public class SpawnContent {
	
	private final String entityName;
	private final int spawnCount;
	
	private SpawnContent(String name, int count) {
		this.entityName = name;
		this.spawnCount = count;
	}
	
	public static class Builder {
		
		private String entityName;
		private int spawnCount = 1;
		
		public Builder name(String name) {
			this.entityName = name;
			return this;
		}
		
		public Builder count(int cnt) {
			this.spawnCount = cnt;
			return this;
		}
		
		public SpawnContent build() {
			return new SpawnContent(this.entityName, this.spawnCount);
		}
		
		public static Builder spawnFromJson(JsonObject jsonObject) {
			
			final String name = JSONUtils.getAsString(jsonObject, Util.JSON_ENTITY_TYPE, null);
			final int count = JSONUtils.getAsInt(jsonObject, Util.JSON_ENTITY_SPAWN_COUNT, 0);
			
	        return new Builder().name(name).count(count);
		}
	}
}
