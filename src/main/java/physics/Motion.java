package physics;

public class Motion {

    private Motion() {}

    public static float linearMotion(float position, float force, float deltaTime, float acceleration) {
        return position + (force * deltaTime) + ((acceleration * (float) Math.pow(deltaTime, 2)) / 2.0f);
    }
}
