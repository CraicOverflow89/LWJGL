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

        // Create Matrix
        final Matrix4f viewMatrix = Maths.createViewMatrix(camera);

        // Null Translation
        viewMatrix.m30 = 0f;
        viewMatrix.m31 = 0f;
        viewMatrix.m32 = 0f;

        // Load Matrix
        loadUniform("viewMatrix", viewMatrix);
    }

}