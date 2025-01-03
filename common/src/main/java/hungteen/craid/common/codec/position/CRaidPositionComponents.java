package hungteen.craid.common.codec.position;

import com.mojang.serialization.Codec;
import hungteen.craid.api.CRaidHelper;
import hungteen.craid.api.raid.PositionComponent;
import hungteen.craid.api.raid.PositionType;
import hungteen.htlib.api.registry.HTCodecRegistry;
import hungteen.htlib.common.impl.registry.HTRegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.phys.Vec3;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/18 10:37
 */
public interface CRaidPositionComponents {

    HTCodecRegistry<PositionComponent> PLACEMENTS = HTRegistryManager.codec(CRaidHelper.prefix("position"), CRaidPositionComponents::getDirectCodec);

    PositionComponent DEFAULT = new CenterAreaPosition(
            Vec3.ZERO, 0, 1, true, 0, true
    );

    ResourceKey<PositionComponent> TEST = create("test");
    ResourceKey<PositionComponent> COMMON = create("common");

    static void register(BootstrapContext<PositionComponent> context) {
        context.register(TEST, new CenterAreaPosition(
                Vec3.ZERO, 0, 1, true, 0, true
        ));
        context.register(COMMON, new CenterAreaPosition(
                Vec3.ZERO, 0, 10, true, 0, false
        ));
    }

    static Codec<PositionComponent> getDirectCodec() {
        return CRaidPositionTypes.registry().byNameCodec().dispatch(PositionComponent::getType, PositionType::codec);
    }

    static Codec<Holder<PositionComponent>> getCodec() {
        return registry().getHolderCodec(getDirectCodec());
    }

    static ResourceKey<PositionComponent> create(String name) {
        return registry().createKey(CRaidHelper.prefix(name));
    }

    static HTCodecRegistry<PositionComponent> registry() {
        return PLACEMENTS;
    }

}
