package hungteen.craid.common.capability.raid;

import hungteen.craid.api.CRaidForgeCaps;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-01 09:51
 **/
public class ForgeRaidCapProvider implements ICapabilitySerializable<CompoundTag> {

    private ForgeRaidCapabilityImpl raidCapability;
    private final LazyOptional<ForgeRaidCapabilityImpl> raiderCapOpt = LazyOptional.of(this::create);

    public ForgeRaidCapProvider(Entity raider){
        this.raiderCapOpt.ifPresent(cap -> cap.init(raider));
    }

    private @NotNull ForgeRaidCapabilityImpl create(){
        if(raidCapability == null){
            raidCapability = new ForgeRaidCapabilityImpl();
        }
        return raidCapability;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return this.getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == CRaidForgeCaps.RAID_CAP){
            return raiderCapOpt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider registryAccess) {
        return raidCapability.serializeNBT(registryAccess);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider registryAccess, CompoundTag nbt) {
        raidCapability.deserializeNBT(registryAccess, nbt);
    }
}
