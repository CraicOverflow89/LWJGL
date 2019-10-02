package craicoverflow89.lwjgl.helpers;

import org.lwjgl.util.vector.Vector3f;

public final class Colour {

    public final float r, g, b;

    public Colour(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Vector3f asVector3f() {
        return new Vector3f(r, g, b);
    }

}