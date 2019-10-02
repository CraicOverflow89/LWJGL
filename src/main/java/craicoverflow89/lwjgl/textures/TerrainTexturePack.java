package craicoverflow89.lwjgl.textures;

public final class TerrainTexturePack {

    private final TerrainTexture textureBackground;
    private final TerrainTexture textureColourR;
    private final TerrainTexture textureColourG;
    private final TerrainTexture textureColourB;

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