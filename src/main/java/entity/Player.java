package entity;

import geometry.Vertex;
import model.Model;

import static render.Renderer.drawRigidBody;

public class Player extends Entity{

    float rotationAngle;

    public Player(Model model, Vertex position) {
        super(model, position);
        this.rotationAngle = 0;
    }

    public void rotate(float angle) {
        this.rotationAngle += angle;
    }

    @Override
    public void render() {
        drawRigidBody(super.getModel(), super.getPosition(), rotationAngle);
    }
}
