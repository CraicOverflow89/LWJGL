package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.entities.camera.Camera;
import craicoverflow89.lwjgl.entities.light.AbstractLight;
import craicoverflow89.lwjgl.helpers.Colour;
import craicoverflow89.lwjgl.helpers.Maths;
import craicoverflow89.lwjgl.helpers.Pair;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public final class TerrainShader extends AbstractShader {

    public TerrainShader() {
        super("vertexTerrain", "fragmentTerrain", List.of(
            "projectionMatrix", "viewMatrix", "shineDamper", "reflectivity",
            "skyColour", "terrainBackground", "terrainColourR", "terrainColourG", "terrainColourB", "terrainBlendMap"
        ), List.of(new Pair("lightPosition", 4), new Pair("lightColour", 4)));
    }

    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textureCoords");
        bindAttribute(2, "normal");
    }

    public void loadLights(List<AbstractLight> lightList) {

        // Iterate Lights
        for(int x = 0; x < LIGHTS_MAX; x ++) {

            // Light Supplied
            if(x < lightList.size()) {
                loadUniform("lightPosition", x, lightList.get(x).getPosition());
                loadUniform("lightColour", x, lightList.get(x).getColour().asVector3f());
            }

            // Not Supplied
            else {
                loadUniform("lightPosition", x, new Vector3f(0f, 0f, 0f));
                loadUniform("lightColour", x, new Vector3f(0f, 0f, 0f));
            }
        }
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