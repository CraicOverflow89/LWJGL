package craicoverflow89.lwjgl.helpers;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public final class Maths {

    public static final Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {

        // Create Matrix
        final Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        // Translate Matrix
        Matrix4f.translate(translation, matrix, matrix);

        // Rotate Matrix
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);

        // Scale Matrix
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);

        // Return Matrix
        return matrix;
    }

}