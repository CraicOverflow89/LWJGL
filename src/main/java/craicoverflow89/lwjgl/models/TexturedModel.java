package craicoverflow89.lwjgl.models;

import craicoverflow89.lwjgl.textures.ModelTexture;

public final class TexturedModel {

    private final RawModel model;
    private final ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.model = model;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return model;
    }

    public ModelTexture getTexture() {
        return texture;
    }

}