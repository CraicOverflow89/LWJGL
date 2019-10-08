package craicoverflow89.lwjgl.textures;

import org.lwjgl.util.vector.Vector2f;

public final class GUITexture extends AbstractTexture {

    private final Vector2f position;
    private final Vector2f scale;

    public GUITexture(int textureID, Vector2f position, Vector2f scale) {
        super(textureID);
        this.position = position;
        this.scale = scale;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getScale() {
        return this.scale;
    }

}