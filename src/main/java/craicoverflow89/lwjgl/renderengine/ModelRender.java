package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelRender {

    public void prepare() {

        // Clear Colour
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
    }

    public void render(TexturedModel texturedModel) {

        // Raw Model
        final RawModel rawModel = texturedModel.getRawModel();

        // Bind VAO
        GL30.glBindVertexArray(rawModel.getVaoID());

        // Fetch Attribute List
        GL20.glEnableVertexAttribArray(0);

        // Render Model
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertextCount(), GL11.GL_UNSIGNED_INT, 0);

        // Unbind Attribute List
        GL20.glDisableVertexAttribArray(0);

        // Unbind VAO
        GL30.glBindVertexArray(0);
    }

}