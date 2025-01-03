package hungteen.craid.common.codec.result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hungteen.craid.api.raid.HTRaid;
import hungteen.craid.api.raid.ResultComponent;
import hungteen.craid.api.raid.ResultType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * @program: HTLib
 * @author: HungTeen
 * @create: 2022-12-03 22:43
 **/
public record ItemStackResult(boolean forDefender, boolean forRaider, List<ItemStack> rewards) implements ResultComponent {

    public static final MapCodec<ItemStackResult> CODEC = RecordCodecBuilder.<ItemStackResult>mapCodec(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("for_defender", true).forGetter(ItemStackResult::forDefender),
            Codec.BOOL.optionalFieldOf("for_raider", false).forGetter(ItemStackResult::forRaider),
            ItemStack.CODEC.listOf().fieldOf("rewards").forGetter(ItemStackResult::rewards)
    ).apply(instance, ItemStackResult::new));

    @Override
    public void apply(HTRaid raid, ServerLevel level, int tick) {

    }

    @Override
    public void applyToDefender(HTRaid raid, Entity defender, int tick) {
        if(forDefender() && tick == 0 && defender instanceof Player){
            this.giveRewardTo((Player) defender);
        }
    }

    @Override
    public void applyToRaider(HTRaid raid, Entity raider, int tick) {
        if(forRaider() && tick == 0 && raider instanceof Player){
            this.giveRewardTo((Player) raider);
        }
    }

    private void giveRewardTo(Player player){
        rewards().forEach(itemStack -> {
            ItemStack stack = itemStack.copy();
            if (player.addItem(stack)) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            } else {
                ItemEntity itementity = player.drop(stack, false);
                if (itementity != null) {
                    itementity.setNoPickUpDelay();
                    itementity.setThrower(player);
                }
            }
        });
    }

    @Override
    public ResultType<?> getType() {
        return CRaidResultTypes.ITEM_STACK;
    }
}
