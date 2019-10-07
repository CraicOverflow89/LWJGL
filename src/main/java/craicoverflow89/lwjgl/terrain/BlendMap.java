package craicoverflow89.lwjgl.terrain;

import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.textures.TerrainTexture;

public final class BlendMap {

    private final TerrainTexture texture;

    public BlendMap(ModelLoader loader, String file) {
        texture = new TerrainTexture(loader.loadTexture("terrain/blend/test"));
        // NOTE: still using TerrainTexture, as it's far easier, for now
    }

    public TerrainTexture getTexture() {
        return texture;
    }

}