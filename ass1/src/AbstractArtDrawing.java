// ID: 214165417
import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;

/**
 * Draws an abstract art image with random lines, their middle points,
 * intersection points, and triangle parts.
 */
public class AbstractArtDrawing {

    /**
     * Generates a random line within an 800x600 screen.
     * @return a random line
     */
    private Line generateRandomLine() {
        Random rand = new Random();
        int x1 = rand.nextInt(800) + 1;
        int y1 = rand.nextInt(600) + 1;
        int x2 = rand.nextInt(800) + 1;
        int y2 = rand.nextInt(600) + 1;
        return new Line(x1, y1, x2, y2);
    }

    /**
     * Draws a line in black on the given draw surface.
     * @param l the line to draw
     * @param d the draw surface
     */
    private void drawLine(Line l, DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawLine((int) l.start().getX(), (int) l.start().getY(),
                   (int) l.end().getX(), (int) l.end().getY());
    }

    /**
     * Draws the middle point of a line in blue on the given draw surface.
     * @param l the line
     * @param d the draw surface
     */
    private void drawMiddlePoint(Line l, DrawSurface d) {
        d.setColor(Color.BLUE);
        d.fillCircle((int) l.middle().getX(), (int) l.middle().getY(), 3);
    }

    /**
     * Draws the intersection point of two lines in red on the given draw surface.
     * @param l1 the first line
     * @param l2 the second line
     * @param d the draw surface
     */
    private void drawIntersection(Line l1, Line l2, DrawSurface d) {
        Point p = l1.intersectionWith(l2);
        if (p != null) {
            d.setColor(Color.RED);
            d.fillCircle((int) p.getX(), (int) p.getY(), 3);
        }
    }

    /**
     * Draws the parts of lines between intersection points in green.
     * @param lines the array of lines
     * @param d the draw surface
     */
    private void drawTriangleParts(Line[] lines, DrawSurface d) {
        for (int i = 0; i < lines.length; i++) {
            Point[] points = new Point[lines.length];
            int count = 0;
            for (int j = 0; j < lines.length; j++) {
                if (i == j) {
    continue;
}
                Point p = lines[i].intersectionWith(lines[j]);
                if (p != null) {
                    points[count] = p;
                    count++;
                }
            }
            if (count >= 2) {
                d.setColor(Color.GREEN);
                d.drawLine((int) points[0].getX(), (int) points[0].getY(),
                           (int) points[1].getX(), (int) points[1].getY());
            }
        }
    }

    /**
     * Runs the abstract art drawing.
     */
    public void run() {
        GUI gui = new GUI("Abstract Art", 800, 600);
        DrawSurface d = gui.getDrawSurface();

        Line[] lines = new Line[10];
        for (int i = 0; i < 10; i++) {
            lines[i] = generateRandomLine();
        }

        for (int i = 0; i < 10; i++) {
            drawLine(lines[i], d);
        }

        for (int i = 0; i < 10; i++) {
            drawMiddlePoint(lines[i], d);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                drawIntersection(lines[i], lines[j], d);
            }
        }

        drawTriangleParts(lines, d);

        gui.show(d);
    }

    /**
     * Main method.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        AbstractArtDrawing drawing = new AbstractArtDrawing();
        drawing.run();
    }
}