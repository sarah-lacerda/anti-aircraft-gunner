package glfw;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_load;

public class Icon {

    private final ByteBuffer image;
    private final int width, height;

    public Icon(ByteBuffer image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public ByteBuffer getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static Icon loadIconFrom(String filePath) {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = stbi_load(filePath, w, h, comp, 4);
            if (image == null) {
                 throw new IllegalStateException("Could not load game icon from resources.");
            }
            width = w.get();
            height = h.get();
        }
        return new Icon(image, width, height);
    }
}
