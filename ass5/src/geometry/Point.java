package geometry;
/**
 * Represents a point in 2D space with x and y coordinates.
 */
public class Point {
    private static final double THRESHOLD = 0.00001;
    private double x;
    private double y;

    /**
     * Constructs a new point with the given x and y values.
     *
     * @param x the x coordinate
     *
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the distance between this point and another point.
     *
     * @param other the other point
     *
     * @return the distance between the two points
     */
    public double distance(Point other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns true if this point is equal to another point, false otherwise.
     *
     * @param other the other point
     *
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (Math.abs(this.x - other.getX()) < THRESHOLD
                && Math.abs(this.y - other.getY()) < THRESHOLD) {
            return true;
        }
        return false;
    }

    /**
     * Returns the x coordinate of this point.
     *
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate of this point.
     *
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }
}