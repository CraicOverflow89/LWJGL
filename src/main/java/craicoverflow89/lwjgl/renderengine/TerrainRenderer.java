package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.helpers.Maths;
import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.shaders.TerrainShader;
import craicoverflow89.lwjgl.terrain.Terrain;
import craicoverflow89.lwjgl.textures.TerrainTexture;
import craicoverflow89.lwjgl.textures.TerrainTexturePack;
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

        // Load Data
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.loadTextureUnits();
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

    private void renderTerrainBind(Terrain terrain) {

        // Texture Pack
        final TerrainTexturePack texturePack = terrain.getTexturePack();

        // Bind Textures
        renderTerrainBindTexture(GL13.GL_TEXTURE0, texturePack.getTextureBackground());
        renderTerrainBindTexture(GL13.GL_TEXTURE1, texturePack.getTextureColourR());
        renderTerrainBindTexture(GL13.GL_TEXTURE2, texturePack.getTextureColourG());
        renderTerrainBindTexture(GL13.GL_TEXTURE3, texturePack.getTextureColourB());
        renderTerrainBindTexture(GL13.GL_TEXTURE4, terrain.getBlendMap().getTexture());
    }

    private void renderTerrainBindTexture(int active, TerrainTexture texture) {
        GL13.glActiveTexture(active);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    }

    private void renderTerrainPrepare(Terrain terrain) {

        // Fetch Model
        final RawModel rawModel = terrain.getRawModel();

        // Bind VAO
        GL30.glBindVertexArray(rawModel.getVaoID());

        // Enable Attributes
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        renderTerrainBind(terrain);

        // Shine Values
        shader.loadShine(1.0f, 0.0f);
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