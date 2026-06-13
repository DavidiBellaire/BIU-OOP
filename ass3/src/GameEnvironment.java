import java.util.ArrayList;
import java.util.List;

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
     * Returns the closest collision that will occur along the given line.
     * If no collision occurs, returns null.
     *
     * @param trajectory the line representing the object's path
     * @return the closest CollisionInfo or null if there is no collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestObject = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Collidable c : this.collidables) {
            for (Rectangle rect : c.getCollisionRectangles()) {
                Point intersection = trajectory.closestIntersectionToStartOfLine(rect);
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
}