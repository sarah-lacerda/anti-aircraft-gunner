package entity;

import geometry.Vertex;
import model.Model;

import static geometry.configuration.World.WORLD_WIDTH;
import static geometry.configuration.World.X_LOWER_BOUND;
import static geometry.configuration.World.X_UPPER_BOUND;
import static geometry.configuration.World.Y_LOWER_BOUND;
import static util.Randomizer.randomIntWithinRange;

public class Building extends Friendly{
    private boolean destroyed;

    private static final int MINIMUM_X_SPAWN_POSITION = X_LOWER_BOUND + 15;
    private static final int MAXIMUM_X_SPAWN_POSITION = X_UPPER_BOUND;
    private static final int MINIMUM_DISTANCE_BETWEEN_BUILDINGS = WORLD_WIDTH / 5;
    public static final int TOTAL_NUMBER_OF_BUILDINGS = 5;

    public Building(Model model) {
        super(model, null);
        destroyed = false;
        setPosition(randomValidPosition());
    }

    @Override
    public void hit() {
        destroyed = true;
    }

    private Vertex randomValidPosition() {
        final int x = randomIntWithinRange(MINIMUM_X_SPAWN_POSITION,
                MAXIMUM_X_SPAWN_POSITION,
                MINIMUM_DISTANCE_BETWEEN_BUILDINGS);
        final int y = Y_LOWER_BOUND + getModel().getHeight();
        return new Vertex(x, y);
    }
}
