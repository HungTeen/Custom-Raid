package com.hungteen.craid.common.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hungteen.craid.CRaid;
import com.hungteen.craid.CRaidUtil;
import com.hungteen.craid.api.*;
import com.hungteen.craid.common.raid.RaidManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.Map.Entry;

public class RaidComponent implements IRaidComponent {

	public static final String NAME = "default";
	private List<IWaveComponent> waves = new ArrayList<>();
	private List<IRewardComponent> rewards = new ArrayList<>();
	private Set<String> tags = new HashSet<>();
	private List<String> authors = new ArrayList<>();
	private IPlacementComponent placement;
	private Component raidTitle = new TranslatableComponent("raid.craid.title");
	private Component winTitle = new TranslatableComponent("raid.craid.win_title");
	private Component lossTitle = new TranslatableComponent("raid.craid.loss_title");
	private BossEvent.BossBarColor barColor = BossEvent.BossBarColor.WHITE;
	private SoundEvent preSound;
	private SoundEvent waveSound;
	private SoundEvent winSound;
	private SoundEvent lossSound;
	private int winCD;
	private int lossCD;

	@Override
	public boolean readJson(JsonObject json) {

		/* titles */
		{
			Component text = Component.Serializer.fromJson(json.get(StringUtil.RAID_TITLE));
		    if(text != null) {
			    this.raidTitle = text;
		    }
		}
		{
			Component text = Component.Serializer.fromJson(json.get(StringUtil.WIN_TITLE));
		    if(text != null) {
			    this.winTitle = text;
		    }
		}
		{
			Component text = Component.Serializer.fromJson(json.get(StringUtil.LOSS_TITLE));
		    if(text != null) {
			    this.lossTitle = text;
		    }
		}


		/* authors */
		{
			final JsonArray array = GsonHelper.getAsJsonArray(json, StringUtil.AUTHORS, new JsonArray());
			if(array != null) {
				for(int i = 0; i < array.size(); ++ i) {
					final JsonElement e = array.get(i);
					if(e.isJsonPrimitive()) {
						this.authors.add(e.getAsString());
					}
				}
			}
		}

		/* tags */
		{
			final JsonArray array = GsonHelper.getAsJsonArray(json, StringUtil.TAGS, new JsonArray());
			if(array != null) {
				for(int i = 0; i < array.size(); ++ i) {
					final JsonElement e = array.get(i);
					if(e.isJsonPrimitive()) {
						this.tags.add(e.getAsString());
					}
				}
			}
		}

		/* raid cd */
		{
		    this.winCD = GsonHelper.getAsInt(json, StringUtil.WIN_CD, 200);
		    this.lossCD = GsonHelper.getAsInt(json, StringUtil.LOSS_CD, 100);
		}

		/* bar color */
		this.barColor = BossEvent.BossBarColor.byName(GsonHelper.getAsString(json, StringUtil.BAR_COLOR, "red"));

		/* sounds */
		{
			JsonObject obj = GsonHelper.getAsJsonObject(json, StringUtil.SOUNDS, null);
			if(obj != null) {
				this.preSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(GsonHelper.getAsString(obj, StringUtil.PRE_SOUND, "")));
			    this.waveSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(GsonHelper.getAsString(obj, StringUtil.WAVE_SOUND, "")));
			    this.winSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(GsonHelper.getAsString(obj, StringUtil.WIN_SOUND, "")));
			    this.lossSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(GsonHelper.getAsString(obj, StringUtil.LOSS_SOUND, "")));
			}
		}

		/* spawn placement */
		this.placement = CRaidUtil.readPlacement(json, true);

		/* waves */
		JsonArray jsonWaves = GsonHelper.getAsJsonArray(json, StringUtil.WAVES, new JsonArray());
		if(jsonWaves != null) {
			for(int i = 0; i < jsonWaves.size(); ++ i) {
			    JsonObject obj = jsonWaves.get(i).getAsJsonObject();
			    if(obj != null) {
			    	String type = GsonHelper.getAsString(obj, StringUtil.TYPE, "");
		            IWaveComponent wave = RaidManager.getWaveType(type);
		            if(! wave.readJson(obj)) {
		            	return false;
		            }
		            //by tick order.
		            wave.getSpawns().sort(new Sorter());
				    this.waves.add(wave);
			    }
			}
		}
	    if(this.waves.isEmpty()) {// mandatory !
		    throw new JsonSyntaxException("Wave list cannot be empty");
	    }

	    /* rewards */
	    {
	    	JsonObject obj = GsonHelper.getAsJsonObject(json, StringUtil.REWARDS, null);
		    if(obj != null && ! obj.entrySet().isEmpty()) {
		       for(Entry<String, JsonElement> entry : obj.entrySet()) {
		  		    final IRewardComponent tmp = RaidManager.getReward(entry.getKey());
		    	    if(tmp != null) {
		    		    tmp.readJson(entry.getValue());
		    		    this.rewards.add(tmp);
		    	    } else {
		    		    CRaid.LOGGER.warn("Placement Component : Read Spawn Placement Wrongly");
		    	    }
		   	    }
		    }
	    }

	    return true;
	}

	@Override
	public List<ISpawnComponent> getSpawns(int wavePos) {
		return this.waves.get(this.wavePos(wavePos)).getSpawns();
	}

	@Override
	public List<IRewardComponent> getRewards() {
		return this.rewards;
	}

	@Override
	public List<String> getAuthors() {
		return this.authors;
	}

	@Override
	public boolean hasTag(String tag) {
		return this.tags.contains(tag);
	}

	@Override
	public int getPrepareCD(int wavePos) {
		return this.waves.get(this.wavePos(wavePos)).getPrepareCD();
	}

	@Override
	public int getLastDuration(int wavePos) {
		return this.waves.get(this.wavePos(wavePos)).getLastDuration();
	}

	@Override
	public boolean isWaveFinish(int wavePos, int spawnPos) {
		return spawnPos >= this.waves.get(this.wavePos(wavePos)).getSpawns().size();
	}

	@Override
	public int getMaxWaveCount() {
		return this.waves.size();
	}

	@Override
	public int getWinCD() {
		return this.winCD;
	}

	@Override
	public int getLossCD() {
		return this.lossCD;
	}

	@Override
	public SoundEvent getPrepareSound() {
		return this.preSound;
	}

	@Override
	public SoundEvent getStartWaveSound() {
		return this.waveSound;
	}

	@Override
	public SoundEvent getWinSound() {
		return this.winSound;
	}

	@Override
	public SoundEvent getLossSound() {
		return this.lossSound;
	}

	@Override
	public IPlacementComponent getPlacement(int wavePos) {
		final IPlacementComponent p = this.waves.get(this.wavePos(wavePos)).getPlacement();
		return p == null ? this.placement : p;
	}

	@Override
	public Component getRaidTitle() {
		return this.raidTitle;
	}

	@Override
	public Component getWinTitle() {
		return this.winTitle;
	}

	@Override
	public Component getLossTitle() {
		return this.lossTitle;
	}

	@Override
	public BossEvent.BossBarColor getBarColor() {
		return this.barColor;
	}

	private int wavePos(int pos) {
		return Mth.clamp(pos, 0, this.waves.size() - 1);
	}

	private static class Sorter implements Comparator<ISpawnComponent> {

		public int compare(ISpawnComponent a, ISpawnComponent b) {
			final double d0 = a.getSpawnTick();
			final double d1 = b.getSpawnTick();
			return d0 < d1 ? -1 : d0 > d1 ? 1 : 0;
		}

	}

}

