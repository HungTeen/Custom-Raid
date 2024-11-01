package hungteen.craid.compat.kubejs.event;

import dev.latvian.mods.kubejs.entity.EntityEventJS;
import hungteen.craid.common.event.events.RaidEvent;
import hungteen.craid.common.world.raid.HTRaidImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2023-02-12 22:34
 **/
public class RaidResultRaiderEventJS extends EntityEventJS {

    private final RaidEvent.RaidResultRaiderEvent event;

    public RaidResultRaiderEventJS(RaidEvent.RaidResultRaiderEvent event) {
        this.event = event;
    }

    @Override
    public Entity getEntity() {
        return event.getEntity();
    }

    public int getTick(){
        return event.getTick();
    }

    public ResourceLocation getId(){
        return event.getId();
    }

    public HTRaidImpl getRaid(){
        return event.getRaid();
    }
}
