package com.hungteen.craid.common.raid;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hungteen.craid.CRaid;
import com.hungteen.craid.Util;
import com.hungteen.craid.api.ISpawnAmount;
import com.hungteen.craid.common.impl.amount.ConstantSpawn;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.impl.SummonCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class SpawnComponent {
	
	private final EntityType<?> entityType;
	private final ISpawnAmount spawnAmount;
	private final int startTick;
	private final CompoundNBT nbt;
	
	private SpawnComponent(EntityType<?> type, int startTick, ISpawnAmount count, CompoundNBT nbt) {
		this.entityType = type;
		this.startTick = startTick;
		this.spawnAmount = count;
		this.nbt = nbt;
	}
	
	public int getStartTick() {
		return this.startTick;
	}
	
	public ISpawnAmount getSpawnCount() {
		return this.spawnAmount;
	}
	
	/**
	 * copy from {@link SummonCommand}
	 */
	public Entity createEntity(ServerWorld world, BlockPos pos) {
		if(! World.isInSpawnableBounds(pos)) {
			CRaid.LOGGER.error("Invalid position when trying summon entity !");
			return null;
		}
		final CompoundNBT compound = nbt.copy();
		compound.putString("id", this.entityType.getRegistryName().toString());
		Entity entity = EntityType.loadEntityRecursive(compound, world, e -> {
			e.moveTo(pos, e.xRot, e.yRot);
			return e;
		});
		if(entity == null) {
			CRaid.LOGGER.error("summon entity failed !");
			return null;
		} else {
			if(! world.tryAddFreshEntityWithPassengers(entity)) {
				CRaid.LOGGER.error("summon entity duplicated uuid !");
				return null;
			}
		}
		return entity;
	}
	
	public static class Builder {
		
		private EntityType<?> entityType;
		private ISpawnAmount spawnAmount;
		private int startTick;
		private CompoundNBT nbt;
		
		public Builder type(EntityType<?> type) {
			this.entityType = type;
			return this;
		}
		
		public Builder count(ISpawnAmount spawnAmount) {
			this.spawnAmount = spawnAmount;
			return this;
		}
		
		public Builder start(int tick) {
			this.startTick = tick;
			return this;
		}
		
		public Builder nbt(CompoundNBT nbt) {
			this.nbt = nbt;
			return this;
		}
		
		public SpawnComponent build() {
			return new SpawnComponent(this.entityType, this.startTick, this.spawnAmount, this.nbt);
		}
		
		public static Builder spawnFromJson(JsonObject jsonObject) {
			
			/* entity type */
			final String name = JSONUtils.getAsString(jsonObject, Util.JSON_ENTITY_TYPE, "");
			ResourceLocation res = new ResourceLocation(name);
			final EntityType<?> type;
			if(ForgeRegistries.ENTITIES.containsKey(res)) {
				type = ForgeRegistries.ENTITIES.getValue(res);
			} else {
				throw new JsonSyntaxException("entity type cannot be empty or wrong format");
			}
			
			/* spawn amount */
			ISpawnAmount amount = new ConstantSpawn();
		    JsonObject obj = JSONUtils.getAsJsonObject(jsonObject, Util.JSON_ENTITY_SPAWN_AMOUNT);
		    if(obj != null && ! obj.entrySet().isEmpty()) {
		    	for(Entry<String, JsonElement> entry : obj.entrySet()) {
		    		final ISpawnAmount tmp = RaidManager.getSpawnAmount(entry.getKey());
		    		if(tmp != null) {
		    			tmp.readJson(entry.getValue());
		    			amount = tmp;
		    		} else {
		    			CRaid.LOGGER.warn("Spawn Component : Read Spawn Amount Wrongly");
		    		}
		    		break;
		    	}
		    }
			
			final int tick = JSONUtils.getAsInt(jsonObject, Util.JSON_ENTITY_START_TICK, 0);
			
			/* nbt */
			CompoundNBT nbt = new CompoundNBT();
			if(jsonObject.has(Util.JSON_ENTITY_NBT)) {
				try {
				    nbt = JsonToNBT.parseTag(JSONUtils.convertToString(jsonObject.get(Util.JSON_ENTITY_NBT), Util.JSON_ENTITY_NBT));
			    } catch (CommandSyntaxException e) {
				    throw new JsonSyntaxException("Invalid nbt tag: " + e.getMessage());
			    }
			}
			
	        return new Builder().type(type).count(amount).start(tick).nbt(nbt);
		}
	}
}
