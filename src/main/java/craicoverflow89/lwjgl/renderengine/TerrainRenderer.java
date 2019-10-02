package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.helpers.Maths;
import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.shaders.TerrainShader;
import craicoverflow89.lwjgl.terrain.Terrain;
import craicoverflow89.lwjgl.textures.ModelTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public final class TerrainRenderer {

    private final TerrainShader shader;

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {

        // Store Shader
        this.shader = shader;

        // Load Projection
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(List<Terrain> terrainList) {

        // Iterate Terrain
        for(Terrain terrain : terrainList) {

            // Prepare Terrain
            renderTerrainPrepare(terrain);
            shader.loadTransformationMatrix(Maths.createTransformationMatrix(terrain.getPosition(), 0f, 0f, 0f, 1f));

            // Render Terrain
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getRawModel().getVertextCount(), GL11.GL_UNSIGNED_INT, 0);

            // Unbind Terrain
            renderTerrainUnbind();
        }
    }

    private void renderTerrainPrepare(Terrain terrain) {

        // Fetch Model
        final RawModel rawModel = terrain.getRawModel();
        final ModelTexture texture = terrain.getTexture();

        // Bind VAO
        GL30.glBindVertexArray(rawModel.getVaoID());

        // Enable Attributes
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Shine Values
        shader.loadShine(texture.getShineDamper(), texture.getReflectivity());

        // Bind Texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
    }

    private void renderTerrainUnbind() {

        // Disable Attributes
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        // Unbind VAO
        GL30.glBindVertexArray(0);
    }

}