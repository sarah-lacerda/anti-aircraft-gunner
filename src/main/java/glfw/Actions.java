package glfw;

import entity.Airplane;
import entity.Building;
import entity.Destroyable;
import entity.Entity;
import entity.EntityManager;
import entity.Player;
import entity.Projectile;
import geometry.Vertex;
import glfw.listener.KeyListener;

import static entity.Airplane.getProbabilityOfShooting;
import static entity.Entity.UNIT_OF_MOVEMENT_PER_FRAME;
import static entity.EntityManager.spawnEnemyPlane;
import static entity.Projectile.canBeHitBy;
import static entity.Projectile.isProjectile;
import static geometry.Collision.collisionBetween;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static util.Randomizer.randomBoolean;

public class Actions {

    private Actions() {
    }

    public static void handleActions(EntityManager entityManager) {
        handleEnemyMovement(entityManager);
        handleEnemyShooting(entityManager);
        handleMainCharacterMovement(entityManager);
        handleBuildingDestruction(entityManager);
        handleCollisions(entityManager);
        handleGameOver(entityManager);
        entityManager.removeDestroyedEntities();
    }

    private static void handleGameOver(EntityManager entityManager) {
        if (entityManager.getBuildings().size() == 0 || entityManager.getPlayer().getHealth() == 0) {
            // TODO: Implement game over text
            // entityManager.add(createGameOverText());
        }
        if (entityManager.getAirplanes().size() == 0) {
            // TODO: Implement you win
            // entityManager.add(createYouWinText());
        }
    }

    private static void handleBuildingDestruction(EntityManager entityManager) {
        entityManager.getBuildings().stream().filter(Building::isDestroyed).forEach(Building::fall);
    }

    private static void handleEnemyShooting(EntityManager entityManager) {
        if (randomBoolean(getProbabilityOfShooting())) {
            final Airplane randomAirplane = entityManager.getRandomAirplane();
            if (randomAirplane != null) {
                entityManager.add(randomAirplane.shoot());
            }
        }
    }

    private static void handleEnemyMovement(EntityManager entityManager) {
        entityManager.getAirplanes().forEach(airplane -> airplane.setPosition(moveRightFrom(airplane.getPosition())));
    }

    private static void handleMainCharacterMovement(EntityManager entityManager) {
        final Player player = entityManager.getPlayer();
        final Vertex currentPosition = player.getPosition();

        final KeyListener keyListener = KeyListener.getInstance();

        if (keyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            player.setPosition(moveLeftFrom(currentPosition));
        }
        if (keyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            player.setPosition(moveRightFrom(currentPosition));
        }
        if (keyListener.isKeyPressed(GLFW_KEY_UP)) {
            player.rotate(1);
        }
        if (keyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            player.rotate(-1);
        }
        if (keyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT) &&
                KeyListener.getInstance().isKeyPressed(GLFW_KEY_SPACE)) {

            entityManager.add(player.shoot());
            player.unloadRocketLauncher();

        } else if (keyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            player.chargeRocketLauncher();
        }
        if (!keyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            player.unloadRocketLauncher();
        }
    }

    private static void handleCollisions(EntityManager entityManager) {
        for (int i = 0; i < entityManager.getEntities().size(); i++) {
            for (int j = i + 1; j < entityManager.getEntities().size(); j++) {
                final Entity entity1 = entityManager.getEntities().get(i);
                final Entity entity2 = entityManager.getEntities().get(j);
                if (collisionBetween(entity1, entity2)) {
                    if (collisionBetweenPlanes(entity1, entity2)) {
                        removeAndCreateNewAirplane((Airplane) entity1, entityManager);
                    } else {
                        entityManager.remove(registerCollisionBetween(entity1, entity2));
                    }
                }
            }
        }
    }

    private static void removeAndCreateNewAirplane(Airplane airplane, EntityManager entityManager) {
        entityManager.remove(airplane);
        entityManager.add(spawnEnemyPlane());
    }

    private static Entity registerCollisionBetween(Entity entity1, Entity entity2) {
        if (isProjectile(entity1)) {
            if (canBeHitBy((Projectile) entity1, entity2)) {
                ((Destroyable) entity2).hit();
                return entity1;
            }
        }
        if (isProjectile(entity2)) {
            if (canBeHitBy((Projectile) entity2, entity1)) {
                ((Destroyable) entity1).hit();
                return entity2;
            }
        }
        return null;
    }

    private static boolean collisionBetweenPlanes(Entity entity1, Entity entity2) {
        return entity1.getClass() == Airplane.class &&
                entity2.getClass() == Airplane.class &&
                !((Airplane) entity1).isDestroyed() &&
                !((Airplane) entity2).isDestroyed();
    }

    private static Vertex moveLeftFrom(Vertex currentPosition) {
        return new Vertex(
                currentPosition.getX() - UNIT_OF_MOVEMENT_PER_FRAME,
                currentPosition.getY()
        );
    }

    private static Vertex moveRightFrom(Vertex currentPosition) {
        return new Vertex(
                currentPosition.getX() + UNIT_OF_MOVEMENT_PER_FRAME,
                currentPosition.getY()
        );
    }
}
