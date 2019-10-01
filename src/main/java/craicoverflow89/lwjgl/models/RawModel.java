package craicoverflow89.lwjgl.models;

public final class RawModel {

    private int vaoID;
    private int vertextCount;

    public RawModel(int vaoID, int vertextCount) {
        this.vaoID = vaoID;
        this.vertextCount = vertextCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertextCount() {
        return vertextCount;
    }

}