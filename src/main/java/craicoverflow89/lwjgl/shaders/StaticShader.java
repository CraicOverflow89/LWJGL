package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.helpers.Maths;
import org.lwjgl.util.vector.Matrix4f;

public final class StaticShader extends AbstractShader {

    private static final String VERTEX_FILE = "vertexShader";
    private static final String FRAGMENT_FILE = "fragmentShader";
    private int location_transformationMatrix, location_projectionMatrix, location_viewMatrix;
    private int location_lightPosition, location_lightColour, location_shineDamper, location_reflectivity;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    protected void getUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
    }

    public void loadLight(Light light) {
        super.loadUniform(location_lightPosition, light.getPosition());
        super.loadUniform(location_lightColour, light.getColour());
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadUniform(location_projectionMatrix, projection);
    }

    public void loadShine(float shineDamper, float reflectivity) {
        super.loadUniform(location_shineDamper, shineDamper);
        super.loadUniform(location_reflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f transformation) {
        super.loadUniform(location_transformationMatrix, transformation);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadUniform(location_viewMatrix, Maths.createViewMatrix(camera));
    }

}