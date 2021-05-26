package entity;

import java.util.List;

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
        return (Player) entities.
                stream()
                .filter(entity -> entity.getClass() == Player.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player is not instantiated"));
    }
}
