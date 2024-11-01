package hungteen.craid.common.capability;

import hungteen.craid.api.capability.RaidCapability;
import hungteen.craid.api.raid.HTRaid;
import hungteen.htlib.common.world.entity.DummyEntityManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-01 09:48
 **/
public class RaidCapabilityImpl extends HTCapComponent implements RaidCapability {

    private Entity entity;
    private HTRaid raid = null;
    private int wave = 0;

    public void init(Entity entity){
        this.entity = entity;
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        if(tag.contains("CurrentWave")){
            this.wave = tag.getInt("CurrentWave");
        }
        if(entity != null && tag.contains("RaidID")){
            if (entity.level() instanceof ServerLevel serverLevel) {
                DummyEntityManager.getDummyEntity(serverLevel, tag.getInt("RaidID")).ifPresent(dummyEntity -> {
                    if(dummyEntity instanceof HTRaid){
                        this.raid = (HTRaid)dummyEntity;
                    }
                });
            }

            if (this.raid != null) {
                this.raid.addRaider(entity);
//                if (this.isPatrolLeader()) {
//                    this.raid.setLeader(this.wave, this);
//                }
            }
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        tag.putInt("CurrentWave", this.wave);
        if(raid != null){
            tag.putInt("RaidID", this.raid.getEntityID());
        }
    }

    @Override
    public boolean isRaider() {
        return this.getRaid() != null;
    }

    @Override
    public void setRaid(HTRaid raid) {
        this.raid = raid;
    }

    @Override
    public HTRaid getRaid() {
        return this.raid;
    }

    @Override
    public void setWave(int wave) {
        this.wave = wave;
    }

    @Override
    public int getWave() {
        return this.wave;
    }
}
