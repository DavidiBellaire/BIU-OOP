import java.util.ArrayList;
import java.util.List;

/**
 * The Rectangle class represents an axis-aligned rectangle, defined by
 * its upper-left corner, width, and height.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructor that creates a new rectangle with the given location and size.
     *
     * @param upperLeft the upper-left corner of the rectangle.
     *
     * @param width the width of the rectangle.
     *
     * @param height the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns a (possibly empty) list of intersection points between the
     * given line and this rectangle's four sides.
     *
     * @param line the line to test.
     *
     * @return a list of intersection points with the rectangle's sides.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<>();

        // Compute the four corners from the upper-left, width, and height.
        Point upperRight = new Point(this.upperLeft.getX() + this.width,
                this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(),
                this.upperLeft.getY() + this.height);
        Point lowerRight = new Point(this.upperLeft.getX() + this.width,
                this.upperLeft.getY() + this.height);

        Line topSide = new Line(this.upperLeft, upperRight);
        Line bottomSide = new Line(lowerLeft, lowerRight);
        Line leftSide = new Line(this.upperLeft, lowerLeft);
        Line rightSide = new Line(upperRight, lowerRight);

        // Check each side for an intersection with the given line.
        Line[] sides = {topSide, bottomSide, leftSide, rightSide};
        for (Line side : sides) {
            Point intersection = line.intersectionWith(side);
            if (intersection != null) {
                points.add(intersection);
            }
        }

        return points;
    }

    /**
     * @return the width of this rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of this rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the upper-left point of this rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}