package craicoverflow89.lwjgl.entities;

import craicoverflow89.lwjgl.helpers.Colour;
import org.lwjgl.util.vector.Vector3f;

public final class Light {

    private Vector3f position;
    private Colour colour;

    public Light(Vector3f position, Colour colour) {
        this.position = position;
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

}