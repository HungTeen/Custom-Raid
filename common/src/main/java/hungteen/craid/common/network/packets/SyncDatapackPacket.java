package hungteen.craid.common.network.packets;

import hungteen.craid.api.CustomRaidAPI;
import hungteen.craid.common.impl.registry.HTRegistryManager;
import hungteen.craid.common.network.ClientPacketContext;
import hungteen.craid.common.network.HTPlayToClientPayload;
import hungteen.craid.util.helper.CodecHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2023/8/25 16:23
 */
public record SyncDatapackPacket(ResourceLocation type, ResourceLocation location, CompoundTag data) implements HTPlayToClientPayload {

    public static final StreamCodec<FriendlyByteBuf, SyncDatapackPacket> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            SyncDatapackPacket::type,
            ResourceLocation.STREAM_CODEC,
            SyncDatapackPacket::location,
            ByteBufCodecs.TRUSTED_COMPOUND_TAG,
            SyncDatapackPacket::data,
            SyncDatapackPacket::new
    );

    @Override
    public void process(ClientPacketContext context) {
        HTRegistryManager.getCodecRegistry(type()).ifPresent(registry -> {
            registry.getSyncCodec().flatMap(codec -> CodecHelper.parse(codec, data())
                    .resultOrPartial(msg -> CustomRaidAPI.logger().error("HTLib sync datapack : {}", msg))).ifPresent(value -> {
                registry.syncRegister(location(), value);
            });
        });
    }

}
