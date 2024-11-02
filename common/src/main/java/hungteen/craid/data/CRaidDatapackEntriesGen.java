package hungteen.craid.data;

import hungteen.craid.common.codec.position.CRaidPositionComponents;
import hungteen.craid.common.codec.raid.CRaidRaidComponents;
import hungteen.craid.common.codec.result.CRaidResultComponents;
import hungteen.craid.common.codec.spawn.CRaidSpawnComponents;
import hungteen.craid.common.codec.wave.CRaidWaveComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-10-25 12:44
 **/
public class CRaidDatapackEntriesGen extends HTRegistriesDatapackGenerator {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(CRaidPositionComponents.registry().getRegistryKey(), CRaidPositionComponents::register)
            .add(CRaidResultComponents.registry().getRegistryKey(), CRaidResultComponents::register)
            .add(CRaidSpawnComponents.registry().getRegistryKey(), CRaidSpawnComponents::register)
            .add(CRaidWaveComponents.registry().getRegistryKey(), CRaidWaveComponents::register)
            .add(CRaidRaidComponents.registry().getRegistryKey(), CRaidRaidComponents::register)
            ;

    public CRaidDatapackEntriesGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER);
    }

}
