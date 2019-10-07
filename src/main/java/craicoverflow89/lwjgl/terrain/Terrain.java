package craicoverflow89.lwjgl.terrain;

import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.textures.TerrainTexture;
import craicoverflow89.lwjgl.textures.TerrainTexturePack;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class Terrain {

    private static final float SIZE = 800;
    private static final float HEIGHT_MAX = 40f;
    private static final float HEIGHT_MIN = -40f;
    private static final float PIXEL_COLOUR_MAX = 256 * 256 * 256;
    private final float posX, posZ;
    private final RawModel model;
    private final TerrainTexturePack texturePack;
    private final TerrainTexture blendMap;

    public Terrain(int gridX, int gridZ, ModelLoader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, String heightMap) {
        this.posX = gridX * SIZE;
        this.posZ = gridZ * SIZE;
        this.model = generateTerrain(loader, heightMap);
        this.texturePack = texturePack;
        this.blendMap = blendMap;
    }

    private RawModel generateTerrain(ModelLoader loader, String heightMapFile) {

        // Load Heightmap
        BufferedImage heightMapImage = null;
        try {heightMapImage = ImageIO.read(Terrain.class.getResource("/textures/" + heightMapFile + ".png"));}
        catch(IOException ex) {
            System.err.println("Could not read height map file!");
            ex.printStackTrace();
            System.exit(-1);
        }
        final int vertexCount = heightMapImage.getHeight();

        // ?
        int count = vertexCount * vertexCount;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (vertexCount - 1) * (vertexCount - 1)];

        // ?
        int vertexPointer = 0;
        for(int i = 0; i < vertexCount; i ++) {
            for(int j = 0; j < vertexCount; j ++) {
                vertices[vertexPointer * 3 ] = (float) j / ((float)vertexCount - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = getTerrainHeight(j, i, heightMapImage);
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertexCount - 1) * SIZE;
                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 + 1] = 1;
                normals[vertexPointer * 3 + 2] = 0;
                textureCoords[vertexPointer * 2] = (float) j / ((float) vertexCount - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) vertexCount - 1);
                vertexPointer ++;
            }
        }

        // ?
        int pointer = 0;
        for(int gz = 0; gz < vertexCount - 1; gz ++) {
            for(int gx = 0; gx < vertexCount - 1; gx ++) {
                int topLeft = (gz * vertexCount) + gx;
                int bottomLeft = ((gz + 1) * vertexCount) + gx;
                indices[pointer ++] = topLeft;
                indices[pointer ++] = bottomLeft;
                indices[pointer ++] = topLeft + 1;
                indices[pointer ++] = topLeft + 1;
                indices[pointer ++] = bottomLeft;
                indices[pointer ++] = bottomLeft + 1;
            }
        }

        // Return Model
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }

    private float getTerrainHeight(int x, int z, BufferedImage image) {

        // Invalid Position
        if(x < 0 || x > image.getHeight() || z < 0 || z > image.getHeight()) {
            throw new IllegalArgumentException("Invalid position for terrain height - must be 0 - " + image.getHeight() + "!");
        }

        // Calculate Height
        float height = image.getRGB(x, z);
        height += PIXEL_COLOUR_MAX / 2f;
        height /= PIXEL_COLOUR_MAX / 2f;
        height *= HEIGHT_MAX    / 2f;
        return height;
    }

    public Vector3f getPosition() {
        return new Vector3f(posX, 0, posZ);
    }

    public float getPositionX() {
        return posX;
    }

    public float getPositionZ() {
        return posZ;
    }

    public RawModel getRawModel() {
        return model;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

}