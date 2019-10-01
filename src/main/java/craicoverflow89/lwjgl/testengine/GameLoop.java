package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.DisplayManager;
import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.renderengine.ModelRender;
import craicoverflow89.lwjgl.shaders.StaticShader;
import craicoverflow89.lwjgl.textures.ModelTexture;
import org.lwjgl.opengl.Display;

public class GameLoop {

    public static void main(String[] args) {

        // Create Display
        DisplayManager.createDisplay();

        // Model Logic
        final ModelLoader loader = new ModelLoader();
        final ModelRender renderer = new ModelRender();

        // Shader Logic
        final StaticShader shader = new StaticShader();

        // Example Model
        final TexturedModel texturedModel = new TexturedModel(loader.loadToVAO(new float[] {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        }, new int[] {
                0, 1, 3, 3, 1, 2
        }), new ModelTexture(loader.loadTexture("test/image")));

        // Game Running
        while(!Display.isCloseRequested()) {

            // Prepare Renderer
            renderer.prepare();

            // Game Logic
            //

            // Game Render
            shader.start();
            renderer.render(texturedModel);
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