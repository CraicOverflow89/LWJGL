package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.helpers.Colour;
import craicoverflow89.lwjgl.helpers.Maths;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public final class TerrainShader extends AbstractShader {

    private static final String VERTEX_FILE = "vertexShaderTerrain";
    private static final String FRAGMENT_FILE = "fragmentShaderTerrain";
    //private int location_transformationMatrix, location_projectionMatrix, location_viewMatrix, location_lightPosition;
    //private int location_lightColour, location_shineDamper, location_reflectivity, location_skyColour;
    //private int loaction_terrainBackground, location_terrainColourR, location_terrainColourG, location_terrainColourB;
    //private int location_terrainBlendMap;

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE, List.of(
            "transformationMatrix", "projectionMatrix", "viewMatrix", "lightPosition", "lightColour", "shineDamper",
            "reflectivity", "skyColour", "terrainBackground", "terrainColourR", "terrainColourG", "terrainColourB",
            "terrainBlendMap"
        ));
    }

    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textureCoords");
        bindAttribute(2, "normal");
    }

    public void loadLight(Light light) {
        loadUniform("lightPosition", light.getPosition());
        loadUniform("lightColour", light.getColour());
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        loadUniform("projectionMatrix", projection);
    }

    public void loadShine(float shineDamper, float reflectivity) {
        loadUniform("shineDamper", shineDamper);
        loadUniform("reflectivity", reflectivity);
    }

    public void loadSkyColour(Colour skyColour) {
        loadUniform("skyColour", skyColour.asVector3f());
    }

    public void loadTextureUnits() {
        loadUniform("terrainBackground", 0);
        loadUniform("terrainColourR", 1);
        loadUniform("terrainColourG", 2);
        loadUniform("terrainColourB", 3);
        loadUniform("terrainBlendMap", 4);
    }

    public void loadTransformationMatrix(Matrix4f transformation) {
        loadUniform("transformationMatrix", transformation);
    }

    public void loadViewMatrix(Camera camera) {
        loadUniform("viewMatrix", Maths.createViewMatrix(camera));
    }

}