package entity;

import geometry.Dimension;
import geometry.Vertex;
import model.Model;

import static geometry.configuration.World.WORLD_WIDTH;
import static render.Renderer.FRAMES_PER_SECOND;
import static render.Renderer.drawRigidBody;

public class Entity {

    private final Model model;
    private Vertex position;

    /*
     * Speed in which an Entity will move. Unit is based on seconds it will take for
     * an Entity to cross the entire screen length regardless of the defined framerate.
     *
     * SPEED = {UNIT_OF_SCREEN_WIDTH} / {TIME_IN_SECONDS_TO_CROSS_WIDTH}
     * */
    public final static float SPEED = 1f / 15f;

    public final static float UNIT_OF_MOVEMENT_PER_FRAME = ((float) WORLD_WIDTH / (float) FRAMES_PER_SECOND) * SPEED;

    public Entity(Model model, Vertex position) {
        this.model = model;
        this.position = position;
    }

    public void render() {
        drawRigidBody(model, position);
    }

    public Vertex getPosition() {
        return position;
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

    public void setPosition(Vertex position) {
        this.position = position;
    }

    public Dimension getDimension() {
        return model.getDimension();
    }

    protected Model getModel() {
        return model;
    }
}
