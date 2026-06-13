/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 *
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor for Velocity.
     *
     * @param dx the change in x-axis.
     * @param dy the change in y-axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the change in x-axis.
     *
     * @return the velocity in x-axis.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Returns the change in y-axis.
     *
     * @return the velocity in y-axis.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Takes a point with position (x,y) and returns a new point
     * with position (x+dx, y+dy).
     *
     * @param p the point to apply the velocity to.
     * @return a new point with updated coordinates.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Creates a new Velocity object from angle and speed.
     *
     * @param angle the angle of movement in degrees.
     * @param speed the speed of movement.
     * @return a new Velocity instance.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = speed * Math.sin(radians);
        double dy = -speed * Math.cos(radians);
        return new Velocity(dx, dy);
    }
}