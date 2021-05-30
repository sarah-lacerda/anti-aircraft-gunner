package entity;

import geometry.Vertex;
import model.Model;

import static geometry.configuration.World.WORLD_HEIGHT;
import static geometry.configuration.World.WORLD_WIDTH;
import static geometry.configuration.World.X_LOWER_BOUND;
import static geometry.configuration.World.X_UPPER_BOUND;
import static geometry.configuration.World.Y_UPPER_BOUND;
import static util.Randomizer.getSparseIntWithinRange;

public class Plane extends Entity {
    public static final int MINIMUM_FLIGHT_LEVEL = 10;
    public static final int MINIMUM_X_DISTANCE_BETWEEN_PLANES = WORLD_WIDTH / 10;
    public static final int MINIMUM_Y_DISTANCE_BETWEEN_PLANES = WORLD_HEIGHT / 10;
    public static final int TOTAL_NUMBER_OF_ENEMY_PLANES = 10;

    public Plane(Model model) {
        super(model, randomValidPosition());
    }

    @Override
    public void setPosition(float x, float y) {
        if (isOutOfScreen(x)) {
            super.setPosition(randomValidPosition());
        } else {
            super.setPosition(x, y);
        }
    }

    @Override
    public void setPosition(Vertex position) {
        if (isOutOfScreen(position.getX())) {
            super.setPosition(randomValidPosition());
        } else {
            super.setPosition(position);
        }
    }

    public static Vertex randomValidPosition() {
        final int x = getSparseIntWithinRange(X_LOWER_BOUND - 100,
                X_LOWER_BOUND,
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
}
