package render;


import geometry.Vertex;
import model.Model;
import util.Color;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static util.Color.BLANK;
import static util.Color.from;

public class Renderer {

    public static final int FRAMES_PER_SECOND = 30;

    public static void drawRigidBody(
            Model model,
            Vertex position,
            Vertex scalePosition,
            float xScaleFactor,
            float yScaleFactor,
            Vertex rotationPosition,
            float rotationAngle
    ) {
        drawRigidBody(
                model,
                position,
                scalePosition,
                xScaleFactor,
                yScaleFactor,
                rotationPosition,
                rotationAngle,
                null
        );
    }

    public static void drawRigidBody(
            Model model,
            Vertex position,
            Vertex rotationPosition,
            float rotationAngle
    ) {
        drawRigidBody(
                model,
                position,
                null,
                0.0f,
                0.0f,
                rotationPosition,
                rotationAngle
        );
    }

    public static void drawRigidBody(Model model, Vertex position) {
        drawRigidBody(model, position, null, 0);
    }

    public static void drawRigidBody(
            Model model,
            Vertex position,
            Vertex scalePosition,
            float xScaleFactor,
            float yScaleFactor,
            Vertex rotationPosition,
            float rotationAngle,
            Color staticColor
    ) {
        final String[] matrixRepresentation = model.getMatrix();
        final int numberOfColumnsForMatrix = model.getNumberOfColumns();

        glPushMatrix();

        glTranslated(position.getX(), position.getY(), 0);
        rotateAround(rotationPosition, rotationAngle);
        scale(scalePosition, xScaleFactor, yScaleFactor);
        for (int cellId = 1; cellId <= matrixRepresentation.length; cellId++) {
            drawCell(getColorForCell(cellId, matrixRepresentation, staticColor));
            glTranslatef(1, 0, 0);
            if (isLastCellForCurrentLine(numberOfColumnsForMatrix, cellId)) {
                glTranslatef(-numberOfColumnsForMatrix, -1, 0);
            }
        }
        glPopMatrix();
    }

    public static boolean canRender(double elapsedTimeSinceLastRendering) {
        return elapsedTimeSinceLastRendering > 1.0f / FRAMES_PER_SECOND;
    }

    private static void scale(Vertex position, float xScaleFactor, float yScaleFactor) {
        if (position != null) {
            glTranslatef(position.getX(), position.getY(), 0);
            glScalef(xScaleFactor, yScaleFactor, 0);
            glTranslatef(-position.getX(), -position.getY(), 0);
        }
    }

    private static void rotateAround(Vertex vertex, float angle) {
        if (vertex != null) {
            glTranslatef(vertex.getX(), vertex.getY(), 0);
            glRotatef(angle, 0, 0, 1);
            glTranslatef(-vertex.getX(), -vertex.getY(), 0);
        }
    }

    private static void drawCell(Color color) {
        if (!color.equals(BLANK)) {
            glBegin(GL_QUADS);
            glColor3f(color.getRed(), color.getGreen(), color.getBlue());
            glVertex2f(0, 0);
            glVertex2f(0, -1f);
            glVertex2f(1f, -1f);
            glVertex2f(1f, 0);
            glEnd();
        }
    }

    private static boolean isLastCellForCurrentLine(int columns, int cellId) {
        return cellId % columns == 0;
    }

    private static Color getColorForCell(int i, String[] matrix, Color overrideColor) {
        Color color = from(matrix[i - 1]);
        if (overrideColor != null && !color.equals(BLANK)) {
            return overrideColor;
        }
        return color;
    }

}
