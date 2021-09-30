package com.hungteen.craid.api;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;

import com.google.common.base.Suppliers;
import com.hungteen.craid.common.impl.SpawnComponent;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class CRaidAPI {

	private static final Supplier<ICustomRaidAPI> LAZY_INSTANCE = Suppliers.memoize(() -> {
		try {
			return (ICustomRaidAPI) Class.forName("com.hungteen.craid.common.impl.CRaidAPIImpl").newInstance();
		} catch (ReflectiveOperationException e) {
			LogManager.getLogger().warn("Unable to find CRaidAPIImpl, using a dummy one");
			return DummyAPI.INSTANCE;
		}
	});

	/**
	 * Obtain the CustomRaid API, either a valid implementation if CustomRaid is present, else
	 * a dummy instance instead if CustomRaid is absent.
	 */
	public static ICustomRaidAPI get() {
		return LAZY_INSTANCE.get();
	}
	
	public interface ICustomRaidAPI {
		
		/**
		 * register new spawn amount getter used in {@link SpawnComponent} 
		 */
		void registerSpawnAmount(String name, Class<? extends IAmountComponent> c);
		
		/**
		 * register new spawn position getter used in {@link SpawnComponent} 
		 */
		void registerSpawnPlacement(String name, Class<? extends IPlacementComponent> c);

		/**
		 * register new raid type, so that u can read your own json.
		 */
		void registerRaidType(String name, Class<? extends IRaidComponent> c);
		
		/**
		 * register new wave type, so that u can read your own json.
		 */
		void registerWaveType(String name, Class<? extends IWaveComponent> c);
		
		/**
		 * register new spawn type, so that u can read your own json.
		 */
		void registerSpawnType(String name, Class<? extends ISpawnComponent> c);

		/**
		 * register new reward type, so that u can read your own json.
		 */
		void registerReward(String name, Class<? extends IRewardComponent> c);
		
		void createRaid(ServerWorld world, ResourceLocation res, BlockPos pos);
		
		boolean isRaider(ServerWorld world, Entity entity);
	}
	
}
