package craicoverflow89.lwjgl.terrain;

import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.textures.TerrainTexture;
import craicoverflow89.lwjgl.textures.TerrainTexturePack;
import org.lwjgl.util.vector.Vector3f;

public final class Terrain {

    private static final float SIZE = 800;
    private static final float HEIGHT_MAX = 40f;
    private static final float HEIGHT_MIN = -40f;
    private static final float PIXEL_COLOUR_MAX = 256 * 256 * 256;
    private final float posX, posZ;
    private final RawModel model;
    private final TerrainTexturePack texturePack;
    private final TerrainTexture blendMap;
    private final HeightMap heightMap;

    public Terrain(int gridX, int gridZ, ModelLoader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, String heightMap) {
        this.posX = gridX * SIZE;
        this.posZ = gridZ * SIZE;
        this.texturePack = texturePack;
        this.blendMap = blendMap;
        this.heightMap = new HeightMap(heightMap);
        this.model = generateTerrain(loader);
    }

    private RawModel generateTerrain(ModelLoader loader) {

        // ?
        final int vertexCount = this.heightMap.getHeight();
        final int count = vertexCount * vertexCount;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (vertexCount - 1) * (vertexCount - 1)];

        // ?
        int vertexPointer = 0;
        for(int i = 0; i < vertexCount; i ++) {
            for(int j = 0; j < vertexCount; j ++) {
                vertices[vertexPointer * 3 ] = (float) j / ((float)vertexCount - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = generateTerrainHeight(j, i);
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertexCount - 1) * SIZE;
                Vector3f normal = generateTerrainNormal(j, i);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
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

    private float generateTerrainHeight(int x, int z) {

        // Invalid Position
        if(x < 0 || x >= this.heightMap.getHeight() || z < 0 || z >= this.heightMap.getHeight()) {
            //throw new IllegalArgumentException("Invalid position for terrain height - must be 0 - " + this.heightMap.getHeight() + "!");
            return 0f;
        }

        // Calculate Height
        float height = this.heightMap.getImage().getRGB(x, z);
        height += PIXEL_COLOUR_MAX / 2f;
        height /= PIXEL_COLOUR_MAX / 2f;
        height *= HEIGHT_MAX    / 2f;
        return height;
    }

    private Vector3f generateTerrainNormal(int x, int z) {

        // Neighbouring Vertices
        final float heightL = generateTerrainHeight(x - 1, z);
        final float heightR = generateTerrainHeight(x + 1, z);
        final float heightD = generateTerrainHeight(x, z - 1);
        final float heightU = generateTerrainHeight(x, z + 1);

        // Calculate Normal
        final Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
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