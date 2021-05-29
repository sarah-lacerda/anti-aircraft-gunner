import entity.Entity;
import entity.EntityManager;
import entity.Player;
import geometry.Vertex;
import glfw.Window;
import model.Model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static de.damios.guacamole.gdx.StartOnFirstThreadHelper.startNewJvmIfRequired;
import static model.Model.createModelFrom;

public class Game {

    private static final Window WINDOW = Window.getInstance();

    private static final String MAIN_CHARACTER_MODEL_FILE_PATH = "model/player.json";
    private static final String ROCKET_LAUNCHER_MODEL_FILE_PATH = "model/rocketLauncher.json";
    private static final String PROJECTILE_MODEL_FILE_PATH = "model/projectile.json";

    public static List<Entity> createEntities() throws IOException {
        List<Entity> entities = new LinkedList<>();
        entities.add(createMainCharacter());
        Entity testProjectile = new Entity(createModelFrom(PROJECTILE_MODEL_FILE_PATH), new Vertex(-10f, -10f));
        entities.add(testProjectile);

        return entities;
    }

    private static Entity createMainCharacter() throws IOException {
        Model playerModel = createModelFrom(MAIN_CHARACTER_MODEL_FILE_PATH);
        Model rocketLauncherModel = createModelFrom(ROCKET_LAUNCHER_MODEL_FILE_PATH);
        Vertex initialPosition = new Vertex(0, 0);
        return new Player(playerModel, rocketLauncherModel, initialPosition);
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
