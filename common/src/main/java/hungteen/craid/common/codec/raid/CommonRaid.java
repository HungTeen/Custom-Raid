package hungteen.craid.common.codec.raid;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hungteen.craid.api.raid.HTRaid;
import hungteen.craid.api.raid.RaidType;
import hungteen.craid.api.raid.WaveComponent;
import hungteen.craid.common.codec.wave.CRaidWaveComponents;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-11-28 23:37
 **/
public class CommonRaid extends RaidComponentImpl {

    public static final MapCodec<CommonRaid> CODEC = RecordCodecBuilder.<CommonRaid>mapCodec(instance -> instance.group(
            RaidSetting.CODEC.fieldOf("setting").forGetter(CommonRaid::getRaidSettings),
            CRaidWaveComponents.getCodec().listOf().fieldOf("waves").forGetter(CommonRaid::getWaveComponents)
    ).apply(instance, CommonRaid::new));
    private final List<Holder<WaveComponent>> waveComponents;

    public CommonRaid(RaidSetting raidSettings, List<Holder<WaveComponent>> waveComponents) {
        super(raidSettings);
        this.waveComponents = waveComponents;
    }

    @Override
    public int getWaveCount(HTRaid raid) {
        return this.waveComponents.size();
    }

    @Override
    public @NotNull WaveComponent getCurrentWave(HTRaid raid, int currentWave) {
        return this.waveComponents.get(currentWave).value();
    }

    public List<Holder<WaveComponent>> getWaveComponents(){
        return this.waveComponents;
    }

    @Override
    public RaidType<?> getType() {
        return CRaidRaidTypes.COMMON;
    }
}
