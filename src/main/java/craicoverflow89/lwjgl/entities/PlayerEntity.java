package craicoverflow89.lwjgl.entities;

import craicoverflow89.lwjgl.models.TexturedModel;
import craicoverflow89.lwjgl.renderengine.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class PlayerEntity extends BaseEntity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;
    private static final float TERRAIN_HEIGHT = 0;
    private float currentRunSpeed = 0;
    private float currentTurnSpeed = 0;
    private float currentJumpSpeed = 0;
    private boolean currentJumpActive = false;

    public PlayerEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    private void inputCheck() {

        // Run Forward
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) currentRunSpeed = RUN_SPEED;

        // Run Backward
        else if(Keyboard.isKeyDown(Keyboard.KEY_S) && !Keyboard.isKeyDown(Keyboard.KEY_W)) currentRunSpeed = -RUN_SPEED;

        // No Run
        else currentRunSpeed = 0;

        // Turn Left
        if(Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) currentTurnSpeed = -TURN_SPEED;

        // Turn Right
        else if(Keyboard.isKeyDown(Keyboard.KEY_D) && !Keyboard.isKeyDown(Keyboard.KEY_A)) currentTurnSpeed = TURN_SPEED;

        // No Turn
        else currentTurnSpeed = 0;

        // Jump Upward
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) jump();
    }

    private void jump() {
        if(!currentJumpActive) {
            currentJumpActive = true;
            currentJumpSpeed = JUMP_POWER;
        }
    }

    public void tick() {

        // Keyboard Actions
        inputCheck();

        // Update Rotation
        rotate(0f, currentTurnSpeed * DisplayManager.getFrameTimeS(), 0f);

        // Update Position
        final float runDistance = currentRunSpeed * DisplayManager.getFrameTimeS();
        final double radianY = Math.toRadians(getRotationY());
        currentJumpSpeed += GRAVITY * DisplayManager.getFrameTimeS();
        move((float) (runDistance * Math.sin(radianY)), currentJumpSpeed * DisplayManager.getFrameTimeS(), (float) (runDistance * Math.cos(radianY)));

        // Terrain Collision
        if(getPosition().y < TERRAIN_HEIGHT) {
            currentJumpActive = false;
            currentJumpSpeed = 0;
            setPositionY(TERRAIN_HEIGHT);
        }
    }

}