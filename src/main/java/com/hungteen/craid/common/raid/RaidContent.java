package com.hungteen.craid.common.raid;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hungteen.craid.Util;

import net.minecraft.util.JSONUtils;

public class RaidContent {

	private final String name;
	private final List<WaveContent> waves;
	
	
	private RaidContent(String name, List<WaveContent> waves) {
		this.name = name;
		this.waves = waves;
	}
	
	public static class Builder {
		
		private String name;
		private List<WaveContent> waves = new ArrayList<>();
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder wave(List<WaveContent> waves) {
			this.waves = waves;
			return this;
		}
		
		public RaidContent build() {
			return new RaidContent(this.name, this.waves);
		}
		
		public static Builder fromJson(JsonObject jsonObject) {
			
			final String name = JSONUtils.getAsString(jsonObject, Util.JSON_RAID_NAME, null);
			if(name == null) {
				throw new JsonSyntaxException("Raid name cannot be empty");
			}
			
			final List<WaveContent> waves = new ArrayList<>();
			JsonArray jsonWaves = JSONUtils.getAsJsonArray(jsonObject, Util.JSON_WAVES, new JsonArray());
			for(int i = 0; i < jsonWaves.size(); ++ i) {
				JsonElement e = jsonWaves.get(i);
				if(e.isJsonObject()) {
					waves.add(WaveContent.Builder.waveFromJson(e.getAsJsonObject()).build());
				}
			}
			if(waves.isEmpty()) {
				throw new JsonSyntaxException("Wave list cannot be empty");
			}
			
//	        Map<String, Criterion> map = Criterion.criteriaFromJson(JSONUtils.getAsJsonObject(p_241043_0_, "criteria"), p_241043_1_);
//	        if (map.isEmpty()) {
//	           
//	        } else {
	           
//	           String[][] astring = new String[jsonarray.size()][];
	//
//	           for(int i = 0; i < jsonarray.size(); ++i) {
//	              JsonArray jsonarray1 = JSONUtils.convertToJsonArray(jsonarray.get(i), "requirements[" + i + "]");
//	              astring[i] = new String[jsonarray1.size()];
	//
//	              for(int j = 0; j < jsonarray1.size(); ++j) {
//	                 astring[i][j] = JSONUtils.convertToString(jsonarray1.get(j), "requirements[" + i + "][" + j + "]");
//	              }
//	           }
	//
//	           if (astring.length == 0) {
//	              astring = new String[map.size()][];
//	              int k = 0;
	//
//	              for(String s2 : map.keySet()) {
//	                 astring[k++] = new String[]{s2};
//	              }
//	           }
	//
//	           for(String[] astring1 : astring) {
//	              if (astring1.length == 0 && map.isEmpty()) {
//	                 throw new JsonSyntaxException("Requirement entry cannot be empty");
//	              }
	//
//	              for(String s : astring1) {
//	                 if (!map.containsKey(s)) {
//	                    throw new JsonSyntaxException("Unknown required criterion '" + s + "'");
//	                 }
//	              }
//	           }
	//
//	           for(String s1 : map.keySet()) {
//	              boolean flag = false;
	//
//	              for(String[] astring2 : astring) {
//	                 if (ArrayUtils.contains(astring2, s1)) {
//	                    flag = true;
//	                    break;
//	                 }
//	              }
	//
//	              if (!flag) {
//	                 throw new JsonSyntaxException("Criterion '" + s1 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
//	              }
//	           }

	        return new Builder().name(name).wave(waves);
		}
	}
}
	
