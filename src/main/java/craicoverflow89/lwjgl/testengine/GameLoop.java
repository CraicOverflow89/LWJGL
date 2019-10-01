package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.DisplayManager;
import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.renderengine.ModelRender;
import craicoverflow89.lwjgl.shaders.StaticShader;
import craicoverflow89.lwjgl.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public final class GameLoop {

    public static void main(String[] args) {

        // Create Display
        DisplayManager.createDisplay();

        // Create Logic
        final ModelLoader loader = new ModelLoader();
        final StaticShader shader = new StaticShader();
        final ModelRender renderer = new ModelRender(shader);

        // Example Entity
        final Entity entity = new Entity(new TexturedModel(loader.loadToVAO(new float[] {
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f
        }, new float[] {
            0, 0, 0, 1, 1, 1, 1, 0
        }, new int[] {
            0, 1, 3, 3, 1, 2
        }), new ModelTexture(loader.loadTexture("test/image"))), new Vector3f(0, 0, -1), 0, 0, 0, 1);

        // Game Running
        while(!Display.isCloseRequested()) {

            // Test Transformation
            entity.move(0f, 0f, -0.05f);
            //entity.rotate(0f, 1f, 0f);

            // Prepare Renderer
            renderer.prepare();

            // Game Logic
            //

            // Game Render
            shader.start();
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        // Delete Resources
        loader.clean();
        shader.clean();

        // Close Display
        DisplayManager.closeDisplay();

    }

}