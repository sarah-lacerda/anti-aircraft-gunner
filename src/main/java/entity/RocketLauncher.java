package entity;

import geometry.Dimension;
import geometry.Vertex;
import model.Model;
import util.Color;

import static entity.EntityManager.createProjectile;
import static render.Renderer.FRAMES_PER_SECOND;
import static render.Renderer.drawRigidBody;
import static util.Color.MAROON;

public class RocketLauncher extends Entity {

    private final Model model;
    private Vertex playerPosition;
    private final Dimension playerDimension;
    private float rotationAngle;
    private float launcherStrength;

    private final static float LAUNCHER_SECONDS_UNTIL_FULL = 2.00f;
    private final static float LAUNCHER_MAX_POWER = 8.00f;
    private final static float LAUNCHER_CHARGE_RATE =
            LAUNCHER_MAX_POWER / (LAUNCHER_SECONDS_UNTIL_FULL * FRAMES_PER_SECOND);

    public RocketLauncher(Model model, Vertex playerPosition, Dimension playerDimension) {
        super(model, null);
        this.model = model;
        this.playerPosition = playerPosition;
        this.playerDimension = playerDimension;
        this.rotationAngle = 0;
    }

    public void setPlayerPosition(Vertex playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public void unload() {
        launcherStrength = 0;
    }

    public void charge() {
        if (launcherStrength <= LAUNCHER_MAX_POWER) {
            launcherStrength += LAUNCHER_CHARGE_RATE;
        }
    }

    public Projectile shoot() {
        return createProjectile(playerPosition, playerDimension, launcherStrength, rotationAngle);
    }

    @Override
    public void render() {
        final float playerYCenter = - playerDimension.getHeight() / 2.0f;
        final float launcherXCenter = model.getWidth() / 2.0f;

        final float xScaleFactor = 1;
        final float yScaleFactor = launcherStrength / (LAUNCHER_MAX_POWER / 2.0f);

        final Color colorOverride = launcherStrength >= LAUNCHER_MAX_POWER ? MAROON : null;

        drawRigidBody(
                model,
                position(),
                scalePosition(launcherXCenter),
                xScaleFactor,
                yScaleFactor,
                rotationPosition(launcherXCenter, playerYCenter),
                rotationAngle,
                colorOverride
        );
    }

    @Override
    public Vertex getPosition() {
        return position();
    }

    @Override
    public float getPositionX() {
        return position().getX();
    }

    @Override
    public float getPositionY() {
        return position().getY();
    }

    private Vertex scalePosition(float launcherXCenter) {
        return new Vertex(launcherXCenter, - model.getHeight());
    }

    private Vertex rotationPosition(float launcherXCenter, float playerYCenter) {
        final float launcherRotationY = - model.getHeight() + playerYCenter;
        return new Vertex(launcherXCenter, launcherRotationY);
    }

    private Vertex position() {
        return new Vertex(
                playerPosition.getX() + ((playerDimension.getWidth() - model.getWidth()) / 2.0f)
                ,
                playerPosition.getY() + model.getHeight()
        );
    }
}
