package hungteen.craid.common;

import hungteen.craid.platform.CRaidPlatformAPI;
import hungteen.craid.client.HTLibClientProxy;
import hungteen.craid.common.world.entity.DummyEntity;
import hungteen.craid.common.world.entity.DummyEntityManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @program: HTLib
 * @author: PangTeen
 * @create: 2024/10/25 16:14
 **/
public class HTLibProxy {

    private static volatile HTLibProxy instance;

    /**
     * @return 根据客户端服务端环境获取代理对象。
     */
    public static HTLibProxy get() {
        if (instance == null) {
            synchronized (HTLibProxy.class) {
                if (instance == null) {
                    if(CRaidPlatformAPI.get().isPhysicalClient()){
                        instance = new HTLibClientProxy();
                    } else {
                        instance = new HTLibProxy();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * 仅仅在客户端运行。
     */
    public void runOnClient(Supplier<Runnable> s) {

    }

    /**
     * @return 返回玩家实体。
     */
    public @Nullable Player getPlayer(){
        return null;
    }

    /* 虚拟实体相关 */

    /**
     * @param entity 加入客户端世界的虚拟实体。
     */
    public void addDummyEntity(DummyEntity entity) {
    }

    /**
     * @param entityID 被客户端世界移除的虚拟实体的 ID。
     */
    public void removeDummyEntity(int entityID) {
    }

    /**
     * @return 获取所有存在于客户端的虚拟实体。
     */
    public List<DummyEntity> getDummyEntities() {
        return List.of();
    }

    /**
     * @param level 客户端或服务端世界。
     * @return 获取所有存在的虚拟实体。
     */
    public List<DummyEntity> getDummyEntities(Level level) {
        if(level instanceof ServerLevel) {
            return DummyEntityManager.getDummyEntities((ServerLevel) level);
        }
        return getDummyEntities();
    }

    /**
     * @param id 客户端虚拟实体的 ID。
     * @return 获取指定 ID 的虚拟实体。
     */
    public Optional<DummyEntity> getDummyEntity(int id) {
        return Optional.empty();
    }

    /**
     * 清除客户端所有虚拟实体。
     */
    public void clearDummyEntities() {

    }

}
