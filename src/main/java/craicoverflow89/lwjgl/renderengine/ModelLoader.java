package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.helpers.ArrayIndexed;
import craicoverflow89.lwjgl.helpers.Pair;
import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.textures.TextureData;
import de.matthiasmann.twl.utils.PNGDecoder;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public final class ModelLoader {

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

    private TextureData decodeTextureFile(String file) {

        // Load Texture
        ByteBuffer buffer = null;
        int width = 0;
        int height = 0;
        try {
            final FileInputStream inputStream = new FileInputStream(ModelLoader.class.getResource("/textures/" + file + ".png").getFile());
            final PNGDecoder decoder = new PNGDecoder(inputStream);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            inputStream.close();
        }

        // Texture Error
        catch(Exception ex) {
            System.out.println("Could not load texture file " + file + "!");
            ex.printStackTrace();
            System.exit(-1);
        }

        // Create Data
        return new TextureData(buffer, width, height);
    }

    public int loadCubeMap(String directory, String[] textureFiles) {

        // Create Texture
        final int textureID = GL11.glGenTextures();
        textureList.add(textureID);

        // Bind Texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);

        // Iterate Textures
        TextureData data;
        for(Object object : new ArrayIndexed(textureFiles)) {
            Pair<String, Integer> file = (Pair<String, Integer>) object;
            data = decodeTextureFile(directory + "/" + file.first);
            GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + file.second, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
        }

        // Texture Parameters
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        // Hide Seams
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        // Return ID
        return textureID;
    }

    public int loadTexture(String file) {

        // Load Texture
        int textureID = 0;
        try {
            final FileInputStream inputStream = new FileInputStream(ModelLoader.class.getResource("/textures/" + file + ".png").getFile());
            final Texture texture = TextureLoader.getTexture("PNG", inputStream);
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
            textureID = texture.getTextureID();
            textureList.add(textureID);
            inputStream.close();
        }

        // Texture Error
        catch(IOException ex) {
            System.out.println("Could not load texture file " + file + "!");
            ex.printStackTrace();
            System.exit(-1);
        }

        // Return ID
        return textureID;
    }

    public RawModel loadToVAO(float[] positions) {

        // Create VAO
        final int vaoID = createVAO();

        // Store Data
        storeData(0, 2, positions);
        unbindVAO();

        // Create Model
        return new RawModel(vaoID, positions.length / 2);
    }

    public RawModel loadToVAO(float[] positions, int dimensions) {

        // Create VAO
        final int vaoID = createVAO();

        // Store Data
        storeData(0, dimensions, positions);
        unbindVAO();

        // Create Model
        return new RawModel(vaoID, positions.length / dimensions);
    }

    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {

        // Create VAO
        final int vaoID = createVAO();

        // Bind Indices
        bindIndicesBuffer(indices);

        // Store Data
        storeData(0, 3, positions);
        storeData(1, 2, textureCoords);
        storeData(2, 3, normals);
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