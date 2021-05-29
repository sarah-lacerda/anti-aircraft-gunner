package entity;

import geometry.Dimension;
import geometry.Vertex;
import model.Model;

import static java.lang.Math.*;
import static render.Renderer.drawRigidBody;
import static util.Time.getCurrentTimeInSeconds;

public class Projectile extends Entity {

    private final Dimension shooterDimension;
    private double forceX;
    private double forceY;
    private double initialTime;
    private float angle;

    public Projectile(Model model, Vertex shooterPosition, Dimension shooterDimension, double force, float angle) {
        super(model, initialPosition(model.getDimension(), shooterPosition, shooterDimension));

        this.shooterDimension = shooterDimension;

        final double angleFromOrigin = toRadians(90 - angle);
        this.forceX = force * cos(angleFromOrigin);
        this.forceY = force * sin(angleFromOrigin);
        this.initialTime = getCurrentTimeInSeconds();
        this.angle = angle;
    }

    @Override
    public void render() {
        final float projectileYCenter = getModel().getWidth() / 2.0f;

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
        return new Vertex(projectileYCenter, - shooterHeight);
    }
}
