package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.*;
import craicoverflow89.lwjgl.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public final class GameLoop {

    public static void main(String[] args) {

        // Create Display
        DisplayManager.createDisplay();

        // Create Logic
        final ModelLoader loader = new ModelLoader();
        final MasterRender renderer = new MasterRender();

        // Create Light
        final Light light = new Light(new Vector3f(-20f, 30f, -25f), new Vector3f(1f, 1f, 1f));

        // Create Camera
        final Camera camera = new Camera();

        // Example Entity
        final Entity entity = testEntity(loader);

        // Game Running
        while(!Display.isCloseRequested()) {

            // Test Transformation
            //entity.move(0f, 0f, -0.05f);
            //entity.rotate(0f, 1f, 0f);

            // Camera Movement
            camera.move();

            // Game Render
            renderer.addEntity(entity);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        // Delete Resources
        renderer.clean();
        loader.clean();

        // Close Display
        DisplayManager.closeDisplay();
    }

    private static Entity testEntity(ModelLoader loader) {
        final ModelTexture texture = new ModelTexture(loader.loadTexture("temp"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        return new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), texture), new Vector3f(0, 0, -25), 0, 0, 0, 1);
    }

}