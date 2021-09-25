package com.hungteen.craid.common.raid;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Sets;
import com.hungteen.craid.CRaid;
import com.hungteen.craid.common.world.WorldRaidData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;

public class Raid {

	private static final ITextComponent RAID_NAME_COMPONENT = new TranslationTextComponent("event.minecraft.raid");
	private final int id;
	public final ServerWorld world;
	public final ResourceLocation resource;
	protected RaidComponent content;
	protected BlockPos center;
	private final ServerBossInfo raidBar = new ServerBossInfo(RAID_NAME_COMPONENT, BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_10);
	protected Status status = Status.PREPARE;
	protected int tick = 0;
	protected int currentWave = 0;
	protected int currentSpawn = 0;
	protected Set<Entity> raiders = new HashSet<>();
	
	
	public Raid(int id, ServerWorld world, ResourceLocation res, BlockPos pos) {
		this.id = id;
		this.world = world;
		this.resource = res;
		this.center = pos;
	}
	
	public Raid(ServerWorld world, CompoundNBT nbt) {
		this.world = world;
		this.id = nbt.getInt("raid_id");
		this.status = Status.values()[nbt.getInt("raid_status")];
		this.resource = new ResourceLocation(nbt.getString("raid_resource"));
		this.tick = nbt.getInt("raid_tick");
		this.currentWave = nbt.getInt("current_wave");
		{// for raid center position.
			CompoundNBT tmp = nbt.getCompound("center_pos");
			this.center = new BlockPos(tmp.getInt("pos_x"), tmp.getInt("pos_y"), tmp.getInt("pos_z"));
		}
		{// for raiders entity id.
			ListNBT list = nbt.getList("raiders", 10);
			for(int i = 0; i < list.size(); ++ i) {
				final int id = list.getInt(i);
				final Entity entity = world.getEntity(id);
				if(entity != null) {
					this.raiders.add(entity);
				}
			}
		}
	}
	
	public void save(CompoundNBT nbt) {
		nbt.putInt("raid_id", this.id);
		nbt.putInt("raid_status", this.status.ordinal());
		nbt.putString("raid_resource", this.resource.toString());
		nbt.putInt("raid_tick", this.tick);
		nbt.putInt("current_wave", this.currentWave);
		{// for raid center position.
			CompoundNBT tmp = new CompoundNBT();
			nbt.putInt("pos_x", this.center.getX());
			nbt.putInt("pos_y", this.center.getY());
			nbt.putInt("pos_z", this.center.getZ());
			nbt.put("center_pos", tmp);
		}
		{// for raiders entity id.
			ListNBT list = new ListNBT();
			for(Entity entity : this.raiders) {
				list.add(IntNBT.valueOf(entity.getId()));
			}
			nbt.put("raiders", list);
		}
	}
	
	/**
	 * {@link WorldRaidData#tick()}
	 */
	public void tick() {
		if(this.isRemoving() || this.isStopping()) {//removed.
			return ;
		}
		if(this.getRaidContent() == null) {//no content.
			this.remove();
			CRaid.LOGGER.warn("Raid Error : Where is the raid content ?");
			return ;
		}
		this.tickBar();
		if(this.isPreparing()) {
			if(this.tick >= this.content.getPreCD()) {
				this.start();
			}
		} else if(this.isRunning()) {
			if(this.tick >= this.content.getCurrentDuration(this.currentWave)) {
				this.nextWave();
			}
			if(this.isLoss()) {//fail to start next wave.
				this.onLoss();
				return ;
			}
			if(this.isVictory()) {
				this.onVictory();
				return ;
			}
			this.tickWave();
		} else if(this.isLoss()) {
			if(this.tick >= this.content.getLossCD()) {
				this.remove();
			}
		} else if(this.isVictory()) {
			if(this.tick >= this.content.getWinCD()) {
				this.remove();
			}
		}
		++ this.tick;
	}
	
	public void tickWave() {
		final WaveComponent content = this.getRaidContent().getWaves().get(this.currentWave);
		final List<SpawnComponent> spawns = content.getSpawns();
		while(this.currentSpawn < spawns.size() && this.tick >= spawns.get(this.currentSpawn).getStartTick()) {
			this.spawnEntities(spawns.get(this.currentSpawn ++));
		}
		this.updateEntities();
	}
	
