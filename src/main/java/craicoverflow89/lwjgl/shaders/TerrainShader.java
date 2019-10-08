package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.helpers.Colour;
import craicoverflow89.lwjgl.helpers.Maths;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public final class TerrainShader extends AbstractShader {

    public TerrainShader() {
        super("vertexTerrain", "fragmentTerrain", List.of(
            "projectionMatrix", "viewMatrix", "lightPosition", "lightColour", "shineDamper", "reflectivity",
            "skyColour", "terrainBackground", "terrainColourR", "terrainColourG", "terrainColourB", "terrainBlendMap"
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

    public void loadViewMatrix(Camera camera) {
        loadUniform("viewMatrix", Maths.createViewMatrix(camera));
    }

}