package craicoverflow89.lwjgl.shaders;

import java.util.List;

public class GUIShader extends AbstractShader {

    public GUIShader() {
        super("vertexShaderGUI", "fragmentShaderGUI", List.of());
    }

    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

}