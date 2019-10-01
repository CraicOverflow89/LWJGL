package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.helpers.Maths;
import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.shaders.StaticShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public final class ModelRender {

    public void prepare() {

        // Clear Colour
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public void render(Entity entity, StaticShader shader) {

        // Fetch Models
        final TexturedModel texturedModel = entity.getModel();
        final RawModel rawModel = texturedModel.getRawModel();

        // Bind VAO
        GL30.glBindVertexArray(rawModel.getVaoID());

        // Enable Attributes
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        // Load Transform
        shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()));

        // Bind Texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());

        // Render Model
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertextCount(), GL11.GL_UNSIGNED_INT, 0);

        // Disable Attributes
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        // Unbind VAO
        GL30.glBindVertexArray(0);
    }

}