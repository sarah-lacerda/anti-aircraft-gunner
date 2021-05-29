package entity;

import geometry.Dimension;
import geometry.Vertex;
import model.Model;

import java.util.List;

import static geometry.configuration.World.X_LOWER_BOUND;
import static geometry.configuration.World.Y_LOWER_BOUND;
import static model.Model.PLAYER_MODEL_FILE_PATH;
import static model.Model.PROJECTILE_MODEL_FILEPATH;
import static model.Model.ROCKET_LAUNCHER_MODEL_FILE_PATH;
import static model.Model.createModelFrom;

public class EntityManager {

    private final List<Entity> entities;

    public EntityManager(List<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity entity) {
        if (entity != null) {
            entities.add(entity);
        }
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

    public static Player createPlayer() {
        final Model playerModel = createModelFrom(PLAYER_MODEL_FILE_PATH);
        final Model rocketLauncherModel = createModelFrom(ROCKET_LAUNCHER_MODEL_FILE_PATH);
        final Vertex initialPosition = new Vertex(
                X_LOWER_BOUND + playerModel.getWidth(),
                Y_LOWER_BOUND + playerModel.getHeight() + playerModel.getWidth() / 2f
        );
        return new Player(playerModel, rocketLauncherModel, initialPosition);
    }

    public static Projectile createProjectile(Vertex shooterPosition,
                                              Dimension shooterDimension,
                                              float force,
                                              float angle) {
        return new Projectile(createModelFrom(PROJECTILE_MODEL_FILEPATH),
                shooterPosition,
                shooterDimension,
                force,
                angle);
    }
}
