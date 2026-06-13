package collision;
import java.util.List;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Ball;

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
     * @param hitter          the ball that is doing the hitting
     * @return the new velocity after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Returns a list of all collision rectangles of this object.
     *
     * @return list of collision rectangles
     */
    default List<Rectangle> getCollisionRectangles() {
        return List.of(getCollisionRectangle());
    }
}