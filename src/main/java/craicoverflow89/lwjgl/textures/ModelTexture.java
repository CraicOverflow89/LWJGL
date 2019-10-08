package craicoverflow89.lwjgl.textures;

public final class ModelTexture extends AbstractTexture {

    private float shineDamper = 1;
    private float reflectivity = 0;
    private boolean hasTransparency = false;
    private boolean hasFakeLighting = false;
    private int numberOfRows = 1;

    public ModelTexture(int textureID) {
        super(textureID);
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public boolean hasFakeLighting() {
        return hasFakeLighting;
    }

    public void hasFakeLighting(boolean hasFakeLighting) {
        this.hasFakeLighting = hasFakeLighting;
    }

    public boolean hasTransparency() {
        return hasTransparency;
    }

    public void hasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

}