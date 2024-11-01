package hungteen.craid.compat.kubejs.event;

import dev.latvian.mods.kubejs.level.LevelEventJS;
import hungteen.craid.common.event.events.RaidEvent;
import hungteen.craid.common.world.raid.HTRaidImpl;
import net.minecraft.world.level.Level;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2023-02-11 22:57
 **/
public class RaidWaveStartEventJS extends LevelEventJS {

    private final RaidEvent.RaidWaveStartEvent event;

    public RaidWaveStartEventJS(RaidEvent.RaidWaveStartEvent event) {
        this.event = event;
    }

    @Override
    public Level getLevel() {
        return event.getLevel();
    }

    public HTRaidImpl getRaid(){
        return event.getRaid();
    }

    public int getRound(){
        return event.getRound();
    }
}
