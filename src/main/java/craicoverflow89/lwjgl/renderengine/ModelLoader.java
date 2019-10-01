package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.models.RawModel;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class ModelLoader {

    private List<Integer> vaoList = new ArrayList();
    private List<Integer> vboList = new ArrayList();
    private List<Integer> textureList = new ArrayList();

    private void bindIndicesBuffer(int[] indices) {

        // Create VBO
        final int vboID = GL15.glGenBuffers();
        vboList.add(vboID);

        // Bind VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);

        // Store Data
        final IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    public void clean() {

        // Delete VAOs
        for(int vaoID : vaoList) GL30.glDeleteVertexArrays(vaoID);

        // Delete VBOs
        for(int vboID : vboList) GL15.glDeleteBuffers(vboID);

        // Delete Textures
        for(int textureID : textureList) GL11.glDeleteTextures(textureID);
    }

    private int createVAO() {

        // Create VAO
        final int vaoID = GL30.glGenVertexArrays();
        vaoList.add(vaoID);

        // Bind VAO
        GL30.glBindVertexArray(vaoID);

        // Return ID
        return vaoID;
    }

    public int loadTexture(String file) {

        // Load Texture
        int textureID = 0;
        try {
            final Texture texture = TextureLoader.getTexture("PNG", new FileInputStream(ModelLoader.class.getResource("/" + file + ".png").getFile()));
            textureID = texture.getTextureID();
            textureList.add(textureID);
        }

        // Texture Error
        catch(IOException ex) {ex.printStackTrace();}

        // Return ID
        return textureID;
    }

    public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices) {

        // Create VAO
        final int vaoID = createVAO();

        // Bind Indices
        bindIndicesBuffer(indices);

        // Store Data
        storeData(0, 3, positions);
        storeData(1, 2, textureCoords);
        unbindVAO();

        // Create Model
        return new RawModel(vaoID, indices.length);
    }

    private void storeData(int attributeNumber, int size, float[] data) {

        // Create VBO
        final int vboID = GL15.glGenBuffers();
        vboList.add(vboID);

        // Bind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

        // Store Data
        final FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Store VBO
        GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);

        // Unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {

        // Unbind Current
        GL30.glBindVertexArray(0);

    }

}