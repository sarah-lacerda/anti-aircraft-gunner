package glfw;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import static java.lang.System.err;
import static javax.imageio.ImageIO.read;
import static util.FileUtils.getFileFromResourceAsStream;

public class Icon {

    private final GLFWImage.Buffer image;

    private Icon(GLFWImage.Buffer image) {
        this.image = image;
    }

    public GLFWImage.Buffer getImage() {
        return image;
    }

    public static Icon loadIconFrom(String filePath) {
        try {
            final BufferedImage bufferedImage = read(getFileFromResourceAsStream(filePath));
            final GLFWImage.Buffer buffer = GLFWImage.malloc(1);
            buffer.put(imageToGLFWImage(bufferedImage));
            buffer.position(0);
            return new Icon(buffer);

        } catch (IOException e) {
            err.println("Unable to load window icon, cause: " + e.getCause());
        }
        return new Icon(null);
    }

    /**
     * Convert the {@link BufferedImage} to the {@link GLFWImage}.
     *
     * Extracted from: https://github.com/jMonkeyEngine/jmonkeyengine
     *  Copyright (c) 2009-2020 jMonkeyEngine
     *  All rights reserved.
     */
    private static GLFWImage imageToGLFWImage(BufferedImage image) {
        if (image.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
            final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
            final Graphics2D graphics = convertedImage.createGraphics();
            final int targetWidth = image.getWidth();
            final int targetHeight = image.getHeight();
            graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
            graphics.dispose();
            image = convertedImage;
        }
        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int colorSpace = image.getRGB(j, i);
                buffer.put((byte) ((colorSpace << 8) >> 24));
                buffer.put((byte) ((colorSpace << 16) >> 24));
                buffer.put((byte) ((colorSpace << 24) >> 24));
                buffer.put((byte) (colorSpace >> 24));
            }
        }
        buffer.flip();
        final GLFWImage result = GLFWImage.create();
        result.set(image.getWidth(), image.getHeight(), buffer);
        return result;
    }
}
