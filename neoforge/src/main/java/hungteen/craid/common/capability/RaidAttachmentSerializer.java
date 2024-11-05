package hungteen.craid.common.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import org.jetbrains.annotations.Nullable;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-01 09:51
 **/
public class RaidAttachmentSerializer implements IAttachmentSerializer<CompoundTag, RaidCapabilityImpl> {

    @Override
    public RaidCapabilityImpl read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
        RaidCapabilityImpl capability = new RaidCapabilityImpl();
        if (holder instanceof Entity entity) {
            capability.init(entity);
        } else {
            throw new IllegalArgumentException("Raid Cap only support entity");
        }
        capability.deserializeNBT(provider, tag);
        return capability;
    }

    @Override
    public @Nullable CompoundTag write(RaidCapabilityImpl capability, HolderLookup.Provider provider) {
        return capability.serializeNBT(provider);
    }

}


