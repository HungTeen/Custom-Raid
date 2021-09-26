package com.hungteen.craid.api;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;

import com.google.common.base.Suppliers;
import com.hungteen.craid.common.raid.SpawnComponent;

public class CRaidAPI {

	private static final Supplier<ICustomRaidAPI> LAZY_INSTANCE = Suppliers.memoize(() -> {
		try {
			return (ICustomRaidAPI) Class.forName("com.hungteen.craid.common.impl.RaidManager").newInstance();
		} catch (ReflectiveOperationException e) {
			LogManager.getLogger().warn("Unable to find RaidManager, using a dummy one");
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
	}
	
}
