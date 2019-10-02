package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.helpers.Maths;
import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.shaders.StaticShader;
import craicoverflow89.lwjgl.textures.ModelTexture;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

public final class EntityRenderer {

    private final StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {

        // Store Shader
        this.shader = shader;

        // Load Projection
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entityMap) {

        // Iterate Models
        for(TexturedModel model : entityMap.keySet()) {

            // Prepare Model
            renderModelPrepare(model);

            // Iterate Entities
            for(Entity entity : entityMap.get(model)) {

                // Prepare Entity
                shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()));

                // Render Entity
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertextCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            // Unbind Model
            renderModelUnbind();
        }
    }

    private void renderModelPrepare(TexturedModel model) {

        // Fetch Model
        final RawModel rawModel = model.getRawModel();
        final ModelTexture texture = model.getTexture();

        // Bind VAO
        GL30.glBindVertexArray(rawModel.getVaoID());

        // Enable Attributes
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Texture Transparency
        if(texture.hasTransparency()) MasterRenderer.setCulling(false);
        shader.loadLightFake(texture.hasTransparency());

        // Shine Values
        shader.loadShine(texture.getShineDamper(), texture.getReflectivity());

        // Bind Texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    }

    private void renderModelUnbind() {

        // Enable Culling
        MasterRenderer.setCulling(true);

        // Disable Attributes
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        // Unbind VAO
        GL30.glBindVertexArray(0);
    }

}