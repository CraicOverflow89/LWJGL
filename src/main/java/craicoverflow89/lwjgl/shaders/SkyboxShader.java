package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.entities.camera.Camera;
import craicoverflow89.lwjgl.helpers.Colour;
import craicoverflow89.lwjgl.helpers.Maths;
import craicoverflow89.lwjgl.renderengine.DisplayManager;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public final class SkyboxShader extends AbstractShader {

    private static final float ROTATION_SPEED = 1f;
    private float rotationCurrent = 0;

    public SkyboxShader() {
        super(
            "vertexSkybox", "fragmentSkybox",
            List.of("position"),
            List.of("projectionMatrix", "viewMatrix", "fogColour", "cubeMap1", "cubeMap2", "blendFactor"),
            List.of()
        );
    }

    public void loadBlendFactor(float blendFactor) {
        loadUniform("blendFactor", blendFactor);
    }

    public void loadFogColour(Colour fogColour) {
        loadUniform("fogColour", fogColour.asVector3f());
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        loadUniform("projectionMatrix", projection);
    }

    public void loadCubeMaps() {
        loadUniform("cubeMap1", 0);
        loadUniform("cubeMap2", 1);
    }

    public void loadViewMatrix(Camera camera) {

        // Create Matrix
        final Matrix4f viewMatrix = Maths.createViewMatrix(camera);

        // Null Translation
        viewMatrix.m30 = 0f;
        viewMatrix.m31 = 0f;
        viewMatrix.m32 = 0f;

        // Apply Rotation
        rotationCurrent += ROTATION_SPEED * DisplayManager.getFrameTimeS();
        Matrix4f.rotate((float) Math.toRadians(rotationCurrent), new Vector3f(0f, 1f, 0f), viewMatrix, viewMatrix);

        // Load Matrix
        loadUniform("viewMatrix", viewMatrix);
    }

}