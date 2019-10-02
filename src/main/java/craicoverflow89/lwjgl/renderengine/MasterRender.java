package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Entity;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.shaders.StaticShader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MasterRender {

    private StaticShader shader = new StaticShader();
    private ModelRender renderer = new ModelRender(shader);
    private Map<TexturedModel, List<Entity>> entityMap = new HashMap();

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

    public void clean() {
        shader.clean();
    }

    public void render(Light light, Camera camera) {

        // Render Setup
        renderer.prepare();
        shader.start();
        shader.loadLight(light);
        shader.loadViewMatrix(camera);

        // Render Entities
        renderer.render(entityMap);

        // Render Done
        shader.stop();
        entityMap.clear();
    }

}