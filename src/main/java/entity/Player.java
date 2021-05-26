package entity;

import geometry.Vertex;
import model.Model;

import static render.Renderer.drawRigidBody;

public class Player extends Entity {

    private float rotationAngle;

    private final static float MAX_ANGLE_ROTATION = 80;

    public Player(Model model, Vertex position) {
        super(model, position);
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
        drawRigidBody(super.getModel(), super.getPosition(), rotationAngle);
    }
}
