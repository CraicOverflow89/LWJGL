package craicoverflow89.lwjgl.shaders;

import java.util.List;

public class GUIShader extends AbstractShader {

    public GUIShader() {
        super("vertexGUI", "fragmentGUI", List.of(), List.of());
    }

    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

}