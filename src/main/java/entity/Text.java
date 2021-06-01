package entity;

import geometry.Vertex;
import model.Model;
import render.Renderer;

public class Text extends Entity {
    public Text(Model model, Vertex position) {
        super(model, position);
    }

    @Override
    public void render() {
        Renderer.drawRigidBody(getModel(),
                getPosition(),
                new Vertex(0, 0),
                2,
                2,
                null,
                0);
    }
}
