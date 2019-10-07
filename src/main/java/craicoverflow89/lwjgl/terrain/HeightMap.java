package craicoverflow89.lwjgl.terrain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class HeightMap {

    private final BufferedImage image;

    public HeightMap(String file) {
        image = loadImage(file);
    }

    public int getHeight() {
        return image.getHeight();
    }

    public BufferedImage getImage() {
        return image;
    }

    private BufferedImage loadImage(String file) {
        BufferedImage image = null;
        try {image = ImageIO.read(HeightMap.class.getResource("/textures/" + file + ".png"));}
        catch(IOException ex) {
            System.err.println("Could not read height map file!");
            ex.printStackTrace();
            System.exit(-1);
        }
        return image;
    }

}