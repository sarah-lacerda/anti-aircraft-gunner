package entity;

import geometry.Vertex;
import model.Model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static util.Time.getCurrentTimeInSeconds;

public class Projectile extends Entity {

    private double forceX;
    private double forceY;
    private double initialTime;
    private float angle;

    public Projectile(Model model, Vertex shooterPosition, int shooterWidth, double force, float angle) {
        super(
                model,
                new Vertex(
                        shooterPosition.getX() + (shooterWidth - model.getNumberOfColumns()) / 2.0f,
                        shooterPosition.getY() + model.getNumberOfLines()
                )
        );

        final double angleFromOrigin = toRadians(90 - angle);
        this.forceX = force * cos(angleFromOrigin);
        this.forceY = force * sin(angleFromOrigin);
        this.initialTime = getCurrentTimeInSeconds();
        this.angle = angle;
    }
}
