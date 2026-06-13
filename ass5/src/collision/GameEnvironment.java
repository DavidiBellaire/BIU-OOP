package collision;
import java.util.ArrayList;
import java.util.List;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

/**
 * Holds all the collidable objects in the game environment.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Constructs an empty game environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds the given collidable to the environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Returns the list of all collidables in this environment.
     *
     * @return list of collidables
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * Given a movement trajectory, returns information about the closest
     * collision that will occur with any collidable in this environment.
     * Returns null if no collision will occur.
     *
     * @param trajectory the line representing the object's movement path.
     * @return a CollisionInfo with the closest collision point and collidable,
     *         or null if there is no collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestObject = null;
        double minDistance = Double.POSITIVE_INFINITY;
        List<Collidable> collidablesCopy = new ArrayList<>(this.collidables);
        for (Collidable c : collidablesCopy) {
            for (Rectangle rect : c.getCollisionRectangles()) {
                Point intersection =
                        trajectory.closestIntersectionToStartOfLine(rect);
                if (intersection != null) {
                    double distance = trajectory.start().distance(intersection);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestPoint = intersection;
                        closestObject = c;
                    }
                }
            }
        }
        if (closestPoint == null) {
            return null;
        }
        return new CollisionInfo(closestPoint, closestObject);
    }
    /**
     * Removes the given collidable from the environment.
     *
     * @param c the collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
}