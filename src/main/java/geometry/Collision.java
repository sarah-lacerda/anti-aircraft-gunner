package geometry;

import entity.Entity;

public class Collision {

    public static boolean collisionBetween(Entity entity1, Entity entity2) {
        final Vertex entity1Center = new Vertex(
                entity1.getPositionX() + (entity1.getDimension().getWidth() / 2f),
                entity1.getPositionY() - (entity1.getDimension().getHeight() / 2f));
        final Vertex entity2Center = new Vertex(
                entity2.getPositionX() + (entity1.getDimension().getWidth() / 2f),
                entity2.getPositionY() - (entity1.getDimension().getHeight() / 2f));


        if (distanceBetween(entity1Center.getX(), entity2Center.getX()) > ((entity1.getDimension().getWidth() + entity2.getDimension().getWidth()) / 2f)) {
            return false;
        }
        if (distanceBetween(entity1Center.getY(), entity2Center.getY()) > ((entity1.getDimension().getHeight() + entity2.getDimension().getHeight()) / 2f)) {
            return false;
        }
        return true;
    }

    private static float distanceBetween(float c1, float c2) {
        return Math.abs(c1 - c2);
    }
}
