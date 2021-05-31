package entity;

import geometry.Vertex;
import model.Model;

public abstract class Friendly extends Destroyable {
    public Friendly(Model model, Vertex position) {
        super(model, position);
    }
}
