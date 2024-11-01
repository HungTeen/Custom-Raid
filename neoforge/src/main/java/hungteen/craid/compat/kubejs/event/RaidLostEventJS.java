package hungteen.craid.compat.kubejs.event;

import dev.latvian.mods.kubejs.level.LevelEventJS;
import hungteen.craid.common.event.events.RaidEvent;
import hungteen.craid.common.world.raid.HTRaidImpl;
import net.minecraft.world.level.Level;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2023-02-11 23:00
 **/
public class RaidLostEventJS extends LevelEventJS {
    private final RaidEvent.RaidLostEvent event;

    public RaidLostEventJS(RaidEvent.RaidLostEvent event) {
        this.event = event;
    }

    @Override
    public Level getLevel() {
        return event.getLevel();
    }

    public HTRaidImpl getRaid(){
        return event.getRaid();
    }
}
