package craicoverflow89.lwjgl.shaders;

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

    private final int vertexShaderID;
    private final int fragmentShaderID;
    private final int programID;
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    private final List<String> uniformList;
    protected final HashMap<String, Integer> uniformMap = new HashMap();

    public AbstractShader(String vertextFile, String fragmentFile, List<String> uniformList) {

        // Store List
        this.uniformList = new ArrayList();
        this.uniformList.add("transformationMatrix");
        this.uniformList.addAll(uniformList);

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

    protected abstract void bindAttributes();

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

    private final int getUniformData(String uniform) {
        int result = 0;
        try {result = uniformMap.get(uniform);}
        catch(NullPointerException ex) {
            System.err.println("Could not find uniform with name " + uniform + "!");
            System.err.println("    " + uniformMap.keySet());
            System.err.println();
            ex.printStackTrace();
            System.exit(-1);
        }
        return result;
    }

    protected final int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    private final void getUniformLocations() {
        uniformMap.clear();
        for(String uniform : uniformList) uniformMap.put(uniform, getUniformLocation(uniform));
    }

    private static final int loadShader(String file, int type) {

        // Create Source
        final StringBuilder source = new StringBuilder();

        // Read File
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(AbstractShader.class.getResource("/craicoverflow89/lwjgl/shaders/" + file).getFile()));
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

    public final void loadTransformationMatrix(Matrix4f transformation) {
        loadUniform("transformationMatrix", transformation);
    }

    protected final void loadUniform(String uniform, boolean value) {
        GL20.glUniform1f(getUniformData(uniform), value ? 1f : 0f);
    }

    protected final void loadUniform(String uniform, int value) {
        GL20.glUniform1i(getUniformData(uniform), value);
    }

    protected final void loadUniform(String uniform, float value) {
        GL20.glUniform1f(getUniformData(uniform), value);
    }

    protected final void loadUniform(String uniform, Matrix4f value) {
        value.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(getUniformData(uniform), false, matrixBuffer);
    }

    protected final void loadUniform(String uniform, Vector2f value) {
        GL20.glUniform2f(getUniformData(uniform), value.x, value.y);
    }

    protected final void loadUniform(String uniform, Vector3f value) {
        GL20.glUniform3f(getUniformData(uniform), value.x, value.y, value.z);
    }

    public final void start() {
        GL20.glUseProgram(programID);
    }

    public final void stop() {
        GL20.glUseProgram(0);
    }

}