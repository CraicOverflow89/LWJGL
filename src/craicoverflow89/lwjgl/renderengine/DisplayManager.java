package craicoverflow89.lwjgl.renderengine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;

    public static void closeDisplay() {

        // Destroy Display
        Display.destroy();
    }

    public static void createDisplay() {

        // Context Attributes
        final ContextAttribs contextAttribs = new ContextAttribs(3, 2);
        contextAttribs.withForwardCompatible(true);
        contextAttribs.withProfileCore(true);

        // Display Setup
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), contextAttribs);
            Display.setTitle("LWJGL");
        }
        catch(LWJGLException ex) {ex.printStackTrace();}

        // Render Location
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void updateDisplay() {

        // Synchronise Display
        Display.sync(FPS_CAP);

        // Invoke Update
        Display.update();
    }

}