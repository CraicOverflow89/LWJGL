package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.entities.BaseEntity;
import craicoverflow89.lwjgl.entities.camera.Camera;
import craicoverflow89.lwjgl.entities.light.AbstractLight;
import craicoverflow89.lwjgl.helpers.Colour;
import craicoverflow89.lwjgl.helpers.Pair;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.shaders.StaticShader;
import craicoverflow89.lwjgl.shaders.TerrainShader;
import craicoverflow89.lwjgl.terrain.Terrain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public final class MasterRenderer {

    private static final float FIELD_OF_VIEW = 70f;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    private static final Colour SKY_COLOUR = new Colour(0.5444f, 0.62f, 0.69f);
    private final StaticShader shaderStatic = new StaticShader();
    private final TerrainShader shaderTerrain = new TerrainShader();
    private final EntityRenderer rendererEntity;
    private final TerrainRenderer rendererTerrain;
    private final SkyboxRenderer rendererSkybox;
    private final Map<TexturedModel, List<BaseEntity>> entityMap = new HashMap();
    private final List<Terrain> terrainList = new ArrayList();

    public MasterRenderer(ModelLoader loader, Pair<String, String> skyboxDirectory) {

        // Ignore Faces
        setCulling(true);

        // Renderer Creation
        final Matrix4f projectionMatrix = createProjectionMatrix();
        rendererEntity = new EntityRenderer(shaderStatic, projectionMatrix);
        rendererTerrain = new TerrainRenderer(shaderTerrain, projectionMatrix);
        rendererSkybox = new SkyboxRenderer(loader, projectionMatrix, skyboxDirectory);
    }

    public void addEntity(BaseEntity entity) {

        // Fetch Model
        final TexturedModel model = entity.getModel();

        // Existing Model
        final List<BaseEntity> existingList = entityMap.get(model);
        if(existingList != null) existingList.add(entity);

        // New Model
        else {
            List<BaseEntity> newList = new ArrayList();
            newList.add(entity);
            entityMap.put(model, newList);
        }
    }

    public void addTerrain(Terrain terrain) {
        terrainList.add(terrain);
    }

    public void clean() {
        shaderStatic.clean();
        shaderTerrain.clean();
    }

    private Matrix4f createProjectionMatrix() {

        // Fetch Values
        final float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        final float scaleY = (float) (1f / Math.tan(Math.toRadians(FIELD_OF_VIEW / 2f))) * aspectRatio;
        final float scaleX = scaleY / aspectRatio;
        final float frustumLength = FAR_PLANE - NEAR_PLANE;

        // Create Matrix
        final Matrix4f matrix = new Matrix4f();
        matrix.m00 = scaleX;
        matrix.m11 = scaleY;
        matrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
        matrix.m23 = -1;
        matrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
        matrix.m33 = 0;

        // Return Matrix
        return matrix;
    }

    public void prepare() {

        // Depth Order
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Clear Buffers
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Background Colour
        GL11.glClearColor(SKY_COLOUR.r, SKY_COLOUR.g, SKY_COLOUR.b, 1f);
    }

    public void render(List<AbstractLight> lights, Camera camera) {

        // Render Setup
        prepare();

        // Render Entities
        renderEntities(lights, camera);

        // Render Terrain
        renderTerrain(lights, camera);

        // Render Skybox
        rendererSkybox.render(camera, SKY_COLOUR);
    }

    private void renderEntities(List<AbstractLight> lights, Camera camera) {

        // Render Setup
        shaderStatic.start();
        shaderStatic.loadLights(lights);
        shaderStatic.loadViewMatrix(camera);
        shaderStatic.loadSkyColour(SKY_COLOUR);

        // Render Entities
        rendererEntity.render(entityMap);

        // Render Done
        shaderStatic.stop();
        entityMap.clear();
    }

    private void renderTerrain(List<AbstractLight> lights, Camera camera) {

        // Render Setup
        shaderTerrain.start();
        shaderTerrain.loadLights(lights);
        shaderTerrain.loadViewMatrix(camera);
        shaderTerrain.loadSkyColour(SKY_COLOUR);

        // Render Terrain
        rendererTerrain.render(terrainList);

        // Render Done
        shaderTerrain.stop();
        terrainList.clear();
    }

    public static void setCulling(Boolean active) {
        if(active) {
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);
        }
        else GL11.glDisable(GL11.GL_CULL_FACE);
    }

}