package craicoverflow89.lwjgl.shaders;

import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public class GUIShader extends AbstractShader {

    public GUIShader() {
        super("vertexGUI", "fragmentGUI", List.of("transformationMatrix"), List.of());
    }

    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

    public final void loadTransformationMatrix(Matrix4f transformation) {
        loadUniform("transformationMatrix", transformation);
    }

}