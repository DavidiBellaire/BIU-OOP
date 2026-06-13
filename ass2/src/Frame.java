
/**
 * The Frame class represents a rectangular area, defined
 * by its top-left corner and bottom-right corner.
 * Used both as an allowed area a ball must stay inside of, and as an
 * obstacle a ball must remain outside of.
 */
public class Frame {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    /**
     *
     *
     * @param x1 top-left x.
     * @param y1 top-left y.
     * @param x2 bottom-right x.
     * @param y2 bottom-right y.
     */
    public Frame(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * @return the top-left x coordinate.
     */
    public int getX1() {
        return this.x1;
    }

    /**
     * @return the top-left y coordinate.
     */
    public int getY1() {
        return this.y1;
    }

    /**
     * @return the bottom-right x coordinate.
     */
    public int getX2() {
        return this.x2;
    }

    /**
     * @return the bottom-right y coordinate.
     */
    public int getY2() {
        return this.y2;
    }

    /**
     * @return the width of the frame.
     */
    public int getWidth() {
        return this.x2 - this.x1;
    }

    /**
     * @return the height of the frame.
     */
    public int getHeight() {
        return this.y2 - this.y1;
    }

    /**
     * Checks whether a circle with center (cx, cy) and radius r overlaps
     * the interior of this frame.
     *
     * @param cx the x coordinate of the circle's center.
     * @param cy the y coordinate of the circle's center.
     * @param r  the radius of the circle.
     * @return true iff the circle overlaps this frame.
     */
    public boolean overlapsCircle(double cx, double cy, int r) {
        return cx + r > this.x1 && cx - r < this.x2
                && cy + r > this.y1 && cy - r < this.y2;
    }
}