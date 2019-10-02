package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.entities.Camera;
import craicoverflow89.lwjgl.entities.Light;
import craicoverflow89.lwjgl.helpers.Colour;
import craicoverflow89.lwjgl.helpers.Maths;
import org.lwjgl.util.vector.Matrix4f;

import java.util.HashMap;
import java.util.List;

public final class TerrainShader extends AbstractShader {

    private static final String VERTEX_FILE = "vertexShaderTerrain";
    private static final String FRAGMENT_FILE = "fragmentShaderTerrain";
    //private int location_transformationMatrix, location_projectionMatrix, location_viewMatrix, location_lightPosition;
    //private int location_lightColour, location_shineDamper, location_reflectivity, location_skyColour;
    //private int loaction_terrainBackground, location_terrainColourR, location_terrainColourG, location_terrainColourB;
    //private int location_terrainBlendMap;
    private final List<String> uniformList = List.of(
        "transformationMatrix", "projectionMatrix", "viewMatrix", "lightPosition", "lightColour", "shineDamper",
        "reflectivity", "skyColour", "terrainBackground", "terrainColourR", "terrainColourG", "terrainColourB",
        "terrainBlendMap"
    );
    private final HashMap<String, Integer> uniformMap = new HashMap();

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    protected void getUniformLocations() {
        /*location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");
        loaction_terrainBackground = super.getUniformLocation("terrainBackground");
        location_terrainColourR = super.getUniformLocation("terrainColourR");
        location_terrainColourG = super.getUniformLocation("terrainColourG");
        location_terrainColourB = super.getUniformLocation("terrainColourB");
        location_terrainBlendMap = super.getUniformLocation("terrainBlendMap");*/
        uniformMap.clear();
        for(String uniform : uniformList) uniformMap.put(uniform, getUniformLocation(uniform));
    }

    public void loadLight(Light light) {
        //super.loadUniform(location_lightPosition, light.getPosition());
        //super.loadUniform(location_lightColour, light.getColour());
        loadUniform(uniformMap.get("lightPosition"), light.getPosition());
        loadUniform(uniformMap.get("lightColour"), light.getColour());
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        //super.loadUniform(location_projectionMatrix, projection);
        loadUniform(uniformMap.get("projectionMatrix"), projection);
    }

    public void loadShine(float shineDamper, float reflectivity) {
        //super.loadUniform(location_shineDamper, shineDamper);
        //super.loadUniform(location_reflectivity, reflectivity);
        loadUniform(uniformMap.get("shineDamper"), shineDamper);
        loadUniform(uniformMap.get("reflectivity"), reflectivity);
    }

    public void loadSkyColour(Colour skyColour) {
        //super.loadUniform(location_skyColour, skyColour.asVector3f());
        loadUniform(uniformMap.get("skyColour"), skyColour.asVector3f());
    }

    public void loadTextureUnits() {
        loadUniform(uniformMap.get("terrainBackground"), 0);
        loadUniform(uniformMap.get("terrainColourR"), 1);
        loadUniform(uniformMap.get("terrainColourG"), 2);
        loadUniform(uniformMap.get("terrainColourB"), 3);
        loadUniform(uniformMap.get("terrainBlendMap"), 4);
    }

    public void loadTransformationMatrix(Matrix4f transformation) {
        //super.loadUniform(location_transformationMatrix, transformation);
        loadUniform(uniformMap.get("transformationMatrix"), transformation);
    }

    public void loadViewMatrix(Camera camera) {
        //super.loadUniform(location_viewMatrix, Maths.createViewMatrix(camera));
        loadUniform(uniformMap.get("viewMatrix"), Maths.createViewMatrix(camera));
    }

}