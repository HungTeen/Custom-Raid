package hungteen.craid.common.codec.raid;

import com.mojang.serialization.Codec;
import hungteen.craid.api.CRaidHelper;
import hungteen.craid.api.raid.*;
import hungteen.craid.common.CRaidSounds;
import hungteen.craid.common.codec.result.CRaidResultComponents;
import hungteen.craid.common.codec.wave.CRaidWaveComponents;
import hungteen.craid.common.world.raid.HTRaidImpl;
import hungteen.htlib.api.registry.HTCodecRegistry;
import hungteen.htlib.api.registry.HTHolder;
import hungteen.htlib.common.impl.registry.HTRegistryManager;
import hungteen.htlib.util.helper.ColorHelper;
import hungteen.htlib.util.helper.impl.SoundHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/18 10:37
 */
public interface CRaidRaidComponents {

    /**
     * Require Cache for command suggestions.
     */
    HTCodecRegistry<RaidComponent> RAIDS = HTRegistryManager.codec(CRaidHelper.get().prefix("raid"), CRaidRaidComponents::getDirectCodec, true);

    ResourceKey<RaidComponent> TEST = create("test");
    ResourceKey<RaidComponent> COMMON = create("common");

    static void register(BootstrapContext<RaidComponent> context) {
        final HolderGetter<ResultComponent> results = CRaidResultComponents.registry().helper().lookup(context);
        final HolderGetter<WaveComponent> waves = CRaidWaveComponents.registry().helper().lookup(context);
        final HolderGetter<SoundEvent> sounds = SoundHelper.get().lookup(context);
        var PVZ_PREPARE = holderOpt(sounds, CRaidSounds.PREPARE);
        var PVZ_WAVE = holderOpt(sounds, CRaidSounds.HUGE_WAVE);
        var PVZ_VICTORY = holderOpt(sounds, CRaidSounds.VICTORY);
        var PVZ_LOSS = holderOpt(sounds, CRaidSounds.LOSS);
        context.register(TEST, new CommonRaid(
                builder()
                        .blockInside(false)
                        .blockOutside(false)
                        .renderBorder(false)
                        .victoryResult(results.getOrThrow(CRaidResultComponents.TEST))
                        .color(BossEvent.BossBarColor.BLUE)
                        .raidSound(PVZ_PREPARE)
                        .waveSound(PVZ_WAVE)
                        .victorySound(PVZ_VICTORY)
                        .lossSound(PVZ_LOSS)
                        .build(),
                Arrays.asList(
                        waves.getOrThrow(CRaidWaveComponents.TEST_1),
                        waves.getOrThrow(CRaidWaveComponents.TEST_2)
                )
        ));
        context.register(COMMON, new CommonRaid(
                builder()
                        .blockInside(true)
                        .blockOutside(true)
                        .renderBorder(true)
                        .victoryResult(results.getOrThrow(CRaidResultComponents.COMMON_FUNCTION))
                        .victoryResult(results.getOrThrow(CRaidResultComponents.GIVE_APPLE_COMMAND))
                        .color(BossEvent.BossBarColor.RED)
                        .raidSound(PVZ_PREPARE)
                        .waveSound(PVZ_WAVE)
                        .victorySound(PVZ_VICTORY)
                        .lossSound(PVZ_LOSS)
                        .build(),
                Arrays.asList(
                        waves.getOrThrow(CRaidWaveComponents.COMMON_WAVE_1),
                        waves.getOrThrow(CRaidWaveComponents.COMMON_WAVE_2),
                        waves.getOrThrow(CRaidWaveComponents.COMMON_WAVE_3)
                )
        ));
    }

    static Codec<RaidComponent> getDirectCodec() {
        return CRaidRaidTypes.registry().byNameCodec().dispatch(RaidComponent::getType, RaidType::codec);
    }

    static Codec<Holder<RaidComponent>> getCodec() {
        return registry().getHolderCodec(getDirectCodec());
    }

    static ResourceKey<RaidComponent> create(String name) {
        return registry().createKey(CRaidHelper.prefix(name));
    }

    static HTCodecRegistry<RaidComponent> registry() {
        return RAIDS;
    }

    static RaidSettingBuilder builder() {
        return new RaidSettingBuilder();
    }

    static Optional<Holder<SoundEvent>> holderOpt(HolderGetter<SoundEvent> sounds, HTHolder<SoundEvent> holder) {
//        return SoundHelper.get().getRegistry().getHolder(SoundHelper.get().createKey(holder.getRegistryName())).map(l -> (Holder<SoundEvent>) l);
        return sounds.get(SoundHelper.get().createKey(holder.getRegistryName())).map(l -> (Holder<SoundEvent>) l);
    }

