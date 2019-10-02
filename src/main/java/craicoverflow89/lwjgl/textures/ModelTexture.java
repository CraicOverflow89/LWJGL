package craicoverflow89.lwjgl.textures;

public final class ModelTexture {

    private int textureID;
    private float shineDamper = 1;
    private float reflectivity = 0;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public int getID() {
        return textureID;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

}