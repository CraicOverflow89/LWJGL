package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.*;
import craicoverflow89.lwjgl.terrain.Terrain;
import craicoverflow89.lwjgl.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public final class GameLoop {

    public static void main(String[] args) {

        // Create Display
        DisplayManager.createDisplay();

        // Create Logic
        final ModelLoader loader = new ModelLoader();
        final MasterRenderer renderer = new MasterRenderer();

        // Create Light
        final Light light = new Light(new Vector3f(0f, 10f, 0f), new Vector3f(1f, 1f, 1f));

        // Create Camera
        final Camera camera = new Camera(new Vector3f(0, 2f, 0));

        // Example Entity
        final List<Entity> entityList = testEntity(loader);

        // Example Terrain
        final List<Terrain> terrainList = testTerrain(loader);

        // Game Running
        while(!Display.isCloseRequested()) {

            // Test Transformation
            //entity.move(0f, 0f, -0.05f);
            //entity.rotate(0f, 1f, 0f);

            // Camera Movement
            camera.move();

            // Load Entities
            for(Entity entity : entityList) renderer.addEntity(entity);

            // Load Terrain
            for(Terrain terrain : terrainList) renderer.addTerrain(terrain);

            // Render Game
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        // Delete Resources
        renderer.clean();
        loader.clean();

        // Close Display
        DisplayManager.closeDisplay();
    }

    private static List<Entity> testEntity(ModelLoader loader) {

        // Create Entities
        final List<Entity> entityList = new ArrayList();

        // Example Texture
        final ModelTexture textureTemp = new ModelTexture(loader.loadTexture("temp"));
        textureTemp.setShineDamper(10);
        textureTemp.setReflectivity(1);

        // Example Content
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTemp), new Vector3f(0, 0, -50f), 0, 0, 0, 1));

        // Return Entities
        return entityList;
    }

    private static List<Terrain> testTerrain(ModelLoader loader) {

        // Create Terrain
        final List<Terrain> terrainList = new ArrayList();

        // Example Texture
        final ModelTexture textureGrass = new ModelTexture(loader.loadTexture("grass"));

        // Example Content
        terrainList.add(new Terrain(0, -1, loader, textureGrass));
        terrainList.add(new Terrain(1, -1, loader, textureGrass));

        // Return Terrain
        return terrainList;
    }

}