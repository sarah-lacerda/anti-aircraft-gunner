package entity;

import geometry.Vertex;
import model.Model;

import static render.Renderer.drawRigidBody;

public class Player extends Entity {

    private final Model rocketLauncherModel;
    private float rotationAngle;

    private final static float MAX_ANGLE_ROTATION = 80;

    public Player(Model playerModel, Model rocketLauncherModel, Vertex position) {
        super(playerModel, position);
        this.rocketLauncherModel = rocketLauncherModel;
        this.rotationAngle = 0;
    }

    public void rotate(float angle) {
        if (this.rotationAngle < 0) {
            this.rotationAngle = Math.max(this.rotationAngle + angle, -MAX_ANGLE_ROTATION);
        } else {
            this.rotationAngle = Math.min(this.rotationAngle + angle, MAX_ANGLE_ROTATION);
        }
    }

    @Override
    public void render() {
        final double modelXCenter = super.getModel().getNumberOfColumns() / 2.0;
        final double modelYCenter = -super.getModel().getNumberOfLines() / 2.0;
        final Vertex modelRotationPosition = new Vertex(modelXCenter, modelYCenter);

        final double launcherRotationX = rocketLauncherModel.getNumberOfColumns() / 2.0;
        final double launcherRotationY = -rocketLauncherModel.getNumberOfLines() + modelYCenter;
        final Vertex rocketLauncherRotationPosition = new Vertex(launcherRotationX, launcherRotationY);

        drawRigidBody(rocketLauncherModel, rocketLauncherPosition(), rocketLauncherRotationPosition, rotationAngle);
        drawRigidBody(super.getModel(), super.getPosition(), modelRotationPosition, rotationAngle);
    }

    private Vertex rocketLauncherPosition() {
        return new Vertex(
                super.getPositionX()
                        + ((super.getModel().getNumberOfColumns() - rocketLauncherModel.getNumberOfColumns())
                        / 2.0
                ),
                super.getPositionY()
                        + rocketLauncherModel.getNumberOfLines()
        );
    }
}
