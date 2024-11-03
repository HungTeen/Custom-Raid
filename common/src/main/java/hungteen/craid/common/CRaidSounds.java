package hungteen.craid.common;

import hungteen.craid.api.CRaidAPI;
import hungteen.htlib.api.registry.HTHolder;
import hungteen.htlib.common.impl.registry.HTRegistryManager;
import hungteen.htlib.common.impl.registry.HTVanillaRegistry;
import hungteen.htlib.util.helper.impl.HTLibHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-02 09:19
 **/
public interface CRaidSounds {

    HTVanillaRegistry<SoundEvent> SOUNDS = HTRegistryManager.vanilla(Registries.SOUND_EVENT, CRaidAPI.id());

    HTHolder<SoundEvent> PREPARE = register("prepare");
    HTHolder<SoundEvent> HUGE_WAVE = register("huge_wave");
    HTHolder<SoundEvent> FINAL_WAVE = register("final_wave");
    HTHolder<SoundEvent> VICTORY = register("victory");
    HTHolder<SoundEvent> LOSS = register("loss");
    HTHolder<SoundEvent> REWARD = register("reward");
    HTHolder<SoundEvent> FINAL_VICTORY = register("final_victory");

    private static HTHolder<SoundEvent> register(String name){
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(HTLibHelper.prefix(name)));
    }

    private static HTHolder<SoundEvent> register(String name, float range){
        return SOUNDS.register(name, () -> SoundEvent.createFixedRangeEvent(HTLibHelper.prefix(name), range));
    }

    static HTVanillaRegistry<SoundEvent> registry(){
        return SOUNDS;
    }
}
