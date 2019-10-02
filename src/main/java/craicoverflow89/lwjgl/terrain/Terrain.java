package craicoverflow89.lwjgl.terrain;

import craicoverflow89.lwjgl.models.RawModel;
import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.textures.TerrainTexture;
import craicoverflow89.lwjgl.textures.TerrainTexturePack;
import org.lwjgl.util.vector.Vector3f;

public final class Terrain {

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;
    private final float posX, posZ;
    private final RawModel model;
    private final TerrainTexturePack texturePack;
    private final TerrainTexture blendMap;

    public Terrain(int gridX, int gridZ, ModelLoader loader, TerrainTexturePack texturePack, TerrainTexture blendMap) {
        this.posX = gridX * SIZE;
        this.posZ = gridZ * SIZE;
        this.model = generateTerrain(loader);
        this.texturePack = texturePack;
        this.blendMap = blendMap;
    }

    private RawModel generateTerrain(ModelLoader loader) {

        // ?
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];

        // ?
        int vertexPointer = 0;
        for(int i = 0; i < VERTEX_COUNT; i ++) {
            for(int j = 0; j < VERTEX_COUNT; j ++) {
                vertices[vertexPointer * 3 ] = (float) j / ((float)VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = 0;
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 + 1] = 1;
                normals[vertexPointer * 3 + 2] = 0;
                textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
                vertexPointer ++;
            }
        }

        // ?
        int pointer = 0;
        for(int gz = 0; gz < VERTEX_COUNT - 1; gz ++) {
            for(int gx = 0; gx < VERTEX_COUNT - 1; gx ++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
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