    class RaidSettingBuilder {
        private Holder<PositionComponent> positionComponent;
        private final List<Holder<ResultComponent>> victoryResults = new ArrayList<>();
        private final List<Holder<ResultComponent>> lossResults = new ArrayList<>();
        private double raidRange = 40;
        private boolean blockInside = false;
        private boolean blockOutside = false;
        private boolean renderBorder = false;
        private int borderColor = ColorHelper.BORDER_AQUA.rgb();
        private int victoryDuration = 100;
        private int lossDuration = 100;
        private boolean showRoundTitle = true;
        private boolean sendRaidWarn = true;
        private MutableComponent raidTitle = HTRaidImpl.RAID_TITLE;
        private BossEvent.BossBarColor raidColor = BossEvent.BossBarColor.RED;
        private MutableComponent victoryTitle = HTRaidImpl.RAID_VICTORY_TITLE;
        private MutableComponent lossTitle = HTRaidImpl.RAID_LOSS_TITLE;
        private Optional<Holder<SoundEvent>> raidStartSound = Optional.empty();
        private Optional<Holder<SoundEvent>> waveStartSound = Optional.empty();
        private Optional<Holder<SoundEvent>> victorySound = Optional.empty();
        private Optional<Holder<SoundEvent>> lossSound = Optional.empty();

        public RaidSettingBuilder place(Holder<PositionComponent> positionComponent) {
            this.positionComponent = positionComponent;
            return this;
        }

        public RaidSettingBuilder victoryResult(Holder<ResultComponent> resultComponent) {
            this.victoryResults.add(resultComponent);
            return this;
        }

        public RaidSettingBuilder lossResult(Holder<ResultComponent> resultComponent) {
            this.lossResults.add(resultComponent);
            return this;
        }

        public RaidSettingBuilder range(int raidRange) {
            this.raidRange = raidRange;
            return this;
        }

        public RaidSettingBuilder blockInside(boolean block) {
            this.blockInside = block;
            return this;
        }

        public RaidSettingBuilder blockOutside(boolean block) {
            this.blockOutside = block;
            return this;
        }

        public RaidSettingBuilder renderBorder(boolean render) {
            this.renderBorder = render;
            return this;
        }

        public RaidSettingBuilder borderColor(int color) {
            this.borderColor = color;
            return this;
        }

        public RaidSettingBuilder victoryDuration(int victoryDuration) {
            this.victoryDuration = victoryDuration;
            return this;
        }

        public RaidSettingBuilder lossDuration(int lossDuration) {
            this.lossDuration = lossDuration;
            return this;
        }

        public RaidSettingBuilder showRoundTitle(boolean showRoundTitle) {
            this.showRoundTitle = showRoundTitle;
            return this;
        }

        public RaidSettingBuilder sendRaidWarn(boolean sendRaidWarn) {
            this.sendRaidWarn = sendRaidWarn;
            return this;
        }

        public RaidSettingBuilder color(BossEvent.BossBarColor color) {
            this.raidColor = color;
            return this;
        }

        public RaidSettingBuilder title(MutableComponent title) {
            this.raidTitle = title;
            return this;
        }

        public RaidSettingBuilder victoryTitle(MutableComponent title) {
            this.victoryTitle = title;
            return this;
        }

        public RaidSettingBuilder lossTitle(MutableComponent title) {
            this.lossTitle = title;
            return this;
        }

        public RaidSettingBuilder raidSound(Optional<Holder<SoundEvent>> soundEvent) {
            this.raidStartSound = soundEvent;
            return this;
        }

        public RaidSettingBuilder waveSound(Optional<Holder<SoundEvent>> soundEvent) {
            this.waveStartSound = soundEvent;
            return this;
        }

        public RaidSettingBuilder victorySound(Optional<Holder<SoundEvent>> soundEvent) {
            this.victorySound = soundEvent;
            return this;
        }

        public RaidSettingBuilder lossSound(Optional<Holder<SoundEvent>> soundEvent) {
            this.lossSound = soundEvent;
            return this;
        }

        public RaidComponentImpl.RaidSetting build() {
            return new RaidComponentImpl.RaidSetting(
                    Optional.ofNullable(this.positionComponent),
                    new RaidComponentImpl.BorderSetting(
                            this.raidRange,
                            this.blockInside,
                            this.blockOutside,
                            this.renderBorder,
                            this.borderColor
                    ),
                    new RaidComponentImpl.BarSetting(
                            this.raidTitle,
                            this.raidColor,
                            this.victoryTitle,
                            this.lossTitle
                    ),
                    new RaidComponentImpl.SoundSetting(
                            this.raidStartSound,
                            this.waveStartSound,
                            this.victorySound,
                            this.lossSound
                    ),
                    this.victoryResults,
                    this.lossResults,
                    this.victoryDuration,
                    this.lossDuration,
                    this.showRoundTitle,
                    this.sendRaidWarn
            );
        }

    }

}
