package hungteen.craid.api.event;

import hungteen.craid.api.raid.HTRaid;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2023-02-11 22:39
 **/
public abstract class RaidEvent extends Event {

    private final HTRaid raid;
    private final Level level;

    public RaidEvent(Level level, HTRaid raid) {
        this.level = level;
        this.raid = raid;
    }

    public HTRaid getRaid() {
        return raid;
    }

    public Level getLevel() {
        return level;
    }

    public static class RaidFinishEvent extends RaidEvent {

        private final boolean victory;

        public RaidFinishEvent(Level level, HTRaid raid, boolean victory) {
            super(level, raid);
            this.victory = victory;
        }

        public boolean isVictory() {
            return victory;
        }
    }

    public static class WaveStartEvent extends RaidEvent {

        private final int round;

        public WaveStartEvent(Level level, HTRaid raid, int round) {
            super(level, raid);
            this.round = round;
        }

        public int getRound() {
            return round;
        }
    }

    public static class WaveFinishEvent extends RaidEvent {

        private final int round;

        public WaveFinishEvent(Level level, HTRaid raid, int round) {
            super(level, raid);
            this.round = round;
        }

        public int getRound() {
            return round;
        }
    }

}
