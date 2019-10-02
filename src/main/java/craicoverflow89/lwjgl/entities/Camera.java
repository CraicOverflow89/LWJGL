package craicoverflow89.lwjgl.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public final class Camera {

    private Vector3f position;
    private float pitch = 0f;
    private float yaw = 0f;
    private float roll = 0f;

    public Camera(Vector3f position) {
        this.position = position;
    }

    public Camera(Vector3f position, float pitch, float yaw, float roll) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public float getPitch() {
        return pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRoll() {
        return roll;
    }

    public float getYaw() {
        return yaw;
    }

    public void move() {

        // Move Forward
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) position.z -= 0.1f;

        // Move Backward
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) position.z += 0.1f;

        // Move Left
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) position.x -= 0.1f;

        // Move Right
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) position.x += 0.1f;
    }

}