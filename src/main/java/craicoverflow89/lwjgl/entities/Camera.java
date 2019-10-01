package craicoverflow89.lwjgl.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public final class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch, yaw, roll;

    public Camera() {

        // TEMP
        pitch = 0f;
        yaw = 0f;
        roll = 0f;
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
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) position.z -= 0.02f;

        // Move Backward
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) position.z += 0.02f;

        // Move Left
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) position.x -= 0.02f;

        // Move Right
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) position.x += 0.02f;
    }

}