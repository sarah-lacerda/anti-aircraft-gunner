package entity;

import geometry.Vertex;
import model.Model;

import static geometry.configuration.World.*;
import static geometry.configuration.World.WORLD_HEIGHT;
import static render.Renderer.drawRigidBody;
import static util.Randomizer.getSparseIntWithinRange;

public class Building extends Friendly{
    private boolean destroyed;

    public Building(Model model) {
        super(model, randomValidPosition());
        destroyed = false;
    }

    @Override
    public void hit() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    private static Vertex randomValidPosition() {
        final int x = getSparseIntWithinRange(0,
                0,
                0);
        final int y = getSparseIntWithinRange(0,
                0,
                0);
        return new Vertex(x, y);
    }
}
