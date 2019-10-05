package craicoverflow89.lwjgl.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public final class Camera {

    private final PlayerEntity player;
    private Vector3f position = new Vector3f(0f, 0f, 0f);
    private float pitch = 20f;
    private float yaw = 0f;
    private float roll = 0f;
    private final float distanceFromPlayer = 10f;
    private float angleAroundPlayer;

    public Camera(PlayerEntity player) {
        this.player = player;
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

        // Calculate Zoom
        //distanceFromPlayer += Mouse.getDWheel() * 0.1f;

        // Camera Movement
        if(Mouse.isButtonDown(1)) {

            // Calculate Pitch
            pitch += Mouse.getDY() * 0.1f;

            // Calculate Angle
            angleAroundPlayer += Mouse.getDX() * 0.3f;
        }

        // Calculate Position
        movePosition();
    }

    private void movePosition() {

        // Execute Calculations
        final float distanceHorizontal = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
        final float distanceVertical = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
        final float theta = player.getRotationY() + angleAroundPlayer;

        // Update Position
        position = new Vector3f(
            player.getPosition().x - (float) (distanceHorizontal * Math.sin(Math.toRadians(theta))),
            player.getPosition().y + distanceVertical,
            player.getPosition().z - (float) (distanceHorizontal * Math.cos(Math.toRadians(theta)))
        );

        // Update Yaw
        yaw = 180 - theta;
    }

}