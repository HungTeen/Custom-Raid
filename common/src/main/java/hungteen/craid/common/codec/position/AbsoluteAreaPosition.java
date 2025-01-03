package hungteen.craid.common.codec.position;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hungteen.craid.api.raid.PositionType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

/**
 * @author PangTeen
 * @program HTLib
 * @data 2022/11/18 10:38
 */
public class AbsoluteAreaPosition extends PositionComponentImpl {

    /**
     * getPosition: 绝对坐标。absolute coordinates.
     * excludeRadius: 排除半径，此半径之内不考虑。points in the circle with this radius will be excluded to place.
     * radius: 放置半径。points in the circle with this radius but not in excludeRadius can be placed.
     * isCircle: 默认是圆心，否则是方形。default is circle, or it will be square.
     */
    public static final MapCodec<AbsoluteAreaPosition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Vec3.CODEC.fieldOf("position").forGetter(AbsoluteAreaPosition::getPosition),
            Codec.doubleRange(0D, Double.MAX_VALUE).optionalFieldOf("exclude_radius", 0D).forGetter(AbsoluteAreaPosition::getExcludeRadius),
            Codec.doubleRange(0D, Double.MAX_VALUE).optionalFieldOf("radius", 0D).forGetter(AbsoluteAreaPosition::getRadius),
            Codec.BOOL.optionalFieldOf("is_circle", true).forGetter(AbsoluteAreaPosition::isCircle)
    ).apply(instance, AbsoluteAreaPosition::new));
    private final Vec3 position;

    public AbsoluteAreaPosition(Vec3 position, double excludeRadius, double radius, boolean isCircle) {
        super(excludeRadius, radius, isCircle);
        this.position = position;
    }

    @Override
    public Vec3 getPlacePosition(ServerLevel world, Vec3 origin) {
        if (this.canSpawn()) {
            final Vec3 offset = this.getOffset(world.getRandom());
            return this.getPosition().add(offset);
        }
        return origin;
    }

    @Override
    public PositionType<?> getType() {
        return CRaidPositionTypes.ABSOLUTE_AREA;
    }

    public Vec3 getPosition() {
        return position;
    }

}
