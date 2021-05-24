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

public class Application {

    private static final Window WINDOW = Window.getInstance();

    private static final String MAIN_CHARACTER_MODEL_FILE_PATH = "model/test.json";

    public static List<Entity> startInitialGameState() throws IOException {
        List<Entity> entities = new LinkedList<>();
        entities.add(createMainCharacter());
        return entities;
    }

    private static Entity createMainCharacter() throws IOException {
        Model model = FileUtils.getModelFrom(MAIN_CHARACTER_MODEL_FILE_PATH);
        Vertex initialPosition = new Vertex(WINDOW.getMin().getX() + 4, WINDOW.getMin().getY() + 4);
        return new Entity(model, initialPosition);
    }

    public static void main(String[] args) throws IOException {
        // Starts a new JVM if the application was started on macOS without the
        // -XstartOnFirstThread argument.
        if (startNewJvmIfRequired()) {
            System.exit(0);
        }
        WINDOW.setEntityManager(new EntityManager(startInitialGameState()));
        WINDOW.run();
    }
}
