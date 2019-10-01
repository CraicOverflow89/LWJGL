package craicoverflow89.lwjgl.testengine;

import craicoverflow89.lwjgl.renderengine.DisplayManager;
import org.lwjgl.opengl.Display;

public class GameLoop {

    public static void main(String[] args) {

        // Create Display
        DisplayManager.createDisplay();

        // Game Running
        while(!Display.isCloseRequested()) {

            // Game Logic
            //

            // Game Render
            DisplayManager.updateDisplay();
        }

        // Close Display
        DisplayManager.closeDisplay();

    }

}