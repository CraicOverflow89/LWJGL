package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.DisplayManager;
import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.renderengine.ModelRender;
import craicoverflow89.lwjgl.renderengine.ObjectLoader;
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

        // Create Camera
        final Camera camera = new Camera();

        // Example Entity
        final Entity entity = testEntity(loader);

        // Game Running
        while(!Display.isCloseRequested()) {

            // Test Transformation
            //entity.move(0f, 0f, -0.05f);
            //entity.rotate(1f, 1f, 0f);

            // Camera Movement
            camera.move();

            // Prepare Renderer
            renderer.prepare();

            // Game Render
            shader.start();
            shader.loadViewMatrix(camera);
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

    private static Entity testEntity(ModelLoader loader) {
        return new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), new ModelTexture(loader.loadTexture("temp"))), new Vector3f(0, 0, -5), 0, 0, 0, 1);
    }

}