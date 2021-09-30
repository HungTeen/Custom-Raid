package com.hungteen.craid.common.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hungteen.craid.CRaidUtil;
import com.hungteen.craid.api.IPlacementComponent;
import com.hungteen.craid.api.ISpawnComponent;
import com.hungteen.craid.api.IWaveComponent;
import com.hungteen.craid.api.StringUtil;
import com.hungteen.craid.common.raid.RaidManager;

import net.minecraft.util.JSONUtils;

public class WaveComponent implements IWaveComponent {

	public static final String NAME = "default";
	private List<ISpawnComponent> spawns = new ArrayList<>();
	private IPlacementComponent placement;
	private int duration;
	private int preCD;
	
	@Override
	public boolean readJson(JsonObject json) {
		
		/* duration */
		this.duration = JSONUtils.getAsInt(json, StringUtil.WAVE_DURATION, 0);
		if(this.duration == 0) {
			throw new JsonSyntaxException("Wave duration cannot be empty or zero");
		}
		
		/* pre tick */
		this.preCD = JSONUtils.getAsInt(json, StringUtil.PRE_CD, 100);
		
		/* spawn placement */
		this.placement = CRaidUtil.readPlacement(json, false);
		
		/* spawn list */
		JsonArray jsonSpawns = JSONUtils.getAsJsonArray(json, StringUtil.SPAWNS, new JsonArray());
		for(int i = 0; i < jsonSpawns.size(); ++ i) {
			JsonObject obj = jsonSpawns.get(i).getAsJsonObject();
		    if(obj != null) {
		    	String type = JSONUtils.getAsString(obj, StringUtil.TYPE, "");
	            ISpawnComponent spawn = RaidManager.getSpawnType(type);
	            if(! spawn.readJson(obj)) {
	            	return false;
	            }
			    this.spawns.add(spawn);
		    }
		}
		if(this.spawns.isEmpty()) {
			throw new JsonSyntaxException("Spawn list cannot be empty");
		}
		
		return true;
	}
	
	@Override
	public List<ISpawnComponent> getSpawns(){
		return this.spawns;
	}
	
	@Override
	public IPlacementComponent getPlacement() {
		return this.placement;
	}
	
	@Override
	public int getPrepareCD() {
		return this.preCD;
	}
	
	@Override
	public int getLastDuration() {
		return this.duration;
	}
	
}