	public void tickBar() {
		this.updatePlayers();
		if(this.tick % 100 == 0) {
			System.out.println(this.status);
		}
		if(this.isPreparing()) {
			this.raidBar.setColor(Color.GREEN);
			this.raidBar.setPercent(this.tick * 1.0F / this.getRaidContent().getPreCD());
		} else if(this.isRunning()) {
			this.raidBar.setColor(Color.RED);
			this.raidBar.setPercent(1 - this.tick * 1.0F / this.getRaidContent().getCurrentDuration(this.currentWave));
		} else if(this.isVictory()) {
			this.raidBar.setColor(Color.YELLOW);
			this.raidBar.setPercent(1F);
		} else if(this.isLoss()) {
			this.raidBar.setColor(Color.WHITE);
			this.raidBar.setPercent(1F);
		}
	}
	
	/**
	 * update and maintain raiders. 
	 */
	public void updateEntities() {
		Iterator<Entity> it = this.raiders.iterator();
		while(it.hasNext()) {
			Entity entity = it.next();
			if(! entity.isAlive()) {
				it.remove();
			}
		}
	}
	
	private Predicate<ServerPlayerEntity> validPlayer() {
		return (player) -> {
			return player.isAlive();
		};
	}
	
	protected void updatePlayers() {
		final Set<ServerPlayerEntity> oldPlayers = Sets.newHashSet(this.raidBar.getPlayers());
		final Set<ServerPlayerEntity> newPlayers = Sets.newHashSet(this.world.getPlayers(this.validPlayer()));
		/* add new join players */
		newPlayers.forEach(p -> {
			if(! oldPlayers.contains(p)) {
				this.raidBar.addPlayer(p);
			}
		});
		/* remove offline players */
		oldPlayers.forEach(p -> {
			if(! newPlayers.contains(p)) {
				this.raidBar.removePlayer(p);
			}
		});
	}
	
	public void spawnEntities(SpawnComponent spawn) {
		final int count = spawn.getSpawnCount().getSpawnAmount();
		System.out.println("Spawn !");
		for(int i = 0; i < count; ++ i) {
			Entity entity = spawn.createEntity(world, center);
			if(entity != null) {
				this.raiders.add(entity);
			}
		}
	}
	
	/**
	 * run when prepare time is finished.
	 */
	public void start() {
		this.tick = 0;
		this.status = Status.RUNING;
	}
	
	/**
	 * when time met, check can start next wave or not.
	 */
	public boolean canNextWave() {
		return this.raiders.isEmpty();
	}
	
	public void nextWave() {
		this.tick = 0;
		if(this.canNextWave()) {
			this.currentSpawn = 0;
			if(++ this.currentWave >= this.getRaidContent().getWaves().size()) {
				this.status = Status.VICTORY;
			}
		} else {
			this.status = Status.LOSS;
		}
	}
	
	/**
	 * run when raid is not defeated.
	 */
	public void onLoss() {
		this.tick = 0;
	}

	/**
	 * run when raid is defeated.
	 */
	public void onVictory() {
		this.tick = 0;
	}
	
	public void remove() {
		this.status = Status.REMOVING;
		this.raidBar.removeAllPlayers();
		this.raiders.forEach(e -> e.remove());
	}
	
	public int getId() {
		return this.id;
	}
	
	public BlockPos getCenter() {
		return this.center;
	}
	
	public boolean isRaider(Entity raider) {
		return this.raiders.contains(raider);
	}
	
	public boolean isPreparing() {
		return this.status == Status.PREPARE;
	}
	
	public boolean isRunning() {
		return this.status == Status.RUNING;
	}
	
	public boolean isStopping() {
		return this.status == Status.STOPPING;
	}
	
	public boolean isRemoving() {
		return this.status == Status.REMOVING;
	}
	
	public boolean isLoss() {
		return this.status == Status.LOSS;
	}
	
	public boolean isVictory() {
		return this.status == Status.VICTORY;
	}
	
	public RaidComponent getRaidContent() {
		return this.content != null ? this.content : (this.content = RaidManager.getRaidContent(this.resource));
	}
	
	private static enum Status {
		  PREPARE,
	      RUNING,
	      VICTORY,
	      LOSS,
	      STOPPING,
	      REMOVING;
	}
	
}
