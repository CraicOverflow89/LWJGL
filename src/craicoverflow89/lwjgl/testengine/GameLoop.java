package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.renderengine.DisplayManager;
import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.renderengine.ModelRender;
import craicoverflow89.lwjgl.renderengine.RawModel;
import org.lwjgl.opengl.Display;

public class GameLoop {

    public static void main(String[] args) {

        // Create Display
        DisplayManager.createDisplay();

        // Model Logic
        final ModelLoader loader = new ModelLoader();
        final ModelRender renderer = new ModelRender();

        // Example Model
        final RawModel model = loader.loadToVAO(new float[] {
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f
        }, new int[] {
            0, 1, 3, 3, 1, 2
        });

        // Game Running
        while(!Display.isCloseRequested()) {

            // Prepare Renderer
            renderer.prepare();

            // Game Logic
            //

            // Game Render
            renderer.render(model);
            DisplayManager.updateDisplay();
        }

        // Delete Resources
        loader.clean();

        // Close Display
        DisplayManager.closeDisplay();

    }

}