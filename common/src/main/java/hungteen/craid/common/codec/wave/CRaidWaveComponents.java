package hungteen.craid.common.codec.wave;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import hungteen.craid.api.CRaidHelper;
import hungteen.craid.api.raid.PositionComponent;
import hungteen.craid.api.raid.SpawnComponent;
import hungteen.craid.api.raid.WaveComponent;
import hungteen.craid.api.raid.WaveType;
import hungteen.craid.common.codec.position.CRaidPositionComponents;
import hungteen.craid.common.codec.spawn.CRaidSpawnComponents;
import hungteen.htlib.api.registry.HTCodecRegistry;
import hungteen.htlib.common.impl.registry.HTRegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.ConstantInt;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-11-28 23:32
 **/
public interface CRaidWaveComponents {

    HTCodecRegistry<WaveComponent> WAVES = HTRegistryManager.codec(CRaidHelper.prefix("wave"), CRaidWaveComponents::getDirectCodec);

    ResourceKey<WaveComponent> TEST_1 = create("test_1");
    ResourceKey<WaveComponent> TEST_2 = create("test_2");
    ResourceKey<WaveComponent> COMMON_WAVE_1 = create("common_wave_1");
    ResourceKey<WaveComponent> COMMON_WAVE_2 = create("common_wave_2");
    ResourceKey<WaveComponent> COMMON_WAVE_3 = create("common_wave_3");

    static void register(BootstrapContext<WaveComponent> context) {
        final HolderGetter<SpawnComponent> spawns = CRaidSpawnComponents.registry().helper().lookup(context);
        final HolderGetter<PositionComponent> positions = CRaidPositionComponents.registry().helper().lookup(context);
        final Holder<SpawnComponent> creeperSpawns = spawns.getOrThrow(CRaidSpawnComponents.CREEPER_4_8);
        final Holder<SpawnComponent> poweredCreeperSpawns = spawns.getOrThrow(CRaidSpawnComponents.POWERED_CREEPER_3_5);
        final Holder<SpawnComponent> spiderSpawns = spawns.getOrThrow(CRaidSpawnComponents.SPIDER_5);
        final Holder<SpawnComponent> skeletonSpawns = spawns.getOrThrow(CRaidSpawnComponents.LONG_TERM_SKELETON);
        final Holder<SpawnComponent> witherSkeletonSpawns = spawns.getOrThrow(CRaidSpawnComponents.WITHER_SKELETON);
        final Holder<SpawnComponent> diamondZombieSpawns = spawns.getOrThrow(CRaidSpawnComponents.DIAMOND_ZOMBIE_3_6);
        context.register(TEST_1, new CommonWave(
                CRaidWaveComponents.builder().prepare(100).wave(800).skip(false)
                        .placement(positions.getOrThrow(CRaidPositionComponents.TEST)).build(),
                List.of(Pair.of(ConstantInt.of(10), creeperSpawns))
        ));
        context.register(TEST_2, new CommonWave(
                CRaidWaveComponents.builder().prepare(100).wave(800).skip(false)
                        .placement(positions.getOrThrow(CRaidPositionComponents.TEST)).build(),
                List.of(
                        Pair.of(ConstantInt.of(10), spiderSpawns),
                        Pair.of(ConstantInt.of(100), skeletonSpawns)
                )
        ));
        context.register(COMMON_WAVE_1, new CommonWave(
                CRaidWaveComponents.builder().prepare(60).wave(600).skip(true)
                        .placement(positions.getOrThrow(CRaidPositionComponents.COMMON)).build(),
                List.of(
                        Pair.of(ConstantInt.of(10), poweredCreeperSpawns)
                )
        ));
        context.register(COMMON_WAVE_2, new CommonWave(
                CRaidWaveComponents.builder().prepare(60).wave(1200).skip(true)
                        .placement(positions.getOrThrow(CRaidPositionComponents.COMMON)).build(),
                List.of(
                        Pair.of(ConstantInt.of(100), skeletonSpawns),
                        Pair.of(ConstantInt.of(300), witherSkeletonSpawns),
                        Pair.of(ConstantInt.of(600), witherSkeletonSpawns)
                )
        ));
        context.register(COMMON_WAVE_3, new CommonWave(
                CRaidWaveComponents.builder().prepare(60).wave(1800).skip(true)
                        .placement(positions.getOrThrow(CRaidPositionComponents.COMMON)).build(),
                List.of(
                        Pair.of(ConstantInt.of(50), witherSkeletonSpawns),
                        Pair.of(ConstantInt.of(200), diamondZombieSpawns),
                        Pair.of(ConstantInt.of(500), creeperSpawns)
                )
        ));
    }

    static Codec<WaveComponent> getDirectCodec(){
        return CRaidWaveTypes.registry().byNameCodec().dispatch(WaveComponent::getType, WaveType::codec);
    }

    static Codec<Holder<WaveComponent>> getCodec(){
        return registry().getHolderCodec(getDirectCodec());
    }

    static WaveSettingBuilder builder(){
        return new WaveSettingBuilder();
    }

    static ResourceKey<WaveComponent> create(String name) {
        return registry().createKey(CRaidHelper.prefix(name));
    }

    static HTCodecRegistry<WaveComponent> registry() {
        return WAVES;
    }

    class WaveSettingBuilder {

        private Optional<Holder<PositionComponent>> spawnPlacement = Optional.empty();
        private int prepareDuration = 100;
        private int waveDuration = 0;
        private boolean canSkip = true;
        private Optional<Holder<SoundEvent>> waveStartSound = Optional.empty();

        public WaveComponentImpl.WaveSetting build(){
            return new WaveComponentImpl.WaveSetting(spawnPlacement, prepareDuration, waveDuration, canSkip, waveStartSound);
        }

        public WaveSettingBuilder placement(@NotNull Holder<PositionComponent> spawnPlacement){
            this.spawnPlacement = Optional.of(spawnPlacement);
            return this;
        }

        public WaveSettingBuilder prepare(int duration){
            this.prepareDuration = duration;
            return this;
        }

        public WaveSettingBuilder wave(int duration){
            this.waveDuration = duration;
            return this;
        }

        public WaveSettingBuilder skip(boolean skip){
            this.canSkip = skip;
            return this;
        }

        public WaveSettingBuilder waveStart(SoundEvent soundEvent){
            this.waveStartSound = Optional.of(Holder.direct(soundEvent));
            return this;
        }

    }

}
