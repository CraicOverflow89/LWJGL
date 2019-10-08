package craicoverflow89.lwjgl.entities.light;

import craicoverflow89.lwjgl.helpers.Colour;
import org.lwjgl.util.vector.Vector3f;

public final class GlobalLight extends AbstractLight {

    public GlobalLight(Vector3f position, Colour colour) {
        super(position, colour);
    }

}