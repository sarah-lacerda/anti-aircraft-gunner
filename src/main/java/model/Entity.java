package model;

import geometry.Vertex;

import static render.Renderer.drawRigidBody;

public class Entity {

    private final Model model;
    private Vertex position;

    public Entity(Model model, Vertex position) {
        this.model = model;
        this.position = position;
    }

    public void render() {
        drawRigidBody(model, position.getX(), position.getY());
    }

    public float getPositionX() {
        return position.getX();
    }

    public float getPositionY() {
        return position.getY();
    }

    public void setPosition(float x, float y) {
        position = new Vertex(x, y);
    }
}
