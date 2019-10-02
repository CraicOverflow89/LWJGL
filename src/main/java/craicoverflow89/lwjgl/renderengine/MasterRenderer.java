package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.shaders.StaticShader;
import craicoverflow89.lwjgl.shaders.TerrainShader;
import craicoverflow89.lwjgl.terrain.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MasterRenderer {

    private static final float FIELD_OF_VIEW = 70f;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    private final StaticShader shaderStatic = new StaticShader();
    private final TerrainShader shaderTerrain = new TerrainShader();
    private final EntityRenderer rendererEntity;
    private final TerrainRenderer rendererTerrain;
    private final Map<TexturedModel, List<Entity>> entityMap = new HashMap();
    private final List<Terrain> terrainList = new ArrayList();

    public MasterRenderer() {

        // Ignore Faces
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        // Renderer Creation
        final Matrix4f projectionMatrix = createProjectionMatrix();
        rendererEntity = new EntityRenderer(shaderStatic, projectionMatrix);
        rendererTerrain = new TerrainRenderer(shaderTerrain, projectionMatrix);
    }

    public void addEntity(Entity entity) {

        // Fetch Model
        final TexturedModel model = entity.getModel();

        // Existing Model
        final List<Entity> existingList = entityMap.get(model);
        if(existingList != null) existingList.add(entity);

        // New Model
        else {
            List<Entity> newList = new ArrayList();
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
        GL11.glClearColor(0.471f, 0.745f, 0.235f, 1f);
    }

    public void render(Light light, Camera camera) {

        // Render Setup
        prepare();

        // Render Entities
        renderEntities(light, camera);

        // Render Terrain
        renderTerrain(light, camera);
    }

    private void renderEntities(Light light, Camera camera) {

        // Render Setup
        shaderStatic.start();
        shaderStatic.loadLight(light);
        shaderStatic.loadViewMatrix(camera);

        // Render Entities
        rendererEntity.render(entityMap);

        // Render Done
        shaderStatic.stop();
        entityMap.clear();
    }

    private void renderTerrain(Light light, Camera camera) {

        // Render Setup
        shaderTerrain.start();
        shaderTerrain.loadLight(light);
        shaderTerrain.loadViewMatrix(camera);

        // Render Terrain
        rendererTerrain.render(terrainList);

        // Render Done
        shaderTerrain.stop();
        terrainList.clear();
    }

}