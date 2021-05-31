package entity;

import geometry.Dimension;
import geometry.Vertex;
import model.Model;

import static geometry.configuration.World.GRAVITY_ACCELERATION;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static physics.Motion.linearMotion;
import static render.Renderer.drawRigidBody;
import static util.Time.deltaTimeInSecondsFrom;
import static util.Time.getCurrentTimeInSeconds;

public class Projectile extends Entity {

    private final Dimension shooterDimension;
    private final Vertex initialPosition;
    private final double initialTime;
    private final float forceX;
    private final float forceY;
    private final float angle;
    private final boolean enemy;

    public Projectile(Model model,
                      Vertex shooterPosition,
                      Dimension shooterDimension,
                      float force,
                      float angle,
                      boolean enemy) {
        super(model, initialPosition(model.getDimension(), shooterPosition, shooterDimension));

        this.shooterDimension = shooterDimension;
        this.initialPosition = initialPosition(model.getDimension(), shooterPosition, shooterDimension);
        this.initialTime = getCurrentTimeInSeconds();

        final double angleFromOrigin = toRadians(90 - angle);
        this.forceX = force * (float) -cos(angleFromOrigin);
        this.forceY = force * (float) sin(angleFromOrigin);
        this.angle = angle;
        this.enemy = enemy;
    }

    public boolean isShotFromEnemy() {
        return enemy;
    }

    @Override
    public Vertex getPosition() {
        final float deltaTime = (float) deltaTimeInSecondsFrom(initialTime);
        return new Vertex(
                initialPosition.getX() + (deltaTime * forceX),
                linearMotion(initialPosition.getY(), forceY, deltaTime, -GRAVITY_ACCELERATION)
        );
    }

    @Override
    public float getPositionX() {
        return getPosition().getX();
    }

    @Override
    public float getPositionY() {
        return getPosition().getY();
    }

    @Override
    public void render() {
        final float projectileYCenter = getDimension().getWidth() / 2.0f;

        drawRigidBody(
                getModel(),
                getPosition(),
                rotationPosition(projectileYCenter, shooterDimension.getHeight()),
                angle
        );
    }

    private static Vertex initialPosition(Dimension projectileDimension,
                                          Vertex shooterPosition,
                                          Dimension shooterDimension) {
        return new Vertex(
                shooterPosition.getX() + (shooterDimension.getWidth() - projectileDimension.getWidth()) / 2.0f,
                shooterPosition.getY() + projectileDimension.getHeight()
        );
    }

    private Vertex rotationPosition(float projectileYCenter, int shooterHeight) {
        return new Vertex(projectileYCenter, -shooterHeight);
    }
}
