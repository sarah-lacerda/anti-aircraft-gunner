import listener.KeyListener;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private String title;

    private static Window INSTANCE = null;
    private long glfwWindowAddress;

    Scene scene = new Scene() {
        @Override
        public void update(float deltaTime) {

        }
    };

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Anti-aircraft Gunner";
    }

    public static Window getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Window();
        }
        return INSTANCE;
    }

    public void run() {
        init();
        loop();
        terminateGracefully();
    }

    public void init() {
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

        setListeners();

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindowAddress);

        // Make the window visible
        glfwShowWindow(glfwWindowAddress);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        scene.init();
    }


    public void loop() {
        float beginTime = Time.getCurrentTimeInSeconds();
        float endTime;
        float dt = -1.0f;
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key
        while (!glfwWindowShouldClose(glfwWindowAddress)) {

         /*   glMatrixMode(GL_PROJECTION);
            glLoadIdentity(); // Resets any previous projection matrices
            glOrtho(0, 640, 0, 480, 1, -1);
            glMatrixMode(GL_MODELVIEW);
*/
            // clear the framebuffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            render();
            if (dt >= 0) {
                scene.update(dt);
            }

            // swap the color buffers
            glfwSwapBuffers(glfwWindowAddress);
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            endTime = Time.getCurrentTimeInSeconds();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

    }

    private void render() {
    }

    private long createAndConfigureWindow() {
        // Window Configuration
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // the window will be resizable

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

      //  glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);

        // Create the window
        long windowAddress = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

         if (windowAddress == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        return windowAddress;
    }

    private void setListeners() {
        glfwSetKeyCallback(glfwWindowAddress, KeyListener::keyCallback);
    }

    private void terminateGracefully() {
        // Free the memory upon leaving
        glfwFreeCallbacks(glfwWindowAddress);
        glfwDestroyWindow(glfwWindowAddress);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
}
