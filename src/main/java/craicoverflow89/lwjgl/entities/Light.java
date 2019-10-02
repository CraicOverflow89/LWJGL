package craicoverflow89.lwjgl.entities;

import org.lwjgl.util.vector.Vector3f;

public final class Light {

    private Vector3f position, colour;

    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
    }

    public Vector3f getColour() {
        return colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

}