package hungteen.craid.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import hungteen.craid.compat.kubejs.event.SpawnDummyEntityEventJS;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2023-02-11 23:29
 **/
public interface HTKubeJSEvents {

    EventGroup GROUP = EventGroup.of("HTLibEvents");

    EventHandler SPAWN_DUMMY_ENTITY = GROUP.server("spawnDummyEntity", () -> SpawnDummyEntityEventJS.class).hasResult();


}
