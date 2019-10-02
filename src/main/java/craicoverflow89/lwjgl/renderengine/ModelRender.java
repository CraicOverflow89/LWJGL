package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.helpers.Maths;
import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.shaders.StaticShader;
import craicoverflow89.lwjgl.textures.ModelTexture;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

public final class ModelRender {

    private static final float FIELD_OF_VIEW = 70f;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;

    public ModelRender(StaticShader shader) {

        // Load Projection
        shader.start();
        shader.loadProjectionMatrix(createProjectionMatrix());
        shader.stop();
    }

    private Matrix4f createProjectionMatrix() {

        // Fetch Values
        final float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        final float scaleY = (float) (1f / Math.tan(Math.toRadians(FIELD_OF_VIEW / 2f))) * aspectRatio;
        final float scaleX = scaleY / aspectRatio;
        final float frustumLength = FAR_PLANE - NEAR_PLANE;

        // Create Matrix
        final Matrix4f matrix = new Matrix4f();
        matrix.m00 = scaleX;
        matrix.m11 = scaleY;
        matrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
        matrix.m23 = -1;
        matrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
        matrix.m33 = 0;

        // Return Matrix
        return matrix;
    }

    public void prepare() {

        // Depth Order
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Clear Buffers
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Background Colour
        GL11.glClearColor(0.471f, 0.745f, 0.235f, 1f);
    }

    public void render(Entity entity, StaticShader shader) {

        // Fetch Models
        final TexturedModel texturedModel = entity.getModel();
        final RawModel rawModel = texturedModel.getRawModel();
        final ModelTexture texture = texturedModel.getTexture();

        // Bind VAO
        GL30.glBindVertexArray(rawModel.getVaoID());

        // Enable Attributes
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Load Transform
        shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()));

        // Shine Values
        shader.loadShine(texture.getShineDamper(), texture.getReflectivity());

        // Bind Texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());

        // Render Model
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertextCount(), GL11.GL_UNSIGNED_INT, 0);

        // Disable Attributes
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        // Unbind VAO
        GL30.glBindVertexArray(0);
    }

}