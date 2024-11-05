package hungteen.craid.common.attachment;

import hungteen.craid.api.CRaidAPI;
import hungteen.craid.common.capability.RaidAttachmentSerializer;
import hungteen.craid.common.capability.RaidCapabilityImpl;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

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
            } else {
                throw new IllegalArgumentException("Raid capability can only be attached to Entity");
            }
            return capability;
        }).serialize(new RaidAttachmentSerializer()).build();
    });
}
