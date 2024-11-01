package hungteen.craid.api.capability;

import hungteen.craid.api.CRaidHelper;
import hungteen.craid.api.raid.HTRaid;
import net.minecraft.resources.ResourceLocation;

/**
 * 将实体加入到袭击中，用来判断实体是否属于某个袭击。
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-01 09:47
 **/
public interface RaidCapability {

    ResourceLocation ID = CRaidHelper.prefix("raid");

    boolean isRaider();

    void setRaid(HTRaid raid);

    HTRaid getRaid();

    void setWave(int wave);

    int getWave();
}
