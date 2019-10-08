package craicoverflow89.lwjgl.entities.light;

import craicoverflow89.lwjgl.helpers.Colour;
import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractLight {

    private Vector3f position;
    private Colour colour;
    private Vector3f attenuation;

    public AbstractLight(Vector3f position, Colour colour) {
        this.position = position;
        this.colour = colour;
        this.attenuation = new Vector3f(1f, 0f, 0f);
    }

    public AbstractLight(Vector3f position, Colour colour, Vector3f attenuation) {
        this.position = position;
        this.colour = colour;
        this.attenuation = attenuation;
    }

    public final Vector3f getAttenuation() {
        return attenuation;
    }

    public final Colour getColour() {
        return colour;
    }

    public final Vector3f getPosition() {
        return position;
    }

    public final void setColour(Colour colour) {
        this.colour = colour;
    }

    public final void setPosition(Vector3f position) {
        this.position = position;
    }

}