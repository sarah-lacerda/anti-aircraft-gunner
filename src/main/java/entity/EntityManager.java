package entity;

import geometry.Dimension;
import geometry.Vertex;
import model.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static geometry.Collision.collisionBetween;
import static geometry.configuration.World.X_LOWER_BOUND;
import static geometry.configuration.World.Y_LOWER_BOUND;
import static java.util.stream.IntStream.range;
import static model.Model.BUILDINGS;
import static model.Model.ENEMY_PLANES;
import static model.Model.PLAYER_MODEL_FILE_PATH;
import static model.Model.PROJECTILE_MODEL_FILEPATH;
import static model.Model.ROCKET_LAUNCHER_MODEL_FILE_PATH;
import static model.Model.createModelFrom;

public class EntityManager {

    private final List<Entity> entities;

    public EntityManager() {
        this.entities = new LinkedList<>();
    }

    public void initializeEntities(int quantityOfPlanes, int quantityOfBuildings) {
        add(createPlayer());

        initializeAirplanesAndBuildings(quantityOfPlanes, quantityOfBuildings);
    }

    public void add(Entity entity) {
        if (entity != null) {
            entities.add(entity);
        }
    }

    public void remove(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void renderEntities() {
        entities.stream().filter(entity -> entity.getClass() != Player.class).forEach(Entity::render);
        getPlayer().render();
    }

    public Player getPlayer() {
        return (Player) entities
                .stream()
                .filter(entity -> entity.getClass() == Player.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player is not instantiated"));
    }

    public List<Airplane> getPlanes() {
        return entities
                .stream()
                .filter(entity -> entity.getClass() == Airplane.class)
                .map(Airplane.class::cast)
                .collect(Collectors.toList());
    }

    public void removeDestroyedEntities() {
        entities.removeIf(entity -> entity.getPositionY() < Y_LOWER_BOUND);
    }

    public static Airplane spawnEnemyPlane() {
        final Model randomModel = createModelFrom(ENEMY_PLANES[new Random().nextInt(ENEMY_PLANES.length)]);
        return new Airplane(randomModel);
    }

    public static Projectile createProjectile(Vertex shooterPosition,
                                              Dimension shooterDimension,
                                              float force,
                                              float angle,
                                              boolean enemy) {
        return new Projectile(createModelFrom(PROJECTILE_MODEL_FILEPATH),
                shooterPosition,
                shooterDimension,
                force,
                angle,
                enemy);
    }

    private static Player createPlayer() {
        final Model playerModel = createModelFrom(PLAYER_MODEL_FILE_PATH);
        final Model rocketLauncherModel = createModelFrom(ROCKET_LAUNCHER_MODEL_FILE_PATH);
        final Vertex initialPosition = new Vertex(
                X_LOWER_BOUND + 3,
                Y_LOWER_BOUND + playerModel.getHeight() + playerModel.getWidth() / 2f
        );
        return new Player(playerModel, rocketLauncherModel, initialPosition);
    }

    private static Building createBuilding() {
        final Model Buildings = createModelFrom(BUILDINGS[new Random().nextInt(BUILDINGS.length)]);

        return new Building(Buildings);
    }

    private void initializeAirplanesAndBuildings(int quantityOfPlanes, int quantityOfBuildings) {
        range(0, Math.max(quantityOfPlanes, quantityOfBuildings)).forEach(count -> {
            if (shouldAddNewAirplane(count, quantityOfPlanes)) {
                add(spawnEnemyPlane());
            }
            if (shouldAddNewBuilding(count, quantityOfBuildings)) {
                spawnBuildingOnValidPosition();
            }
        });
    }

    private void spawnBuildingOnValidPosition() {
        Building building;
        do {
            building = createBuilding();
        } while (collidesWithOthers(building));
        add(building);
    }

    private boolean collidesWithOthers(Entity entity) {
        return entities.stream().anyMatch(other -> collisionBetween(entity, other));
    }

    private boolean shouldAddNewBuilding(int count, int quantityOfBuildings) {
        return count < quantityOfBuildings;
    }

    private boolean shouldAddNewAirplane(int count, int quantityOfAirplanes) {
        return count < quantityOfAirplanes;
    }
}
