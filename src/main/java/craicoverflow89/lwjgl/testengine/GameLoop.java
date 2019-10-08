package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.entities.BaseEntity;
import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.entities.PlayerEntity;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.*;
import craicoverflow89.lwjgl.terrain.BlendMap;
import craicoverflow89.lwjgl.terrain.HeightMap;
import craicoverflow89.lwjgl.terrain.Terrain;
import craicoverflow89.lwjgl.terrain.TerrainMap;
import craicoverflow89.lwjgl.textures.GUITexture;
import craicoverflow89.lwjgl.textures.ModelTexture;
import craicoverflow89.lwjgl.textures.TerrainTexture;
import craicoverflow89.lwjgl.textures.TerrainTexturePack;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public final class GameLoop {

    public static void main(String[] args) {

        // Create Display
        DisplayManager.createDisplay();

        // Create Logic
        final ModelLoader loader = new ModelLoader();
        final MasterRenderer renderer = new MasterRenderer();
        final GUIRenderer guiRenderer = new GUIRenderer(loader);

        // Create Light
        final Light light = new Light(new Vector3f(0f, 10f, 0f), new Vector3f(1f, 1f, 1f));

        // Example Entity
        final List<BaseEntity> entityList = testEntity(loader);
        final PlayerEntity entityPlayer = (PlayerEntity) entityList.get(0);

        // Create Camera
        final Camera camera = new Camera(entityPlayer);

        // Example Terrain
        final TerrainMap terrainMap = testTerrain(loader);

        // Example GUIs
        final List<GUITexture> guiList = testGUI(loader);

        // Game Running
        final List<Terrain> terrainList = terrainMap.asList();
        while(!Display.isCloseRequested()) {

            // Test Transformation
            //entity.move(0f, 0f, -0.05f);
            //entity.rotate(0f, 1f, 0f);

            // Process Inputs
            camera.move();
            entityPlayer.tick(terrainMap.atWorldPosition(entityPlayer.getPosition().x, entityPlayer.getPosition().z));
            // NOTE: consider best naming practices for these methods

            // Load Entities
            for(BaseEntity entity : entityList) renderer.addEntity(entity);

            // Load Terrain
            for(Terrain terrain : terrainList) renderer.addTerrain(terrain);

            // Render Game
            renderer.render(light, camera);
            guiRenderer.render(guiList);
            DisplayManager.updateDisplay();
        }

        // Delete Resources
        renderer.clean();
        guiRenderer.clean();
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
        //final ModelTexture textureFern = new ModelTexture(loader.loadTexture("scenery/fern"));
        final ModelTexture textureFern = new ModelTexture(loader.loadTexture("scenery/fernAtlas"));
        textureFern.hasTransparency(true);
        textureFern.hasFakeLighting(true);
        textureFern.setNumberOfRows(2);

        // Example Model: Player
        final TexturedModel modelPlayer = new TexturedModel(ObjectLoader.loadObjectModel("misc/cube", loader), texturePlayer);

        // Example Model: Tree
        final TexturedModel modelTree = new TexturedModel(ObjectLoader.loadObjectModel("scenery/tree", loader), textureTree);

        // Example Model: Fern
        final TexturedModel modelFern = new TexturedModel(ObjectLoader.loadObjectModel("scenery/fern", loader), textureFern);

        // Example Entity: Player
        entityList.add(new PlayerEntity(modelPlayer, new Vector3f(0f, 0f, 0f), 0f, 0f, 0f, 1f));

        // Example Entity: Scenery
        final Random random = new Random(676452);
        entityList.add(new BaseEntity(modelFern, random.nextInt(4), new Vector3f(-10f, 0f, 0f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelFern, random.nextInt(4), new Vector3f(10f, 0f, 0f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelFern, random.nextInt(4), new Vector3f(-10f, 0f, 10f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(0f, 0f, 10f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10f, 0f, 10f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(-10f, 0f, 20f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(0f, 0f, 20f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10f, 0f, 20f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10f, 0f, 30f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10f, 0f, 40f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10f, 0f, 50f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10f, 0f, 60f), 0f, 0f, 0f, 1f));
        entityList.add(new BaseEntity(modelTree, new Vector3f(10f, 0f, 70f), 0f, 0f, 0f, 1f));
        // NOTE: should use Terrain.getTerrainHeight to work out the Y position for new entities

        // Return Entities
        return entityList;
    }

    private static List<GUITexture> testGUI(ModelLoader loader) {

        // Create GUIs
        final ArrayList<GUITexture> guiList = new ArrayList();

        // Example GUIs
        guiList.add(new GUITexture(loader.loadTexture("gui/test"), new Vector2f(-0.5f, -0.5f), new Vector2f(0.5f, 0.5f)));

        // Return GUIs
        return guiList;
    }

    private static TerrainMap testTerrain(ModelLoader loader) {

        // Create Terrain
        final TerrainMap terrainMap = new TerrainMap(loader);

        // Example Textures
        final TerrainTexture textureGrass = new TerrainTexture(loader.loadTexture("terrain/grass"));
        final TerrainTexture textureDirt = new TerrainTexture(loader.loadTexture("terrain/dirt"));
        final TerrainTexture textureMeadow = new TerrainTexture(loader.loadTexture("terrain/meadow"));
        final TerrainTexture texturePath = new TerrainTexture(loader.loadTexture("terrain/path"));

        // Example Texture Pack
        final TerrainTexturePack texturePack = new TerrainTexturePack(textureGrass, textureDirt, textureMeadow, texturePath);

        // Example Blend Map
        final BlendMap blendMap = new BlendMap(loader, "terrain/blend/test");

        // Example Height Map
        final HeightMap heightMap = new HeightMap("terrain/height/test");

        // Example Terrain
        terrainMap.put(0, 0, texturePack, blendMap, heightMap);
        terrainMap.put(-1, 0, texturePack, blendMap, heightMap);

        // Return Terrain
        return terrainMap;
    }

}