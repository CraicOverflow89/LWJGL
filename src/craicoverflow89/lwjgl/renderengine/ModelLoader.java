package craicoverflow89.lwjgl.renderengine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

    private List<Integer> vaoList = new ArrayList();
    private List<Integer> vboList = new ArrayList();

    public RawModel loadToVAO(float[] positions, int[] indicies) {

        // Create VAO
        final int vaoID = createVAO();

        // Bind Indices
        bindIndiciesBuffer(indicies);

        // Store Data
        storeData(0, positions);
        unbindVAO();

        // Create Model
        return new RawModel(vaoID, indicies.length);
    }

    private void bindIndiciesBuffer(int[] indices) {

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
        for(int id : vaoList) GL30.glDeleteVertexArrays(id);

        // Delete VBOs
        for(int id : vboList) GL15.glDeleteBuffers(id);
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

    private void storeData(int attributeNumber, float[] data) {

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
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);

        // Unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {

        // Unbind Current
        GL30.glBindVertexArray(0);

    }

}