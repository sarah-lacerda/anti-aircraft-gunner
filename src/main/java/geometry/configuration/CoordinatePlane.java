package geometry.configuration;

import static org.lwjgl.opengl.GL11.glOrtho;

public class CoordinatePlane {
    public static final int X_LOWER_BOUND = -50;
    public static final int X_UPPER_BOUND = 50;
    public static final int Y_LOWER_BOUND = -50;
    public static final int Y_UPPER_BOUND = 50;

    public static void setCoordinatePlane() {
        glOrtho(X_LOWER_BOUND, X_UPPER_BOUND, Y_LOWER_BOUND, Y_UPPER_BOUND, 0, 1);
    }
}
