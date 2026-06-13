package sprites;
import biuoop.DrawSurface;
import java.awt.Color;
import geometry.Point;
import geometry.Line;
import geometry.Velocity;
import geometry.Rectangle;
import collision.GameEnvironment;
import collision.CollisionInfo;
import collision.Collidable;
import game.Game;

/**
 * The Ball class represents a moving circle with a center point, radius,
 * and color. The ball implements Sprite, draw itself and know to update its
 * position each frame.
 * Movement uses the game's GameEnvironment to detect collisions with any
 * Collidable and bounce
 * off them.
 */
public class Ball implements Sprite {
    private static final double THRESHOLD = 0.00001;

    private Point center;
    private int r;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

    /**
     * Default radius.
     */
    public static final int DEFAULT_RADIUS = 5;

    /**
     * Constructor that creates a new ball with the given center, radius,
     * and color. Velocity is initialized to zero.
     *
     * @param center the center point of the ball.
     * @param r      the radius of the ball.
     * @param color  the color of the ball.
     */
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.gameEnvironment = null;
    }

    /** @return the x coordinate of the ball's center. */
    public int getX() {
        return (int) this.center.getX();
    }

    /** @return the y coordinate of the ball's center. */
    public int getY() {
        return (int) this.center.getY();
    }

    /** @return the radius of the ball. */
    public int getSize() {
        return this.r;
    }

    /** @return the color of the ball. */
    public Color getColor() {
        return this.color;
    }

    /** @return the current velocity of the ball. */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball using dx and dy.
     *
     * @param dx the change in x per step.
     * @param dy the change in y per step.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Sets the game environment this ball uses for collision detection.
     *
     * @param environment the game environment.
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }

    /**
     * Draws this ball on the surface.
     * @param surface the surface to draw on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
    }

    /**
     * Notifies this ball that a unit of time has passed and moves it one
     * step according to its velocity and take collisions into account.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Moves the ball one step. First resolves any overlap with a
     * collidable (for instance, the paddle moved into the ball), then checks
     * for collisions and bounces accordingly.
     */
    public void moveOneStep() {
        if (this.gameEnvironment == null) {
            this.center = this.velocity.applyToPoint(this.center);
            return;
        }

        this.resolveOverlap();

        Point nextPoint = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(this.center, nextPoint);
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);

        if (info == null) {
            this.center = nextPoint;
            return;
        }

        Point collisionPoint = info.collisionPoint();
        this.center = this.almostHitPoint(collisionPoint);
        this.velocity = info.collisionObject().hit(this, collisionPoint, this.velocity);
    }

    /**
     * If the ball's center is inside any collidable rectangle, pushes
     * it above that rectangle and ensures it is moving upward.
     */
    private void resolveOverlap() {
        for (Collidable c : this.gameEnvironment.getCollidables()) {
            for (Rectangle rect : c.getCollisionRectangles()) {
                if (this.isInsideRect(rect)) {
                    double topY = rect.getUpperLeft().getY();
                    this.center = new Point(this.center.getX(), topY - this.r - 1);
                    if (this.velocity.getDy() > 0) {
                        this.velocity = new Velocity(
                                this.velocity.getDx(),
                                -Math.abs(this.velocity.getDy()));
                    }
                    return;
                }
            }
        }
    }

    /**
     * Returns true if the ball's center is inside the given
     * rectangle.
     *
     * @param rect the rectangle to test.
     * @return true if the center is inside rect.
     */
    private boolean isInsideRect(Rectangle rect) {
        double lx = rect.getUpperLeft().getX();
        double ty = rect.getUpperLeft().getY();
        double rx = lx + rect.getWidth();
        double by = ty + rect.getHeight();
        double cx = this.center.getX();
        double cy = this.center.getY();
        return cx > lx && cx < rx && cy > ty && cy < by;
    }

    /**
     * Returns a point slightly before the given collision point, moved
     * back along the ball's current velocity direction by one radius.
     * This method prevents the ball from ending up on or inside the collidable.
     *
     * @param collisionPoint the point of the collision.
     * @return a point just before the collision point.
     */
    private Point almostHitPoint(Point collisionPoint) {
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length < THRESHOLD) {
            return collisionPoint;
        }
        double normX = dx / length;
        double normY = dy / length;
        return new Point(
                collisionPoint.getX() - normX * this.r,
                collisionPoint.getY() - normY * this.r);
    }

    /**
     * Adds this ball to the given game as a sprite.
     *
     * @param g the game to add this ball to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
    /**
     * Sets the color of the ball.
     *
     * @param color the new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * Removes this ball from the given game.
     *
     * @param g the game to remove this ball from.
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}