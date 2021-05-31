import entity.EntityManager;
import glfw.Window;

import java.io.IOException;

import static de.damios.guacamole.gdx.StartOnFirstThreadHelper.startNewJvmIfRequired;
import static entity.Building.TOTAL_NUMBER_OF_BUILDINGS;
import static entity.Airplane.TOTAL_NUMBER_OF_ENEMY_PLANES;

public class Game {

    private static final Window WINDOW = Window.getInstance();

    public static EntityManager createEntityManager() {
        final EntityManager entityManager = new EntityManager();
        entityManager.initializeEntities(TOTAL_NUMBER_OF_ENEMY_PLANES, TOTAL_NUMBER_OF_BUILDINGS);
        return entityManager;
    }

    public static void main(String[] args) throws IOException {
        // Starts a new JVM if the application was started on macOS without the
        // -XstartOnFirstThread argument.
        if (startNewJvmIfRequired()) {
            System.exit(0);
        }
        WINDOW.setEntityManager(createEntityManager());
        WINDOW.run();
    }
}
