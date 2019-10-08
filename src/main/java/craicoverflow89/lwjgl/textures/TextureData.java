package craicoverflow89.lwjgl.textures;

import java.nio.ByteBuffer;

public final class TextureData {

    private final ByteBuffer buffer;
    private final int width, height;

    public TextureData(ByteBuffer buffer, int width, int height) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}