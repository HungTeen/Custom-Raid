package hungteen.craid.api.event;

import hungteen.craid.api.raid.HTRaid;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.level.Level;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2023-02-11 23:16
 **/
public interface RaidEvent {

    Event<WaveStartCallback> WAVE_START = EventFactory.createArrayBacked(WaveStartCallback.class,
            (listeners) -> (raid, level, round) -> {
                for (var listener : listeners) {
                    listener.handle(raid, level, round);
                }
            });

    Event<WaveFinishCallback> WAVE_FINISH = EventFactory.createArrayBacked(WaveFinishCallback.class,
            (listeners) -> (raid, level, round) -> {
                for (var listener : listeners) {
                    listener.handle(raid, level, round);
                }
            });

    Event<RaidFinishCallback> RAID_FINISH = EventFactory.createArrayBacked(RaidFinishCallback.class,
            (listeners) -> (raid, level, victory) -> {
                for (var listener : listeners) {
                    listener.handle(raid, level, victory);
                }
            });

    interface WaveStartCallback {

        void handle(HTRaid raid, Level level, int round);
    }

    interface WaveFinishCallback {

        void handle(HTRaid raid, Level level, int round);
    }

    interface RaidFinishCallback {

        void handle(HTRaid raid, Level level, boolean victory);
    }
}
