package hungteen.craid.api.raid;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;

import java.util.List;
import java.util.Optional;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/18 10:02
 */
public interface RaidComponent {

    /**
     * Get how many waves are there. TODO change by difficulty ?
     * @param raid Current raid.
     * @return Wave count.
     */
    int getWaveCount(HTRaid raid);

    /**
     * Get current wave.
     * @param raid Current raid.
     * @param currentWave Current wave.
     * @return Wave component.
     */
    WaveComponent getCurrentWave(HTRaid raid, int currentWave);

    /**
     * Determines the wave getSpawnEntities placement entityType.
     *
     * @return The least high priority getSpawnEntities placement.
     */
    Optional<PositionComponent> getSpawnPlacement();

    /**
     * Get the raid bar title.
     * @return Raid bar title.
     */
    MutableComponent getRaidTitle();

    /**
     * Get the raid bar color.
     * @return Color.
     */
    BossEvent.BossBarColor getBarColor();

    /**
     * Get the bar title when defeated the raid.
     * @return Victory raid title.
     */
    MutableComponent getVictoryTitle();

    /**
     * Get the bar title when lost the raid.
     * @return Loss raid title.
     */
    MutableComponent getLossTitle();

    /**
     * How long will the raid last when victory.
     * @return Last time.
     */
    int getVictoryDuration();

    /**
     * How long will the raid last when lost.
     * @return Last time.
     */
    int getLossDuration();

    /**
     * The range of the raid.
     * @return Raid range.
     */
    double getRaidRange();

    /**
     * Can inside entities go out.
     * @return true if they can not.
     */
    boolean blockInside();

    /**
     * Can outside entities go into.
     * @return true if they can not.
     */
    boolean blockOutside();

    /**
     * Whether render the border of the raid.
     * @return true to render.
     */
    boolean renderBorder();

    /**
     * Get the color of the border.
     * @return RGB color.
     */
    int getBorderColor();

    /**
     * Should display the round message or not.
     * @return true to display.
     */
    boolean showRoundTitle();

    /**
     * Should send raid warn when stopped.
     * @return true to send.
     */
    boolean sendRaidWarn();

    /**
     * Get victory result components.
     * @return Result list.
     */
    List<ResultComponent> getVictoryResults();

    /**
     * Get loss result components.
     * @return Result list.
     */
    List<ResultComponent> getLossResults();

    /**
     * Get the sound when raid start.
     * @return empty if no sound.
     */
    Optional<Holder<SoundEvent>> getRaidStartSound();

    /**
     * Get the sound when wave start.
     * @return empty if no sound.
     */
    Optional<Holder<SoundEvent>> getWaveStartSound();

    /**
     * Get the sound when victory.
     * @return empty if no sound.
     */
    Optional<Holder<SoundEvent>> getVictorySound();

    /**
     * Get the sound when loss.
     * @return empty if no sound.
     */
    Optional<Holder<SoundEvent>> getLossSound();

    /**
     * Get the entityType of raid.
     * @return Raid entityType.
     */
    RaidType<?> getType();
}
