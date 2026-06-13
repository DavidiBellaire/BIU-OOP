import java.util.List;

/**
 * Represents a line segment in 2D space defined by two points.
 */
public class Line {
    private static final double THRESHOLD = 0.00001;
    private Point start;
    private Point end;

    /**
     * Constructs a new line from two points.
     *
     * @param start the start point
     *
     * @param end the end point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a new line from four coordinates.
     *
     * @param x1 the x coordinate of the start point
     *
     * @param y1 the y coordinate of the start point
     *
     * @param x2 the x coordinate of the end point
     *
     * @param y2 the y coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of this line segment.
     *
     * @return the length of the line
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of this line segment.
     *
     * @return the middle point
     */
    public Point middle() {
        double midX = (start.getX() + end.getX()) / 2;
        double midY = (start.getY() + end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * Returns the start point of this line segment.
     *
     * @return the start point
     */
    public Point start() {
        return start;
    }

    /**
     * Returns the end point of this line segment.
     *
     * @return the end point
     */
    public Point end() {
        return end;
    }

    /**
     * Returns the intersection point of this line and another line.
     * Returns null if the lines do not intersect or have infinite intersections.
     *
     * @param other the other line
     *
     * @return the intersection point, or null
     */
    public Point intersectionWith(Line other) {
        double x1 = start.getX(), y1 = start.getY();
        double x2 = end.getX(), y2 = end.getY();
        double x3 = other.start().getX(), y3 = other.start().getY();
        double x4 = other.end().getX(), y4 = other.end().getY();

        boolean line1Vertical = Math.abs(x2 - x1) < THRESHOLD;
        boolean line2Vertical = Math.abs(x4 - x3) < THRESHOLD;

        if (line1Vertical && line2Vertical) {
            return null;
        }

        if (line1Vertical) {
            double x = x1;
            double m2 = (y4 - y3) / (x4 - x3);
            double b2 = y3 - m2 * x3;
            double y = m2 * x + b2;
            boolean onSegment1 = y >= Math.min(y1, y2) - THRESHOLD && y <= Math.max(y1, y2) + THRESHOLD;
            boolean onSegment2 = x >= Math.min(x3, x4) - THRESHOLD && x <= Math.max(x3, x4) + THRESHOLD;
            if (!(onSegment1 && onSegment2)) {
                return null;
            }
            return new Point(x, y);
        }

        if (line2Vertical) {
            double x = x3;
            double m1 = (y2 - y1) / (x2 - x1);
            double b1 = y1 - m1 * x1;
            double y = m1 * x + b1;
            boolean onSegment1 = x >= Math.min(x1, x2) - THRESHOLD && x <= Math.max(x1, x2) + THRESHOLD;
            boolean onSegment2 = y >= Math.min(y3, y4) - THRESHOLD && y <= Math.max(y3, y4) + THRESHOLD;
            if (!(onSegment1 && onSegment2)) {
                return null;
            }
            return new Point(x, y);
        }

        double m1 = (y2 - y1) / (x2 - x1);
        double m2 = (y4 - y3) / (x4 - x3);

        if (Math.abs(m1 - m2) < THRESHOLD) {
            return null;
        }

        double b1 = y1 - m1 * x1;
        double b2 = y3 - m2 * x3;

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;

        boolean onSegment1 = x >= Math.min(x1, x2) - THRESHOLD && x <= Math.max(x1, x2) + THRESHOLD;
        boolean onSegment2 = x >= Math.min(x3, x4) - THRESHOLD && x <= Math.max(x3, x4) + THRESHOLD;

        if (!(onSegment1 && onSegment2)) {
            return null;
        }
        return new Point(x, y);
    }

    /**
     * Returns true if this line intersects with another line, false otherwise.
     *
     * @param other the other line
     *
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (intersectionWith(other) != null) {
            return true;
        }
        if (isOverlapping(other)) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if this line intersects with both other lines, false otherwise.
     *
     * @param other1 the first other line
     * @param other2 the second other line
     * @return true if this line intersects with both lines, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /**
     * Returns true if this line is equal to another line, false otherwise.
     * Two lines are equal if they have the same start and end points,
     * regardless of direction.
     *
     * @param other the other line
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if ((this.start.equals(other.start()) && this.end.equals(other.end()))
                || (this.start.equals(other.end()) && this.end.equals(other.start()))) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if this line overlaps with another line, false otherwise.
     *
     * @param other the other line
     *
     * @return true if the lines overlap, false otherwise
     */
    private boolean isOverlapping(Line other) {
        double x1 = start.getX(), y1 = start.getY();
        double x2 = end.getX(), y2 = end.getY();
        double x3 = other.start().getX(), y3 = other.start().getY();
        double x4 = other.end().getX(), y4 = other.end().getY();

        boolean line1Vertical = Math.abs(x2 - x1) < THRESHOLD;
        boolean line2Vertical = Math.abs(x4 - x3) < THRESHOLD;

        if (line1Vertical && line2Vertical) {
            if (Math.abs(x1 - x3) > THRESHOLD) {
                return false;
            }
            return Math.max(y1, y2) >= Math.min(y3, y4) - THRESHOLD
                    && Math.max(y3, y4) >= Math.min(y1, y2) - THRESHOLD;
        }

        if (line1Vertical || line2Vertical) {
            return false;
        }

        double m1 = (y2 - y1) / (x2 - x1);
        double m2 = (y4 - y3) / (x4 - x3);
        if (Math.abs(m1 - m2) > THRESHOLD) {
            return false;
        }

        double b1 = y1 - m1 * x1;
        double b2 = y3 - m2 * x3;
        if (Math.abs(b1 - b2) > THRESHOLD) {
            return false;
        }

        return Math.max(x1, x2) >= Math.min(x3, x4) - THRESHOLD
                && Math.max(x3, x4) >= Math.min(x1, x2) - THRESHOLD;
    }

    /**
     * Returns the closest intersection point between this line and the
     * given rectangle, measured from the start of this line. Returns null
     * if there is no intersection.
     *
     * @param rect the rectangle to test against.
     *
     * @return the closest intersection point to start, or null if none.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }

        Point closest = intersections.get(0);
        double minDistance = this.start.distance(closest);

        for (int i = 1; i < intersections.size(); i++) {
            Point candidate = intersections.get(i);
            double distance = this.start.distance(candidate);
            if (distance < minDistance) {
                minDistance = distance;
                closest = candidate;
            }
        }

        return closest;
    }
}