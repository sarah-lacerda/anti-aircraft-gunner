package glfw;

import entity.EntityManager;
import glfw.listener.KeyListener;
import glfw.listener.WindowResizeListener;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import util.Color;

import java.util.Objects;

import static geometry.configuration.World.setCoordinatePlane;
import static glfw.Actions.handleActions;
import static glfw.Icon.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static render.Renderer.canRender;
import static util.Color.WHITE;
import static util.Time.deltaTimeInSecondsFrom;
import static util.Time.getCurrentTimeInSeconds;

public class Window {
    private int width;
    private int height;
    private long glfwWindowAddress;
    private EntityManager entityManager;

    private static Window INSTANCE = null;

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int MIN_WIDTH = 400;
    private static final int MIN_HEIGHT = 600;
    private static final int MAX_WIDTH = GL_DONT_CARE;
    private static final int MAX_HEIGHT = GL_DONT_CARE;
    private static final String TITLE = "Anti-aircraft Gunner";
    private static final Color BACKGROUND_COLOR = WHITE;
    private static final Icon ICON = loadIconFrom("icon.png");

    private Window() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }

    public static Window getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Window();
        }
        return INSTANCE;
    }

    public void run() {
        init();
        execution();
        terminateGracefully();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        boolean glfwStarted = glfwInit();

        // Throw error and terminate if GLFW initialization fails
        if (!glfwStarted) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowAddress = createAndConfigureWindow();

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindowAddress);


        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        setCoordinatePlane();
        setListeners();
        // Make the window visible
        glfwShowWindow(glfwWindowAddress);
    }


    private void execution() {
        double time = getCurrentTimeInSeconds();
        double elapsedTimeSinceLastRendering = 0;
        while (!glfwWindowShouldClose(glfwWindowAddress)) {
            elapsedTimeSinceLastRendering += deltaTimeInSecondsFrom(time);
            time = getCurrentTimeInSeconds();

            if (canRender(elapsedTimeSinceLastRendering)) {
                render();
                handleActions(entityManager);
                elapsedTimeSinceLastRendering = 0;
            }
            glfwPollEvents();
        }
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(BACKGROUND_COLOR.getRed(), BACKGROUND_COLOR.getGreen(), BACKGROUND_COLOR.getBlue(), 0.0f);

        entityManager.renderEntities();

        glfwSwapBuffers(glfwWindowAddress);
    }

    private long createAndConfigureWindow() {
        // Create the window
        long windowAddress = glfwCreateWindow(this.width, this.height, TITLE, NULL, NULL);

        if (windowAddress == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // glfw window Configuration
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwSetWindowSizeLimits(windowAddress, MIN_WIDTH, MIN_HEIGHT, MAX_WIDTH, MAX_HEIGHT);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        useClientVersion3(false);

        GLFWImage image = GLFWImage.malloc();
        GLFWImage.Buffer imageBuffer = GLFWImage.malloc(1);
        image.set(ICON.getWidth(), ICON.getHeight(), ICON.getImage());
        imageBuffer.put(0, image);
        glfwSetWindowIcon(windowAddress, imageBuffer);

        return windowAddress;
    }

    private void useClientVersion3(boolean use) {
        if (use) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }
    }

    private void setListeners() {
        glfwSetKeyCallback(glfwWindowAddress, KeyListener.getInstance());
        glfwSetWindowSizeCallback(glfwWindowAddress, WindowResizeListener.getInstance());
    }

    private void terminateGracefully() {
        // Free memory upon leaving
        glfwFreeCallbacks(glfwWindowAddress);
        glfwDestroyWindow(glfwWindowAddress);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
}
