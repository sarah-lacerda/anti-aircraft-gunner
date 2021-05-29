package glfw;

import entity.EntityManager;
import entity.Player;
import geometry.Vertex;
import glfw.listener.KeyListener;

import static entity.Entity.UNIT_OF_MOVEMENT_PER_FRAME;
import static org.lwjgl.glfw.GLFW.*;

public class Actions {

    public static void handleActions(EntityManager entityManager) {
        handleMainCharacterMovement(entityManager);
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

            entityManager.addEntity(player.shoot());
            player.unloadRocketLauncher();

        } else if (keyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            player.chargeRocketLauncher();
        }
        if (!KeyListener.getInstance().isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            player.unloadRocketLauncher();
        }
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
