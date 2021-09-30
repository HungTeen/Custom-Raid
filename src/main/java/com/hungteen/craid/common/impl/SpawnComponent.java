package com.hungteen.craid.common.impl;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hungteen.craid.CRaid;
import com.hungteen.craid.CRaidUtil;
import com.hungteen.craid.api.IAmountComponent;
import com.hungteen.craid.api.IPlacementComponent;
import com.hungteen.craid.api.ISpawnComponent;
import com.hungteen.craid.api.StringUtil;
import com.hungteen.craid.common.impl.amount.ConstantAmount;
import com.hungteen.craid.common.raid.RaidManager;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class SpawnComponent implements ISpawnComponent {
	
	public static final String NAME = "default";
	private EntityType<?> entityType;
	private IAmountComponent spawnAmount = new ConstantAmount();
	private CompoundNBT nbt = new CompoundNBT();
	private IPlacementComponent placement;
	private int spawnTick;
	
	@Override
	public boolean readJson(JsonObject json) {
		
		/* entity type */
		this.entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(JSONUtils.getAsString(json, StringUtil.ENTITY_TYPE, "")));
		if(this.entityType == null) {
			throw new JsonSyntaxException("entity type cannot be empty or wrong format");
		}
		
		/* spawn amount */
		{
			JsonObject obj = JSONUtils.getAsJsonObject(json, StringUtil.SPAWN_AMOUNT);
	        if(obj != null && ! obj.entrySet().isEmpty()) {
	    	    for(Entry<String, JsonElement> entry : obj.entrySet()) {
	    		    final IAmountComponent tmp = RaidManager.getSpawnAmount(entry.getKey());
	    		    if(tmp != null) {
	    			    tmp.readJson(entry.getValue());
	    			    this.spawnAmount = tmp;
	    		    } else {
	    			    CRaid.LOGGER.warn("Amount Component : Read Spawn Amount Wrongly");
	    		    }
	    		    break;
	    	    }
	        }
		}
	    
	    /* spawn placement */
		this.placement = CRaidUtil.readPlacement(json, false);
		
		/* spawn tick */
		this.spawnTick = JSONUtils.getAsInt(json, StringUtil.SPAWN_TICK, 0);
		
		/* nbt */
		if(json.has(StringUtil.ENTITY_NBT)) {
			try {
			    nbt = JsonToNBT.parseTag(JSONUtils.convertToString(json.get(StringUtil.ENTITY_NBT), StringUtil.ENTITY_NBT));
		    } catch (CommandSyntaxException e) {
			    throw new JsonSyntaxException("Invalid nbt tag: " + e.getMessage());
		    }
		}
		
		return true;
	}
	
	@Override
	public int getSpawnTick() {
		return this.spawnTick;
	}
	
	@Override
	public int getSpawnAmount() {
		return this.spawnAmount.getSpawnAmount();
	}
	
	@Override
	public IPlacementComponent getPlacement() {
		return this.placement;
	}
	
	@Override
	public CompoundNBT getNBT() {
		return this.nbt;
	}
	
	@Override
	public EntityType<?> getSpawnType() {
		return this.entityType;
	}
	
}
