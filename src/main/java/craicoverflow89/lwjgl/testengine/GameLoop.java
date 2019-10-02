package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.entities.BaseEntity;
import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.entities.PlayerEntity;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.*;
import craicoverflow89.lwjgl.terrain.Terrain;
import craicoverflow89.lwjgl.textures.ModelTexture;
import java.util.ArrayList;
import java.util.List;

import craicoverflow89.lwjgl.textures.TerrainTexture;
import craicoverflow89.lwjgl.textures.TerrainTexturePack;
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

        // Example BaseEntity
        final List<BaseEntity> entityList = testEntity(loader);
        final PlayerEntity entityPlayer = (PlayerEntity) entityList.get(0);

        // Example Terrain
        final List<Terrain> terrainList = testTerrain(loader);

        // Game Running
        while(!Display.isCloseRequested()) {

            // Test Transformation
            //entity.move(0f, 0f, -0.05f);
            //entity.rotate(0f, 1f, 0f);

            // Process Inputs
            //camera.tick();
            entityPlayer.tick();

            // Load Entities
            for(BaseEntity entity : entityList) renderer.addEntity(entity);

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

    private static List<BaseEntity> testEntity(ModelLoader loader) {

        // Create Entities
        final List<BaseEntity> entityList = new ArrayList();

        // Example Texture: Player
        final ModelTexture texturePlayer = new ModelTexture(loader.loadTexture("misc/temp"));

        // Example Texture: Tree
        final ModelTexture textureTree = new ModelTexture(loader.loadTexture("misc/temp"));
        textureTree.setShineDamper(10);
        textureTree.setReflectivity(1);

        // Example Texture: Fern
        final ModelTexture textureFern = new ModelTexture(loader.loadTexture("scenery/fern"));
        textureFern.hasTransparency(true);
        textureFern.hasFakeLighting(true);

        // Example Model: Player
        final TexturedModel modelPlayer = new TexturedModel(ObjectLoader.loadObjectModel("misc/bunny", loader), texturePlayer);

        // Example Model: Tree
        final TexturedModel modelTree = new TexturedModel(ObjectLoader.loadObjectModel("scenery/tree", loader), textureTree);

        // Example Model: Fern
        final TexturedModel modelFern = new TexturedModel(ObjectLoader.loadObjectModel("scenery/fern", loader), textureFern);

        // Example Content
        entityList.add(new PlayerEntity(modelPlayer, new Vector3f(0, 0, 0f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelFern, new Vector3f(-10, 0, 0f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelFern, new Vector3f(10, 0, 0f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelFern, new Vector3f(-10, 0, -10f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(0, 0, -10f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10, 0, -10f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(-10, 0, -20f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(0, 0, -20f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10, 0, -20f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10, 0, -30f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10, 0, -40f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10, 0, -50f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10, 0, -60f), 0, 0, 0, 1));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10, 0, -70f), 0, 0, 0, 1));

        // Return Entities
        return entityList;
    }

    private static List<Terrain> testTerrain(ModelLoader loader) {

        // Create Terrain
        final List<Terrain> terrainList = new ArrayList();

        // Example Textures
        final TerrainTexture textureGrass = new TerrainTexture(loader.loadTexture("terrain/grass"));
        final TerrainTexture textureDirt = new TerrainTexture(loader.loadTexture("terrain/dirt"));
        final TerrainTexture textureMeadow = new TerrainTexture(loader.loadTexture("terrain/meadow"));
        final TerrainTexture texturePath = new TerrainTexture(loader.loadTexture("terrain/path"));
        final TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("terrain/blend/test"));
        final TerrainTexturePack texturePack = new TerrainTexturePack(textureGrass, textureDirt, textureMeadow, texturePath);

        // Example Content
        terrainList.add(new Terrain(0, -1, loader, texturePack, blendMap));
        terrainList.add(new Terrain(1, -1, loader, texturePack, blendMap));

        // Return Terrain
        return terrainList;
    }

}