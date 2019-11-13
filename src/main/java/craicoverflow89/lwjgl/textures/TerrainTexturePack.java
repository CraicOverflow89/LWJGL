package craicoverflow89.lwjgl.textures;

public final class TerrainTexturePack {

    private final TerrainTexture textureBackground, textureColourR, textureColourG, textureColourB;

    public TerrainTexturePack(TerrainTexture textureBackground, TerrainTexture textureColourR, TerrainTexture textureColourG, TerrainTexture textureColourB) {
        this.textureBackground = textureBackground;
        this.textureColourR = textureColourR;
        this.textureColourG = textureColourG;
        this.textureColourB = textureColourB;
    }

    public TerrainTexture getTextureBackground() {
        return textureBackground;
    }

    public TerrainTexture getTextureColourB() {
        return textureColourB;
    }

    public TerrainTexture getTextureColourG() {
        return textureColourG;
    }

    public TerrainTexture getTextureColourR() {
        return textureColourR;
    }

}