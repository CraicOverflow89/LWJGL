package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.entities.camera.Camera;
import craicoverflow89.lwjgl.helpers.Maths;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public final class SkyboxShader extends AbstractShader {

    public SkyboxShader() {
        super("vertexSkybox", "fragmentSkybox", List.of("projectionMatrix", "viewMatrix"), List.of());
    }

    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        loadUniform("projectionMatrix", projection);
    }

    public void loadViewMatrix(Camera camera) {
        loadUniform("viewMatrix", Maths.createViewMatrix(camera));
    }

}