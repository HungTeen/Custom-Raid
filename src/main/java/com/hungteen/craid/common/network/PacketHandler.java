package com.hungteen.craid.common.network;

import com.hungteen.craid.CRaid;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

	private static final ResourceLocation CHANNEL_NAME = new ResourceLocation(CRaid.MOD_ID + ":networking");
	private static final String PROTOCOL_VERSION = "1.0";

	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			CHANNEL_NAME,
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void init(FMLCommonSetupEvent event) {
		int id = 0;
		CHANNEL.registerMessage(id ++, PlaySoundPacket.class, PlaySoundPacket::encode, PlaySoundPacket::new, PlaySoundPacket.Handler::onMessage);

	}

}
