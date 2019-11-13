package craicoverflow89.lwjgl.shaders;

import craicoverflow89.lwjgl.helpers.Pair;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class AbstractShader {

    protected static final int LIGHTS_MAX = 4;
    private final int vertexShaderID;
    private final int fragmentShaderID;
    private final int programID;
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    //private final List<String> attributeList;
    private final List<String> uniformIntList;
    protected final HashMap<String, Integer> uniformIntMap = new HashMap();
    private final List<Pair<String, Integer>> uniformArrayList;
    protected final HashMap<String, List<Integer>> uniformArrayMap = new HashMap();

    public AbstractShader(String vertextFile, String fragmentFile, List<String> uniformIntList, List<Pair<String, Integer>> uniformArrayList) {
        // NOTE: need to insert List<String> attributeList as third argument

        // Uniform Data
        this.uniformIntList = uniformIntList;
        this.uniformArrayList = uniformArrayList;

        // Load Shaders
        vertexShaderID = loadShader(vertextFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

        // Create Program
        programID = GL20.glCreateProgram();

        // Attach Shaders
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);

        // Bind Attributes
        bindAttributes();

        // Link Program
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        // Uniform Locations
        getUniformLocations();
    }

    protected final void bindAttribute(int attributeNumber, String variableName) {
        GL20.glBindAttribLocation(programID, attributeNumber, variableName);
    }
    // NOTE: this can be deleted when using the new bindAttributes method

    protected abstract void bindAttributes();
    // NOTE: this can be deleted when using the new bindAttributes method

    /*protected void bindAttributes() {
        for(int x = 0; x < attributeList.size(); x ++) {
            GL20.glBindAttribLocation(programID, x, attributeList[x]);
        }
    }*/

    public final void clean() {

        // Force Stop
        stop();

        // Detach Shaders
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);

        // Delete Shaders
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);

        // Delete Program
        GL20.glDeleteProgram(programID);
    }

    private final List<Integer> getUniformArrayData(String uniform) {
        List<Integer> result = new ArrayList();
        try {result = uniformArrayMap.get(uniform);}
        catch(NullPointerException ex) {
            System.err.println("Could not find uniform array with name " + uniform + "!");
            System.err.println("    " + uniformArrayMap.keySet());
            System.err.println();
            ex.printStackTrace();
            System.exit(-1);
        }
        return result;
    }

    protected final List<Integer> getUniformArrayLocation(Pair<String, Integer> uniform) {

        // Create List
        final List<Integer> locations = new ArrayList();

        // Iterate Elements
        for(int x = 0; x < uniform.second; x ++) {
            locations.add(getUniformIntLocation(uniform.first + "[" + x + "]"));
        }

        // Return List
        return locations;
    }

    private final int getUniformIntData(String uniform) {
        int result = 0;
        try {result = uniformIntMap.get(uniform);}
        catch(NullPointerException ex) {
            System.err.println("Could not find uniform integer with name " + uniform + "!");
            System.err.println("    " + uniformIntMap.keySet());
            System.err.println();
            ex.printStackTrace();
            System.exit(-1);
        }
        return result;
    }

    protected final int getUniformIntLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    private final void getUniformLocations() {

        // Integer Locations
        uniformIntMap.clear();
        for(String uniform : uniformIntList) uniformIntMap.put(uniform, getUniformIntLocation(uniform));

        // Array Locations
        uniformArrayMap.clear();
        for(Pair<String, Integer> uniform : uniformArrayList) uniformArrayMap.put(uniform.first, getUniformArrayLocation(uniform));
    }

    private static final int loadShader(String file, int type) {

        // Create Source
        final StringBuilder source = new StringBuilder();

        // Read File
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(AbstractShader.class.getResource("/shaders/" + file).getFile()));
            String line;
            while((line = reader.readLine()) != null) source.append(line).append("\n");
            reader.close();
        }

        // File Error
        catch(IOException ex) {
            System.err.println("Could not read shader file " + file + "!");
            ex.printStackTrace();
            System.exit(-1);
        }

        // Create Shader
        final int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, source);
        GL20.glCompileShader(shaderID);

        // Shader Error
        if(GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }

        // Return ID
        return shaderID;
    }

    protected final void loadUniform(String uniform, boolean value) {
        GL20.glUniform1f(getUniformIntData(uniform), value ? 1f : 0f);
    }

    protected final void loadUniform(String uniform, int value) {
        GL20.glUniform1i(getUniformIntData(uniform), value);
    }

    protected final void loadUniform(String uniform, float value) {
        GL20.glUniform1f(getUniformIntData(uniform), value);
    }

    protected final void loadUniform(String uniform, Matrix4f value) {
        value.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(getUniformIntData(uniform), false, matrixBuffer);
    }

    protected final void loadUniform(String uniform, Vector2f value) {
        GL20.glUniform2f(getUniformIntData(uniform), value.x, value.y);
    }

    protected final void loadUniform(String uniform, Vector3f value) {
        GL20.glUniform3f(getUniformIntData(uniform), value.x, value.y, value.z);
    }

    protected final void loadUniform(String uniform, Integer position, Vector3f value) {
        GL20.glUniform3f(getUniformArrayData(uniform).get(position), value.x, value.y, value.z);
    }

    public final void start() {
        GL20.glUseProgram(programID);
    }

    public final void stop() {
        GL20.glUseProgram(0);
    }

}