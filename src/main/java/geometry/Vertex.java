package geometry;

public class Vertex {
    private final float x;
    private final float y;

    public Vertex(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vertex(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
