package hungteen.craid.common.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 22:32
 **/
public abstract class HTCapComponent {

    // Fabric CCA interface
    public abstract void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup);

    public abstract void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup);

    // NeoForge interface
    @NotNull
    public final CompoundTag serializeNBT(HolderLookup.Provider registryLookup) {
        var ret = new CompoundTag();
        writeToNbt(ret, registryLookup);
        return ret;
    }

    public final void deserializeNBT(HolderLookup.Provider registryLookup, @NotNull CompoundTag nbt) {
        readFromNbt(nbt, registryLookup);
    }

}
