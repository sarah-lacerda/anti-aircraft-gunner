package entity;

import geometry.Vertex;
import model.Model;

import static render.Renderer.drawRigidBody;

public class Player extends Entity {

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

    public Entity shoot() {
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
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        rocketLauncher.setPlayerPosition(new Vertex(x, y));
    }

    @Override
    public void setPosition(Vertex position) {
        super.setPosition(position);
        rocketLauncher.setPlayerPosition(position);
    }

    @Override
    public void render() {
        final float modelXCenter = getModel().getWidth() / 2.0f;
        final float modelYCenter = -getModel().getHeight() / 2.0f;
        final Vertex modelRotationPosition = new Vertex(modelXCenter, modelYCenter);

        rocketLauncher.render();
        drawRigidBody(
                super.getModel(),
                super.getPosition(),
                modelRotationPosition,
                rotationAngle
        );
    }
}
