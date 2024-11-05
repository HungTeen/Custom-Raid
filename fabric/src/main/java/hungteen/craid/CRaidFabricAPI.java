package hungteen.craid;

import hungteen.craid.api.capability.RaidCapability;
import hungteen.craid.api.event.RaidEvent;
import hungteen.craid.common.CRaidEntityComponents;
import hungteen.craid.common.world.raid.HTRaidImpl;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @program: HTLib
 * @author: PangTeen
 * @create: 2024/10/22 15:13
 **/
public class CRaidFabricAPI implements CRaidPlatformAPI {

    @Override
    public void postWaveStartEvent(Level level, HTRaidImpl raid, int currentWave) {
        RaidEvent.WAVE_START.invoker().handle(raid, level, currentWave);
    }

    @Override
    public void postWaveFinishEvent(Level level, HTRaidImpl raid, int currentWave) {
        RaidEvent.WAVE_FINISH.invoker().handle(raid, level, currentWave);
    }

    @Override
    public void postRaidFinishEvent(Level level, HTRaidImpl raid, boolean victory) {
        RaidEvent.RAID_FINISH.invoker().handle(raid, level, victory);
    }

    @Override
    public Optional<? extends RaidCapability> getRaidCap(Entity entity) {
        return Optional.of(CRaidEntityComponents.RAID.get(entity));
    }

    @Override
    public SpawnGroupData onFinalizeSpawn(Mob mob, ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        return mob.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }
}
