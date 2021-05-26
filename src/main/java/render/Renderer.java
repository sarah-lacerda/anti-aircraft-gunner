package render;


import model.Model;
import util.Color;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Renderer {

    public static final int FRAMES_PER_SECOND = 30;

    public static void drawRigidBody(Model model, float positionX, float positionY) {
        final String[] matrixRepresentation = model.getMatrix();
        final int numberOfColumnsForMatrix = model.getNumberOfColumns();

        glPushMatrix();
        glTranslatef(positionX, positionY, 0);
        for (int cellId = 1; cellId <= matrixRepresentation.length; cellId++) {
            drawCell(getColorForCell(cellId, matrixRepresentation));
            glTranslatef(1, 0, 0);
            if (isLastCellForCurrentLine(numberOfColumnsForMatrix, cellId)) {
                glTranslatef(-numberOfColumnsForMatrix, -1, 0);
            }
        }
        glPopMatrix();
    }

    public static boolean canRender(double elapsedTimeSinceLastRendering) {
        return elapsedTimeSinceLastRendering > 1.0 / FRAMES_PER_SECOND;
    }

    private static void drawCell(String color) {
        glBegin(GL_QUADS);
        Color rgb = Color.from(color);
        glColor3f(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
        glVertex2f(0, 0);
        glVertex2f(0, 1);
        glVertex2f(1, 1);
        glVertex2f(1, 0);
        glEnd();
    }

    private static boolean isLastCellForCurrentLine(int columns, int cellId) {
        return cellId % columns == 0;
    }

    private static String getColorForCell(int i, String[] matrix) {
        return matrix[i - 1];
    }

}
