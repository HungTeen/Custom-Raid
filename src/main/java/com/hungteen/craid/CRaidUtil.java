package com.hungteen.craid;

import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hungteen.craid.api.IPlacementComponent;
import com.hungteen.craid.api.StringUtil;
import com.hungteen.craid.common.impl.placement.CenterPlacement;
import com.hungteen.craid.common.network.PacketHandler;
import com.hungteen.craid.common.network.PlaySoundPacket;
import com.hungteen.craid.common.raid.RaidManager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CRaidUtil {

	/**
	 * flag set false to make default placement as null.
	 */
	@Nullable
	public static IPlacementComponent readPlacement(JsonObject jsonObject, boolean flag) {
		/* spawn placement */
		IPlacementComponent placement = flag ? new CenterPlacement() : null;
		JsonObject obj = JSONUtils.getAsJsonObject(jsonObject, StringUtil.SPAWN_PLACEMENT, null);
	    if(obj != null && ! obj.entrySet().isEmpty()) {
	       for(Entry<String, JsonElement> entry : obj.entrySet()) {
	  		    final IPlacementComponent tmp = RaidManager.getSpawnPlacement(entry.getKey());
	    	    if(tmp != null) {
	    		    tmp.readJson(entry.getValue());
	    		    placement = tmp;
	    	    } else {
	    		    CRaid.LOGGER.warn("Placement Component : Read Spawn Placement Wrongly");
	    	    }
	   		    break;
	   	    }
	    }
	    return placement;
	}
	
	public static ResourceLocation prefix(String s) {
		return new ResourceLocation(CRaid.MOD_ID, s);
	}
	
	/**
	 * gen random from min to max.
	 */
	public static int getRandomMinMax(Random rand, int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
	
	/**
	 * get random from - range to range.
	 * {@link EntityUtil#onMobEntityRandomPosSpawn(net.minecraft.world.IWorld, net.minecraft.entity.MobEntity, BlockPos, int)}
	 */
	public static int getRandomInRange(Random rand, int range) {
		return rand.nextInt(range << 1 | 1) - range;
	}
	
	public static void playClientSound(PlayerEntity player, SoundEvent ev) {
		if(ev != null) {
			PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> {
			    return (ServerPlayerEntity) player;
		    }), new PlaySoundPacket(ev.getRegistryName().toString()));
		}
	}
	
	public static void sendMsgTo(PlayerEntity player, ITextComponent text) {
		player.sendMessage(text, Util.NIL_UUID);
	}
	
	public static boolean isRaidEnable() {
		return CRaidConfig.COMMON_CONFIG.GlobalSettings.EnableRaid.get();
	}
	
	public static int getRaidWaitTime() {
		return CRaidConfig.COMMON_CONFIG.GlobalSettings.RaidWaitTime.get();
	}
	
	public static int getRaidRange() {
		return CRaidConfig.COMMON_CONFIG.GlobalSettings.RaidRange.get();
	}
	
}
