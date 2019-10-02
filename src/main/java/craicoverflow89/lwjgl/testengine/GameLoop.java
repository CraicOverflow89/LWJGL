package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.*;
import craicoverflow89.lwjgl.terrain.Terrain;
import craicoverflow89.lwjgl.textures.ModelTexture;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

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
        final Camera camera = new Camera(new Vector3f(0f, 2f, 25f));

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

        // Example Texture 1
        final ModelTexture textureTree = new ModelTexture(loader.loadTexture("temp"));
        textureTree.setShineDamper(10);
        textureTree.setReflectivity(1);

        // Example Texture 2
        final ModelTexture textureFern = new ModelTexture(loader.loadTexture("fern"));
        textureFern.setShineDamper(10);
        textureFern.setReflectivity(1);
        textureFern.hasTransparency(true);
        textureFern.hasFakeLighting(true);

        // Example Content
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("fern", loader), textureFern), new Vector3f(-10, 0, 0f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(0, 0, 0f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("fern", loader), textureFern), new Vector3f(10, 0, 0f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("fern", loader), textureFern), new Vector3f(-10, 0, -10f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(0, 0, -10f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(10, 0, -10f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(-10, 0, -20f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(0, 0, -20f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(10, 0, -20f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(10, 0, -30f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(10, 0, -40f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(10, 0, -50f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(10, 0, -60f), 0, 0, 0, 1));
        entityList.add(new Entity(new TexturedModel(ObjectLoader.loadObjectModel("tree", loader), textureTree), new Vector3f(10, 0, -70f), 0, 0, 0, 1));

        // Return Entities
        return entityList;
    }

    private static List<Terrain> testTerrain(ModelLoader loader) {

        // Create Terrain
        final List<Terrain> terrainList = new ArrayList();

        // Example Textures
        final ModelTexture textureGrass = new ModelTexture(loader.loadTexture("terrain/grass"));

        // Example Content
        terrainList.add(new Terrain(0, -1, loader, textureGrass));
        terrainList.add(new Terrain(1, -1, loader, textureGrass));

        // Return Terrain
        return terrainList;
    }

}