package hungteen.craid.common.world.raid;

import hungteen.craid.api.raid.RaidComponent;
import hungteen.craid.common.CRaidDummyEntities;
import hungteen.htlib.common.world.entity.DummyEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-03 21:43
 **/
public class DefaultRaid extends HTRaidImpl {

    protected final Set<UUID> defenders = new HashSet<>();

    public DefaultRaid(ServerLevel serverLevel, Vec3 position, RaidComponent raidComponent) {
        super(CRaidDummyEntities.DEFAULT_RAID, serverLevel, position, raidComponent);
    }

    public DefaultRaid(DummyEntityType<?> dummyEntityType, Level level, CompoundTag tag) {
        super(dummyEntityType, level, tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        {
            this.defenders.clear();
            if (tag.contains("Defenders", 9)) {
                ListTag listTag = tag.getList("Defenders", 11);
                for (int i = 0; i < listTag.size(); ++i) {
                    this.defenders.add(NbtUtils.loadUUID(listTag.get(i)));
                }
            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        {
            ListTag listTag = new ListTag();
            for (UUID uuid : this.defenders) {
                listTag.add(NbtUtils.createUUID(uuid));
            }
            tag.put("Defenders", listTag);
        }
        return super.save(tag);
    }

    /**
     * 出发某个条件会将实体标记为防御者。
     */
    public void addDefender(Entity entity) {
        this.defenders.add(entity.getUUID());
        this.setDirty();
    }

    @Override
    public List<Entity> getDefenders(){
        if(this.getLevel() instanceof ServerLevel){
            return this.defenders.stream().map(uuid -> {
                return ((ServerLevel) this.getLevel()).getEntity(uuid);
            }).filter(Objects::nonNull).toList();
        }
        return List.of();
    }

}
