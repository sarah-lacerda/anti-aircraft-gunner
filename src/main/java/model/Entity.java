package model;

import static render.Renderer.drawRigidBody;

public class Entity {

    private final Model model;
    private float positionX;
    private float positionY;

    public Entity(Model model, int positionX, int positionY) {
        this.model = model;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void render() {
        drawRigidBody(model, positionX, positionY);
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setPosition(float x, float y) {
        positionX = x;
        positionY = y;
    }
}
