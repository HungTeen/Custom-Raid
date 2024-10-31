package hungteen.craid.compat.kubejs.event;

import dev.latvian.mods.kubejs.level.LevelEventJS;
import hungteen.craid.common.event.events.DummyEntityEvent;
import hungteen.craid.common.world.entity.DummyEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2023-02-11 23:27
 **/
public class SpawnDummyEntityEventJS extends LevelEventJS {

    private final DummyEntityEvent.DummyEntitySpawnEvent event;

    public SpawnDummyEntityEventJS(DummyEntityEvent.DummyEntitySpawnEvent event) {
        this.event = event;
    }

    public DummyEntity getDummyEntity() {
        return event.getDummyEntity();
    }

    public ResourceLocation getType(){
        return getDummyEntity().getEntityType().getLocation();
    }

    @Override
    public Level getLevel() {
        return event.getLevel();
    }
}
