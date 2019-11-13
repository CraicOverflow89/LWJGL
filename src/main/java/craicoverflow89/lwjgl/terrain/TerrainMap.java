package craicoverflow89.lwjgl.terrain;

import craicoverflow89.lwjgl.renderengine.ModelLoader;
import craicoverflow89.lwjgl.textures.TerrainTexturePack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class TerrainMap {

    private final ModelLoader loader;
    private final HashMap<String, Terrain> terrainMap = new HashMap();

    public TerrainMap(ModelLoader loader) {
        this.loader = loader;
    }

    public List<Terrain> asList() {

        // Create Result
        final List<Terrain> terrainList = new ArrayList();

        // Iterate Points
        for(String point : terrainMap.keySet()) terrainList.add(terrainMap.get(point));

        // Return Result
        return terrainList;
    }

    public Terrain atGridPosition(int x, int z) {
        return terrainMap.get(pointAsString(x, z));
    }

    public Terrain atWorldPosition(float x, float z) {
        // NOTE: not using these arguments
        //       this needs completing

        // Grid Position
        final int gridX = 0;
        final int gridZ = 0;

        //final int gridX = (int) Math.floor(terrainX / mapSquareSize);
        //final int gridZ = (int) Math.floor(terrainZ / mapSquareSize);
        // NOTE: need to know the entire size of the terrain (grid x/z highest - lowest)

        // Return Terrain
        return atGridPosition(gridX, gridZ);
    }

    private String pointAsString(int x, int z) {
        return "x" + x + "z" + z;
    }

    public void put(int x, int z, TerrainTexturePack texturePack, BlendMap blendMap, HeightMap heightMap) {
        terrainMap.put(pointAsString(x, z), new Terrain(x, z, loader, texturePack, blendMap, heightMap));
    }

}