import entity.Entity;
import entity.EntityManager;
import glfw.Window;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static de.damios.guacamole.gdx.StartOnFirstThreadHelper.startNewJvmIfRequired;
import static entity.EntityManager.createPlayer;

public class Game {

    private static final Window WINDOW = Window.getInstance();

    public static List<Entity> createEntities() throws IOException {
        List<Entity> entities = new LinkedList<>();
        entities.add(createPlayer());
        return entities;
    }

    public static void main(String[] args) throws IOException {
        // Starts a new JVM if the application was started on macOS without the
        // -XstartOnFirstThread argument.
        if (startNewJvmIfRequired()) {
            System.exit(0);
        }
        WINDOW.setEntityManager(new EntityManager(createEntities()));
        WINDOW.run();
    }
}
