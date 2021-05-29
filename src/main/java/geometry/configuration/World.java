package geometry.configuration;

import static org.lwjgl.opengl.GL11.glOrtho;

public class World {
    public static final int X_LOWER_BOUND = -100;
    public static final int X_UPPER_BOUND = 100;
    public static final int Y_LOWER_BOUND = -100;
    public static final int Y_UPPER_BOUND = 100;
    public static final int PLANE_WIDTH = Math.abs(X_LOWER_BOUND) + Math.abs(X_UPPER_BOUND);
    public static final int PLANE_HEIGHT = Math.abs(Y_LOWER_BOUND) + Math.abs(X_UPPER_BOUND);

    public static final float GRAVITY_ACCELERATION = 10f;

    public static void setCoordinatePlane() {
        glOrtho(X_LOWER_BOUND, X_UPPER_BOUND, Y_LOWER_BOUND, Y_UPPER_BOUND, 0, 1);
    }
}
