package entity;

import geometry.Dimension;
import geometry.Vertex;

import java.io.IOException;
import java.util.List;

import static model.Model.PROJECTILE_MODEL_FILEPATH;
import static model.Model.createModelFrom;

public class EntityManager {

    private final List<Entity> entities;

    public EntityManager(List<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void renderEntities() {
        entities.forEach(Entity::render);
    }

    public Player getPlayer() {
        return (Player) entities
                .stream()
                .filter(entity -> entity.getClass() == Player.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player is not instantiated"));
    }

    public static Projectile createProjectile(Vertex shooterPosition,
                                              Dimension shooterDimension,
                                              double force,
                                              float angle) {
        try {
            return new Projectile(createModelFrom(PROJECTILE_MODEL_FILEPATH),
                    shooterPosition,
                    shooterDimension,
                    force,
                    angle);
        } catch (IOException e) {
            System.err.println("Error while loading model for projectile entity");
            e.printStackTrace();
        }
        return null;
    }
}
