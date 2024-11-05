package hungteen.craid;

import hungteen.craid.api.capability.RaidCapability;
import hungteen.craid.api.event.RaidEvent;
import hungteen.craid.common.capability.raid.ForgeRaidCapabilityImpl;
import hungteen.craid.common.world.raid.HTRaidImpl;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @program: HTLib
 * @author: PangTeen
 * @create: 2024/10/22 15:13
 **/
public class CRaidForgeAPI implements CRaidPlatformAPI {

    @Override
    public void postWaveStartEvent(Level level, HTRaidImpl raid, int currentWave) {
        MinecraftForge.EVENT_BUS.post(new RaidEvent.WaveStartEvent(level, raid, currentWave));
    }

    @Override
    public void postWaveFinishEvent(Level level, HTRaidImpl raid, int currentWave) {
        MinecraftForge.EVENT_BUS.post(new RaidEvent.WaveFinishEvent(level, raid, currentWave));
    }

    @Override
    public void postRaidFinishEvent(Level level, HTRaidImpl raid, boolean victory) {
        MinecraftForge.EVENT_BUS.post(new RaidEvent.RaidFinishEvent(level, raid, victory));
    }

    @Override
    public Optional<? extends RaidCapability> getRaidCap(Entity entity) {
        return ForgeRaidCapabilityImpl.getRaid(entity);
    }

    @Override
    public SpawnGroupData onFinalizeSpawn(Mob mob, ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        return ForgeEventFactory.onFinalizeSpawn(mob, level, difficulty, spawnType, spawnData);
    }
}
