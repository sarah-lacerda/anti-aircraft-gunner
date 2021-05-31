package entity;

import geometry.Vertex;
import model.Model;

import static entity.EntityManager.createProjectile;
import static geometry.configuration.World.WORLD_HEIGHT;
import static geometry.configuration.World.WORLD_WIDTH;
import static geometry.configuration.World.X_LOWER_BOUND;
import static geometry.configuration.World.X_UPPER_BOUND;
import static geometry.configuration.World.Y_UPPER_BOUND;
import static render.Renderer.drawRigidBody;
import static util.Randomizer.randomIntWithinRange;
import static util.Time.getCurrentTimeInSeconds;

public class Airplane extends Enemy {

    private boolean destroyed;
    private boolean ableToShoot;
    private float rotationAngle;

    public static final int MINIMUM_X_SPAWN_POSITION = X_LOWER_BOUND - 100;
    public static final int MAXIMUM_X_SPAWN_POSITION = X_LOWER_BOUND;
    public static final int MINIMUM_FLIGHT_LEVEL = 10;
    public static final int MINIMUM_X_DISTANCE_BETWEEN_PLANES = WORLD_WIDTH / 10;
    public static final int MINIMUM_Y_DISTANCE_BETWEEN_PLANES = WORLD_HEIGHT / 10;
    public static final int TOTAL_NUMBER_OF_ENEMY_PLANES = 10;
    public static final float INITIAL_PROBABILITY_OF_SHOOTING = .001f;
    public static final float MAXIMUM_PROBABILITY_OF_SHOOTING = .01f;

    public Airplane(Model model) {
        super(model, randomValidPosition());
        destroyed = false;
        ableToShoot = true;
        rotationAngle = 0f;
    }

    public Projectile shoot() {
        if (!destroyed && isVisible() && ableToShoot) {
            ableToShoot = false;
            return createProjectile(getPosition(), getDimension(), 1, rotationAngle, true);
        }
        return null;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    private boolean isVisible() {
        return getPositionX() > X_LOWER_BOUND && getPositionX() < X_UPPER_BOUND;
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
                ableToShoot = true;
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

    public static double getProbabilityOfShooting() {
        final double probabilityOfShooting = getCurrentTimeInSeconds() * INITIAL_PROBABILITY_OF_SHOOTING;

        return probabilityOfShooting > MAXIMUM_PROBABILITY_OF_SHOOTING ?
                MAXIMUM_PROBABILITY_OF_SHOOTING : probabilityOfShooting;
    }

    private static Vertex randomValidPosition() {
        final int x = randomIntWithinRange(MINIMUM_X_SPAWN_POSITION,
                MAXIMUM_X_SPAWN_POSITION,
                MINIMUM_X_DISTANCE_BETWEEN_PLANES);
        final int y = randomValidYLevel();
        return new Vertex(x, y);
    }

    private static int randomValidYLevel() {
        return randomIntWithinRange(MINIMUM_FLIGHT_LEVEL, Y_UPPER_BOUND, MINIMUM_Y_DISTANCE_BETWEEN_PLANES);
    }

    private boolean isOutOfScreen(float positionX) {
        return positionX > X_UPPER_BOUND;
    }

    private void fall(Vertex position) {
        rotationAngle -= 1;
        super.setPosition(position.getX(), position.getY() - 1);
    }
}
