package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.entities.camera.Camera;
import craicoverflow89.lwjgl.helpers.Colour;
import craicoverflow89.lwjgl.helpers.Pair;
import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.shaders.SkyboxShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public final class SkyboxRenderer {

    private static final float SIZE = 500f;
    private static final float[] VERTICES = {-SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE};
    private static final String[] TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"};
    private final SkyboxShader shader = new SkyboxShader();
    private final RawModel model;
    private final Pair<Integer, Integer> textureID;

    public SkyboxRenderer(ModelLoader loader, Matrix4f projectionMatrix, Pair<String, String> directory) {

        // Load Model
        model = loader.loadToVAO(VERTICES, 3);

        // Load Textures
        textureID = new Pair(
            loader.loadCubeMap(directory.first, TEXTURE_FILES),
            loader.loadCubeMap(directory.second, TEXTURE_FILES)
        );

        // Load Data
        shader.start();
        shader.loadCubeMaps();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Camera camera, Colour fogColour) {

        // Shader Setup
        shader.start();
        shader.loadViewMatrix(camera);
        shader.loadFogColour(fogColour);
        shader.loadBlendFactor(0f);
        // NOTE: come back to this (0f is completely daytime and 1f is night)

        // Bind VAO
        GL30.glBindVertexArray(model.getVaoID());

        // Enable Attributes
        GL20.glEnableVertexAttribArray(0);

        // Bind Textures
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID.first);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID.second);

        // Render Skybox
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertextCount());

        // Disable Attributes
        GL20.glDisableVertexAttribArray(0);

        // Unbind VAO
        GL30.glBindVertexArray(0);

        // Shader Stop
        shader.stop();
    }

}