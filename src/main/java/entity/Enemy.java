package entity;

import geometry.Vertex;
import model.Model;

public abstract class Enemy extends Destroyable{
    public Enemy(Model model, Vertex position) {
        super(model, position);
    }
}
