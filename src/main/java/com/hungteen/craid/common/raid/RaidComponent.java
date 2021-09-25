package com.hungteen.craid.common.raid;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hungteen.craid.Util;

import net.minecraft.util.JSONUtils;

public class RaidComponent {

	private final List<WaveComponent> waves;
	private final int preCD;
	private final int winCD;
	private final int lossCD;
	
	private RaidComponent(List<WaveComponent> waves, int preCD, int winCD, int lossCD) {
		this.waves = waves;
		this.preCD = preCD;
		this.winCD = winCD;
		this.lossCD = lossCD;
	}
	
	public List<WaveComponent> getWaves() {
		return this.waves;
	}
	
	public int getCurrentDuration(int pos) {
		return this.waves.get(pos).getDuration();
	}
	
	public int getPreCD() {
		return this.preCD;
	}
	
	public int getWinCD() {
		return this.winCD;
	}
	
	public int getLossCD() {
		return this.lossCD;
	}
	
	public static class Builder {
		
		private List<WaveComponent> waves = new ArrayList<>();
		private int preCD = 100;
		private int winCD = 200;
		private int lossCD = 100;
		
		public Builder wave(List<WaveComponent> waves) {
			this.waves = waves;
			return this;
		}
		
		public Builder pre(int preCD) {
			this.preCD = preCD;
			return this;
		}
		
		public Builder win(int winCD) {
			this.preCD = winCD;
			return this;
		}
		
		public Builder loss(int lossCD) {
			this.preCD = lossCD;
			return this;
		}
		
		public RaidComponent build() {
			return new RaidComponent(this.waves, this.preCD, this.winCD, this.lossCD);
		}
		
		public static Builder fromJson(JsonObject jsonObject) {
			
			final int preCD = JSONUtils.getAsInt(jsonObject, Util.JSON_RAID_PRE_CD, 100);
			final int winCD = JSONUtils.getAsInt(jsonObject, Util.JSON_RAID_WIN_CD, 200);
			final int lossCD = JSONUtils.getAsInt(jsonObject, Util.JSON_RAID_LOSS_CD, 100);
			
			/* waves */
			final List<WaveComponent> waves = new ArrayList<>();
			JsonArray jsonWaves = JSONUtils.getAsJsonArray(jsonObject, Util.JSON_WAVES, new JsonArray());
			if(jsonWaves != null) {
				for(int i = 0; i < jsonWaves.size(); ++ i) {
				    JsonElement e = jsonWaves.get(i);
				    if(e.isJsonObject()) {
					    waves.add(WaveComponent.Builder.waveFromJson(e.getAsJsonObject()).build());
				    }
				}
			}
		    if(waves.isEmpty()) {// mandatory !
			    throw new JsonSyntaxException("Wave list cannot be empty");
		    }

	        return new Builder().wave(waves).pre(preCD).win(winCD).loss(lossCD);
		}
	}
}
	
