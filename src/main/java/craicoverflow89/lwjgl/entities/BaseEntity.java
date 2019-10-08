package craicoverflow89.lwjgl.entities;

import craicoverflow89.lwjgl.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class BaseEntity {

    private final TexturedModel model;
    private final int textureIndex;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    public BaseEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.textureIndex = 0;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public BaseEntity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.textureIndex = textureIndex;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public final TexturedModel getModel() {
        return model;
    }

    public final Vector3f getPosition() {
        return position;
    }

    public final float getRotationX() {
        return rotX;
    }

    public final float getRotationY() {
        return rotY;
    }

    public final float getRotationZ() {
        return rotZ;
    }

    public final float getScale() {
        return scale;
    }

    public float getTextureOffsetX() {
        return ((float) textureIndex % model.getTexture().getNumberOfRows()) / ((float) model.getTexture().getNumberOfRows());
    }

    public float getTextureOffsetY() {
        return ((float) textureIndex / model.getTexture().getNumberOfRows()) / ((float) model.getTexture().getNumberOfRows());
    }

    public void move(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    public final void rotate(float x, float y, float z) {
        rotX += x;
        rotY += y;
        rotZ += z;
    }

    public final void setPosition(Vector3f position) {
        this.position = position;
    }

    public final void setPositionX(float positionX) {
        position = new Vector3f(positionX, position.y, position.z);
    }

    public final void setPositionY(float positionY) {
        position = new Vector3f(position.x, positionY, position.z);
    }

    public final void setPositionZ(float positionZ) {
        position = new Vector3f(position.x, position.y, positionZ);
    }

    public final void setRotation(float rotX, float rotY, float rotZ) {
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    public final void setRotationX(float rotationX) {
        this.rotX = rotX;
    }

    public final void setRotationY(float rotationY) {
        this.rotY = rotY;
    }

    public final void setRotationZ(float rotationZ) {
        this.rotZ = rotZ;
    }

    public final void setScale(float scale) {
        this.scale = scale;
    }

}