package entity;

import geometry.Vertex;
import model.Model;

import static geometry.configuration.World.X_LOWER_BOUND;
import static geometry.configuration.World.X_UPPER_BOUND;
import static render.Renderer.drawRigidBody;

public class Player extends Friendly {

    private final RocketLauncher rocketLauncher;
    private float rotationAngle;

    private final static float MAX_ANGLE_ROTATION = 80f;

    public Player(Model playerModel, Model rocketLauncherModel, Vertex position) {
        super(playerModel, position);

        this.rotationAngle = 0;
        rocketLauncher = new RocketLauncher(
                rocketLauncherModel,
                position,
                playerModel.getDimension()
        );
    }

    public void unloadRocketLauncher() {
        rocketLauncher.unload();
    }

    public void chargeRocketLauncher() {
        rocketLauncher.charge();
    }

    public Projectile shoot() {
        return rocketLauncher.shoot();
    }

    public void rotate(float angle) {
        if (this.rotationAngle < 0) {
            this.rotationAngle = Math.max(this.rotationAngle + angle, -MAX_ANGLE_ROTATION);
        } else {
            this.rotationAngle = Math.min(this.rotationAngle + angle, MAX_ANGLE_ROTATION);
        }
        rocketLauncher.setRotationAngle(this.rotationAngle);
    }

    @Override
    public void hit() {
        // TODO: Implement me!
    }

    @Override
    public void setPosition(float x, float y) {
        final Vertex position = new Vertex(x, y);
        setPosition(position);
        rocketLauncher.setPlayerPosition(position);
    }

    @Override
    public void setPosition(Vertex position) {
        if (isPositionValid(position)) {
            super.setPosition(position);
            rocketLauncher.setPlayerPosition(position);
        }
    }

    @Override
    public void render() {
        final float modelXCenter = getDimension().getWidth() / 2.0f;
        final float modelYCenter = -getDimension().getHeight() / 2.0f;
        final Vertex modelRotationPosition = new Vertex(modelXCenter, modelYCenter);

        rocketLauncher.render();
        drawRigidBody(
                getModel(),
                getPosition(),
                modelRotationPosition,
                rotationAngle
        );
    }

    private boolean isPositionValid(Vertex position) {
        return position.getX() > X_LOWER_BOUND && position.getX() < X_UPPER_BOUND - getDimension().getWidth();
    }
}
