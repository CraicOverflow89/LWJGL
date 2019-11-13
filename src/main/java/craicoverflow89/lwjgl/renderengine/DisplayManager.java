package craicoverflow89.lwjgl.renderengine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

public final class DisplayManager {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;
    private static long lastFrameTime;
    private static float delta;

    public static void closeDisplay() {

        // Destroy Display
        Display.destroy();
    }

    public static void createDisplay() {

        // Display Setup
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true)
            );
            Display.setTitle("LWJGL");
        }
        catch(LWJGLException ex) {
            System.out.println("Could not create display - " + ex.getMessage() + "!");
            ex.printStackTrace();
            System.exit(-1);
        }

        // Render Location
        GL11.glViewport(0, 0, WIDTH, HEIGHT);

        // Update Timer
        lastFrameTime = getCurrentTime();
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    public static float getFrameTimeMS() {
        return delta;
    }

    public static float getFrameTimeS() {
        return delta / 1000f;
    }

    public static void updateDisplay() {

        // Synchronise Display
        Display.sync(FPS_CAP);

        // Invoke Update
        Display.update();

        // Update Timer
        delta = getCurrentTime() - lastFrameTime;
        lastFrameTime = getCurrentTime();
    }

}