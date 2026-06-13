import java.util.List;

/**
 * Represents an object that can be collided with during the game.
 */
public interface Collidable {

    /**
     * Returns the rectangular collision shape of this object.
     *
     * @return the collision Rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies this object of a collision and returns the new velocity after the
     * hit.
     *
     * @param collisionPoint  the point at which the collision occurred
     * @param currentVelocity the velocity before the collision
     * @return the new velocity after the collision
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);

    /**
     * Returns a list of all collision rectangles of this object.
     *
     * @return list of collision rectangles
     */
    default List<Rectangle> getCollisionRectangles() {
        return List.of(getCollisionRectangle());
    }
}