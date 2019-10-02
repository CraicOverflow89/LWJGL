package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.helpers.Colour;
import craicoverflow89.lwjgl.helpers.Maths;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public final class StaticShader extends AbstractShader {

    private static final String VERTEX_FILE = "vertexShaderStatic";
    private static final String FRAGMENT_FILE = "fragmentShaderStatic";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE, List.of(
            "transformationMatrix", "projectionMatrix", "viewMatrix", "lightPosition",
            "lightColour", "shineDamper", "reflectivity", "lightFake", "skyColour"
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

    public void loadLightFake(Boolean lightFake) {
        loadUniform("lightFake", lightFake);
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

    public void loadTransformationMatrix(Matrix4f transformation) {
        loadUniform("transformationMatrix", transformation);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadUniform("viewMatrix", Maths.createViewMatrix(camera));
    }

}