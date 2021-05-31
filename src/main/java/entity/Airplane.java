package entity;

import geometry.Vertex;
import model.Model;

import static geometry.configuration.World.WORLD_HEIGHT;
import static geometry.configuration.World.WORLD_WIDTH;
import static geometry.configuration.World.X_LOWER_BOUND;
import static geometry.configuration.World.X_UPPER_BOUND;
import static geometry.configuration.World.Y_UPPER_BOUND;
import static render.Renderer.drawRigidBody;
import static util.Randomizer.getSparseIntWithinRange;

public class Airplane extends Enemy {

    private boolean destroyed;
    private float rotationAngle;

    public static final int MINIMUM_X_SPAWN_POSITION = X_LOWER_BOUND - 100;
    public static final int MAXIMUM_X_SPAWN_POSITION = X_LOWER_BOUND;
    public static final int MINIMUM_FLIGHT_LEVEL = 10;
    public static final int MINIMUM_X_DISTANCE_BETWEEN_PLANES = WORLD_WIDTH / 10;
    public static final int MINIMUM_Y_DISTANCE_BETWEEN_PLANES = WORLD_HEIGHT / 10;
    public static final int TOTAL_NUMBER_OF_ENEMY_PLANES = 10;

    public Airplane(Model model) {
        super(model, randomValidPosition());
        destroyed = false;
        rotationAngle = 0f;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void setPosition(float x, float y) {
        setPosition(new Vertex(x, y));
    }

    @Override
    public void setPosition(Vertex position) {
        if (destroyed) {
            fall(position);
        } else {
            if (isOutOfScreen(position.getX())) {
                super.setPosition(randomValidPosition());
            } else {
                super.setPosition(position);
            }
        }
    }

    @Override
    public void render() {
        final float modelXCenter = getDimension().getWidth() / 2.0f;
        final float modelYCenter = -getDimension().getHeight() / 2.0f;
        final Vertex modelRotationPosition = new Vertex(modelXCenter, modelYCenter);

        drawRigidBody(getModel(), getPosition(), modelRotationPosition, rotationAngle);
    }

    @Override
    public void hit() {
        destroyed = true;
    }

    private static Vertex randomValidPosition() {
        final int x = getSparseIntWithinRange(MINIMUM_X_SPAWN_POSITION,
                MAXIMUM_X_SPAWN_POSITION,
                MINIMUM_X_DISTANCE_BETWEEN_PLANES);
        final int y = randomValidYLevel();
        return new Vertex(x, y);
    }

    private static int randomValidYLevel() {
        return getSparseIntWithinRange(MINIMUM_FLIGHT_LEVEL, Y_UPPER_BOUND, MINIMUM_Y_DISTANCE_BETWEEN_PLANES);
    }

    private boolean isOutOfScreen(float positionX) {
        return positionX > X_UPPER_BOUND;
    }

    private void fall(Vertex position) {
        rotationAngle -= 1;
        super.setPosition(position.getX(), position.getY() - 1);
    }
}
