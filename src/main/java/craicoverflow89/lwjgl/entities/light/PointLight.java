package craicoverflow89.lwjgl.entities.light;

import craicoverflow89.lwjgl.helpers.Colour;
import org.lwjgl.util.vector.Vector3f;

public final class PointLight extends AbstractLight {

    public PointLight(Vector3f position, Colour colour, Vector3f attenuation) {
        super(position, colour, attenuation);
    }

}