package craicoverflow89.lwjgl.helpers;

import craicoverflow89.lwjgl.entities.camera.Camera;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public final class MousePicker {

    private final Camera camera;
    private final Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Vector3f currentRay;

    public MousePicker(Camera camera, Matrix4f projectionMatrix) {
        this.camera = camera;
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = calculateViewMatrix();
    }

    private Vector3f calculateCurrentRay() {

        // Device Coordinates
        final Vector2f deviceCoords = new Vector2f(
            (2f * Mouse.getX()) / Display.getWidth() - 1f,
            (2f * Mouse.getY()) / Display.getHeight() - 1f
        );

        // Clip Coordinates
        final Vector4f clipCoords = new Vector4f(deviceCoords.x, deviceCoords.y, -1f, 1f);

        // Eyespace Coordinates
        Vector4f eyeCoords = Matrix4f.transform(Matrix4f.invert(projectionMatrix, null), clipCoords, null);
        eyeCoords = new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);

        // Worldspace Coordinates
        final Vector4f worldCoords4f = Matrix4f.transform(Matrix4f.invert(viewMatrix, null), eyeCoords, null);
        final Vector3f worldCoords3f = new Vector3f(worldCoords4f.x, worldCoords4f.y, worldCoords4f.z);
        worldCoords3f.normalise();

        // Return Result
        return worldCoords3f;
    }

    private Matrix4f calculateViewMatrix() {
        return Maths.createViewMatrix(camera);
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public void update() {
        viewMatrix = calculateViewMatrix();
        currentRay = calculateCurrentRay();
    }

}