import geometry.Vertex;
import glfw.Window;
import model.Entity;
import model.EntityManager;
import model.Model;
import util.FileUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static de.damios.guacamole.gdx.StartOnFirstThreadHelper.startNewJvmIfRequired;
import static geometry.configuration.CoordinatePlane.X_LOWER_BOUND;

public class Game {

    private static final Window WINDOW = Window.getInstance();

    private static final String MAIN_CHARACTER_MODEL_FILE_PATH = "model/test.json";

    public static List<Entity> createEntities() throws IOException {
        List<Entity> entities = new LinkedList<>();
        entities.add(createMainCharacter());
        return entities;
    }

    private static Entity createMainCharacter() throws IOException {
        Model model = FileUtils.getModelFrom(MAIN_CHARACTER_MODEL_FILE_PATH);
        Vertex initialPosition = new Vertex(X_LOWER_BOUND + 4, X_LOWER_BOUND + 4);
        return new Entity(model, initialPosition);
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
