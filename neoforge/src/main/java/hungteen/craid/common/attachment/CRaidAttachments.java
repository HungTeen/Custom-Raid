package hungteen.craid.common.attachment;

import hungteen.craid.api.CRaidAPI;
import hungteen.craid.common.capability.RaidCapabilityImpl;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

/**
 * @program: CustomRaid
 * @author: PangTeen
 * @create: 2024/11/1 21:30
 **/
public interface CRaidAttachments {

    DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CRaidAPI.id());

    DeferredHolder<AttachmentType<?>, AttachmentType<RaidCapabilityImpl>> RAID = ATTACHMENTS.register("raid", () -> {
        return AttachmentType.builder(holder -> {
            RaidCapabilityImpl capability = new RaidCapabilityImpl();
            if(holder instanceof Entity entity){
                capability.init(entity);
            }
            return capability;
        }).serialize(new IAttachmentSerializer<CompoundTag, RaidCapabilityImpl>() {
            @Override
            public RaidCapabilityImpl read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
                RaidCapabilityImpl capability = new RaidCapabilityImpl();
                if(holder instanceof Entity entity){
                    capability.init(entity);
                }
                capability.deserializeNBT(provider, tag);
                return capability;
            }

            @Override
            public @Nullable CompoundTag write(RaidCapabilityImpl capability, HolderLookup.Provider provider) {
                return capability.serializeNBT(provider);
            }
        }).build();
    });
}
