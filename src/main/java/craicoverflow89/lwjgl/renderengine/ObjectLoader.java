package craicoverflow89.lwjgl.renderengine;

import craicoverflow89.lwjgl.models.RawModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public final class ObjectLoader {

    public static RawModel loadObjectModel(String file, ModelLoader loader) {

        // Define Result
        RawModel model = null;

        // Create Lists
        final List<Vector3f> vertexList = new ArrayList();
        final List<Vector2f> textureList = new ArrayList();
        final List<Vector3f> normalList = new ArrayList();
        final List<Integer> indiceList = new ArrayList();

        // Read File
        try {

            // Create Reader
            final BufferedReader reader = new BufferedReader(new FileReader(ObjectLoader.class.getResource("/models/" + file + ".obj").getFile()));

            // Read Lines
            String line;
            String[] data;
            while(true) {

                // Read Line
                line = reader.readLine();

                // Line Error
                if(line == null) {
                    System.out.println("Object file does not contain faces!");
                    System.exit(-1);
                }

                // Split Line
                data = line.split(" ");

                // Parse Vertex
                if(data[0].equals("v")) vertexList.add(new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3])));

                // Parse Texture
                else if(data[0].equals("vt")) textureList.add(new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2])));

                // Parse Normal
                else if(data[0].equals("vn")) normalList.add(new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3])));

                // Found Faces
                else if(data[0].equals("f")) break;
            }

            // Create Arrays
            final float[] textureArray = new float[vertexList.size() * 2];
            final float[] normalArray = new float[vertexList.size() * 3];

            // Read Lines
            do {

                // Ignore Line
                if(!line.startsWith("f")) continue;

                // Split Line
                data = line.split(" ");

                // Parse Face
                try {
                    processFace(data[1].split("/"), textureList, normalList, indiceList, textureArray, normalArray);
                    processFace(data[2].split("/"), textureList, normalList, indiceList, textureArray, normalArray);
                    processFace(data[3].split("/"), textureList, normalList, indiceList, textureArray, normalArray);
                }

                // Face Error
                catch(Exception ex) {
                    System.err.println("Error processing face in object file!");
                    System.err.println("    at " + line);
                    System.err.println();
                    System.err.println("Processed data");
                    System.err.println("    " + vertexList.size() + " vertices");
                    System.err.println("    " + textureList.size() + " textures");
                    System.err.println("    " + normalList.size() + " normals");
                    System.err.println();
                    ex.printStackTrace();
                    System.err.println();
                    System.exit(-1);
                }
            }
            while((line = reader.readLine()) != null);

            // Close Reader
            reader.close();

            // Vertex Result
            final float[] vertexArray = new float[vertexList.size() * 3];
            int pos = 0;
            for(Vector3f vertex : vertexList) {
                vertexArray[pos ++] = vertex.x;
                vertexArray[pos ++] = vertex.y;
                vertexArray[pos ++] = vertex.z;
            }

            // Indice Result
            final int[] indiceArray = new int[indiceList.size()];
            for(pos = 0; pos < indiceList.size(); pos ++) indiceArray[pos] = indiceList.get(pos);

            // Model Result
            model = loader.loadToVAO(vertexArray, textureArray, indiceArray);
        }

        // Object Error
        catch(IOException ex) {
            System.err.println("Could not read object file " + file + "!");
            ex.printStackTrace();
            System.exit(-1);
        }

        // Return Model
        return model;
    }

    private static void processFace(String[] data, List<Vector2f> textures, List<Vector3f> normals, List<Integer> indices, float[] textureArray, float[] normalArray) {

        // Vertex Position
        final int pos = Integer.parseInt(data[0]) - 1;

        // Append Indice
        indices.add(pos);

        // Texture Data
        final Vector2f texture = textures.get(Integer.parseInt(data[1]) - 1);
        textureArray[pos * 2] = texture.x;
        textureArray[pos * 2 + 1] = 1 - texture.y;

        // Normal Data
        final Vector3f normal = normals.get(Integer.parseInt(data[2]) - 1);
        normalArray[pos * 3] = normal.x;
        normalArray[pos * 3 + 1] = normal.y;
        normalArray[pos * 3 + 2] = normal.z;
    }

}