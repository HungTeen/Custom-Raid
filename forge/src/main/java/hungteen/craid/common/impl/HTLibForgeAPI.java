package hungteen.craid.common.impl;

import hungteen.craid.platform.HTLibPlatformAPI;
import hungteen.craid.api.registry.HTCodecRegistry;
import hungteen.craid.api.registry.HTCustomRegistry;
import hungteen.craid.platform.HTModContainer;
import hungteen.craid.platform.HTModInfo;
import hungteen.craid.platform.Platform;
import hungteen.craid.common.event.events.DummyEntityEvent;
import hungteen.craid.common.impl.registry.HTCodecRegistryImpl;
import hungteen.craid.common.impl.registry.HTForgeCustomRegistry;
import hungteen.craid.common.impl.registry.HTForgeVanillaRegistry;
import hungteen.craid.common.impl.registry.HTVanillaRegistry;
import hungteen.craid.common.network.NetworkHandler;
import hungteen.craid.common.world.entity.DummyEntity;
import hungteen.craid.util.ForgeHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.List;
import java.util.Optional;

/**
 * @program: HTLib
 * @author: PangTeen
 * @create: 2024/10/22 15:13
 **/
public class HTLibForgeAPI implements HTLibPlatformAPI {

    @Override
    public Platform getPlatform() {
        return Platform.FORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ForgeHelper.isModLoaded(modId);
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    @Override
    public List<? extends HTModInfo> getModInfoList() {
        return ModList.get().getMods().stream().map(HTForgeModInfo::new).toList();
    }

    @Override
    public Optional<? extends HTModContainer> getModContainer(String modId) {
        return ModList.get().getModContainerById(modId).map(HTForgeModContainer::new);
    }

    @Override
    public boolean postDummyEntityCreateEvent(Level level, DummyEntity dummyEntity) {
        return MinecraftForge.EVENT_BUS.post(new DummyEntityEvent.DummyEntitySpawnEvent(level, dummyEntity));
    }

    @Override
    public <MSG> void sendToServer(MSG msg) {
        NetworkHandler.sendToServer(msg);
    }

    @Override
    public <MSG> void sendToClient(MSG msg) {
        NetworkHandler.sendToClient(msg);
    }

    @Override
    public <MSG> void sendToClient(ServerPlayer serverPlayer, MSG msg) {
        NetworkHandler.sendToClient(serverPlayer, msg);
    }

    @Override
    public <MSG> void sendToClient(Level level, Vec3 vec, double dis, MSG msg) {
        NetworkHandler.sendToNearByClient(level, vec, dis, msg);
    }

    @Override
    public HTCodecRegistry.HTCodecRegistryFactory createCodecRegistryFactory() {
        return HTCodecRegistryImpl::new;
    }

    @Override
    public HTCustomRegistry.HTCustomRegistryFactory createCustomRegistryFactory() {
        return HTForgeCustomRegistry::new;
    }

    @Override
    public HTVanillaRegistry.HTVanillaRegistryFactory createVanillaRegistryFactory() {
        return HTForgeVanillaRegistry::new;
    }

}
