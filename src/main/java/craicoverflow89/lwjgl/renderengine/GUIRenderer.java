package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.helpers.Maths;
import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.shaders.GUIShader;
import craicoverflow89.lwjgl.textures.GUITexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public final class GUIRenderer {

    private final GUIShader shader = new GUIShader();
    private final RawModel model;

    public GUIRenderer(ModelLoader loader) {
        this.model = loader.loadToVAO(new float[] {-1, 1, -1, -1, 1, 1, 1, -1});
    }

    public void clean() {
        shader.clean();
    }

    public void render(List<GUITexture> textureList) {

        // Shader Start
        shader.start();

        // Bind Vao
        GL30.glBindVertexArray(model.getVaoID());

        // Enable Attributes
        GL20.glEnableVertexAttribArray(0);

        // Enable Transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // Render GUI
        for(GUITexture texture : textureList) {

            // Bind Texture
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

            // Prepare Texture
            shader.loadTransformationMatrix(Maths.createTransformationMatrix(texture.getPosition(), texture.getScale()));

            // Render Texture
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, model.getVertextCount());
        }

        // Disable Attributes
        GL20.glDisableVertexAttribArray(0);

        // Disable Transparency
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Unbind VAO
        GL30.glBindVertexArray(0);

        // Shader Stop
        shader.stop();
    }

}