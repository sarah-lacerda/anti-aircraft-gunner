package entity;

import geometry.Vertex;
import model.Model;
import util.Color;

import static render.Renderer.FRAMES_PER_SECOND;
import static render.Renderer.drawRigidBody;

public class RocketLauncher extends Entity {

    private final Model model;
    private Vertex playerPosition;
    private final int playerModelWidth;
    private final int playerModelHeight;
    private float rotationAngle;
    private double launcherStrength;

    private final static double LAUNCHER_SECONDS_UNTIL_FULL = 2.00;
    private final static double LAUNCHER_MAX_POWER = 8.00;
    private final static double LAUNCHER_CHARGE_RATE =
            LAUNCHER_MAX_POWER / (LAUNCHER_SECONDS_UNTIL_FULL * FRAMES_PER_SECOND);

    public RocketLauncher(Model model, Vertex playerPosition, int playerModelWidth, int playerModelHeight) {
        super(model, null);
        this.model = model;
        this.playerPosition = playerPosition;
        this.playerModelWidth = playerModelWidth;
        this.playerModelHeight = playerModelHeight;
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

    @Override
    public void render() {
        final double playerYCenter = -playerModelHeight / 2.0;
        final double launcherXCenter = model.getNumberOfColumns() / 2.0;

        final double xScaleFactor = 1;
        final double yScaleFactor = launcherStrength / (LAUNCHER_MAX_POWER / 2.0);

        if (launcherStrength >= LAUNCHER_MAX_POWER) {
            drawRigidBody(
                    model,
                    position(),
                    scalePosition(launcherXCenter),
                    xScaleFactor,
                    yScaleFactor,
                    rotationPosition(launcherXCenter, playerYCenter),
                    rotationAngle,
                    Color.MAROON
            );
        } else {
            drawRigidBody(
                    model,
                    position(),
                    scalePosition(launcherXCenter),
                    xScaleFactor,
                    yScaleFactor,
                    rotationPosition(launcherXCenter, playerYCenter),
                    rotationAngle
            );
        }

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

    private Vertex scalePosition(double launcherXCenter) {
        return new Vertex(launcherXCenter, -model.getNumberOfLines());
    }

    private Vertex rotationPosition(double launcherXCenter, double playerYCenter) {
        final double launcherRotationY = -model.getNumberOfLines() + playerYCenter;
        return new Vertex(launcherXCenter, launcherRotationY);
    }

    private Vertex position() {
        return new Vertex(
                playerPosition.getX() + ((playerModelWidth - model.getNumberOfColumns()) / 2.0)
                ,
                playerPosition.getY() + model.getNumberOfLines()
        );
    }
}
