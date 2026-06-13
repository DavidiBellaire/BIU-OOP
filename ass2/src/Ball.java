/*id: 214165417 */
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The Ball class represents a circle with a center point, radius, and color.
 * It supports an outer allowed Frame the ball must stay inside of, and a
 * set of obstacle Frames the ball must remain outside of.
 */
public class Ball {
    private static final double EPS = 1e-9;
    private Point center;
    private int r;
    private Color color;
    private Velocity velocity;
    private Frame allowedFrame;
    private Frame[] obstacles;

    /**
     * Default radius used when input is invalid.
     */
    public static final int DEFAULT_RADIUS = 5;

    /**
     * 
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
        this.allowedFrame = null;
        this.obstacles = new Frame[0];
    }

    /**
     * Sets the outer allowed bounding box. 
     *
     * @param x1 the minimum x coordinate.
     * @param y1 the minimum y coordinate.
     * @param x2 the maximum x coordinate.
     * @param y2 the maximum y coordinate.
     */
    public void setBoundaries(int x1, int y1, int x2, int y2) {
        this.allowedFrame = new Frame(x1, y1, x2, y2);
    }

    /**
     * Sets the outer Frame the ball must stay inside of.
     *
     * @param f the allowed frame.
     */
    public void setAllowedFrame(Frame f) {
        this.allowedFrame = f;
    }

    /**
     * Sets the obstacle frames the ball must remain outside of.
     *
     * @param obs an array of obstacle frames.
     */
    public void setObstacles(Frame[] obs) {
        if (obs == null) {
            this.obstacles = new Frame[0];
        } else {
            this.obstacles = obs;
        }
    }

    /**
     * @return the x coordinate of the center.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return the y coordinate of the center.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the radius of the ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * @return the color of the ball.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return the velocity of the ball.
     */
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
     * @param dx the change in x.
     * @param dy the change in y.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface the surface to draw on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
    }

    /**
     * Moves the ball one step, bouncing off the outer allowed frame and
     * off any obstacle frames it is currently outside of.
     */
    public void moveOneStep() {
        double curX = this.center.getX();
        double curY = this.center.getY();
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();
        double nextX = curX + dx;
        double nextY = curY + dy;

        // Outer allowed frame — bounce inward.
        if (this.allowedFrame != null) {
            if (nextX + this.r >= this.allowedFrame.getX2() - EPS
                    || nextX - this.r <= this.allowedFrame.getX1() + EPS) {
                dx = -dx;
            }
            if (nextY + this.r >= this.allowedFrame.getY2() - EPS
                    || nextY - this.r <= this.allowedFrame.getY1() + EPS) {
                dy = -dy;
            }
            nextX = curX + dx;
            nextY = curY + dy;
        }

        // Obstacles — bounce off any obstacle the ball is currently
        //    OUTSIDE of (skip ones it spawned inside, so it can leave).
        for (int i = 0; i < this.obstacles.length; i++) {
            Frame ob = this.obstacles[i];
            if (ob.overlapsCircle(curX, curY, this.r)) {
                continue;
            }
            if (ob.overlapsCircle(nextX, nextY, this.r)) {
                boolean enterX = ob.overlapsCircle(nextX, curY, this.r);
                boolean enterY = ob.overlapsCircle(curX, nextY, this.r);
                if (enterX) {
                    dx = -dx;
                }
                if (enterY) {
                    dy = -dy;
                }
                // Pure corner hit 
                if (!enterX && !enterY) {
                    dx = -dx;
                    dy = -dy;
                }
                nextX = curX + dx;
                nextY = curY + dy;
            }
        }

        this.velocity = new Velocity(dx, dy);
        this.center = this.velocity.applyToPoint(this.center);
    }
}