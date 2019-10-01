package craicoverflow89.lwjgl.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class AbstractShader {

    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    public AbstractShader(String vertextFile, String fragmentFile) {

        // Load Shaders
        vertexShaderID = loadShader(vertextFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

        // Create Program
        programID = GL20.glCreateProgram();

        // Attach Shaders
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);

        // Link Program
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        // Bind Attributes
        bindAttributes();
    }

    protected void bindAttribute(int attributeNumber, String variableName) {
        GL20.glBindAttribLocation(programID, attributeNumber, variableName);
    }

    protected abstract void bindAttributes();

    public void clean() {

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

    private static int loadShader(String file, int type) {

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
            System.err.println("Could not read file!");
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

    public final void start() {
        GL20.glUseProgram(programID);
    }

    public final void stop() {
        GL20.glUseProgram(0);
    }

}