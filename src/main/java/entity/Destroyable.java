package entity;

import geometry.Vertex;
import model.Model;

public abstract class Destroyable extends Entity{
    public Destroyable(Model model, Vertex position) {
        super(model, position);
    }

    public abstract void hit();
}
