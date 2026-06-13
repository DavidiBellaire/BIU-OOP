/**
 * The CollisionInfo class holds the result of a collision detection and
 * returned by GameEnvironment.getClosestCollision.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructor that creates a CollisionInfo bundling a collision point
     * and the collidable that was hit.
     *
     * @param collisionPoint the point at which the collision occurred.
     * @param collisionObject the collidable that was hit.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}