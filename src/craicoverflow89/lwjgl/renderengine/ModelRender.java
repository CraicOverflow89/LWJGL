package craicoverflow89.lwjgl.renderengine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelRender {

    public void prepare() {

        // Clear Colour
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void render(RawModel model) {

        // Bind VAO
        GL30.glBindVertexArray(model.getVaoID());

        // Fetch Attribute List
        GL20.glEnableVertexAttribArray(0);

        // Render Model
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertextCount());

        // Unbind Attribute List
        GL20.glDisableVertexAttribArray(0);

        // Unbind VAO
        GL30.glBindVertexArray(0);
    }

}