package craicoverflow89.lwjgl.textures;

public abstract class AbstractTexture {

    private final int textureID;

    public AbstractTexture(int textureID) {
        this.textureID = textureID;
    }

    public final int getTextureID() {
        return textureID;
    }

}