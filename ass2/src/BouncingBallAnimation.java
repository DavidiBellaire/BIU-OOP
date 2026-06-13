import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * The BouncingBallAnimation class creates a simple animation of a single
 * bouncing ball. It takes the starting position and velocity from
 * command-line arguments.
 *
 */
public class BouncingBallAnimation {

    /**
     * Creates the GUI and runs the animation loop for a single ball.
     *
     * @param start the starting point of the ball's center.
     * @param dx    the velocity's change in the x-axis.
     * @param dy    the velocity's change in the y-axis.
     */
    private static void drawAnimation(Point start, double dx, double dy) {
        // GUI fixed at 800x600 
        GUI gui = new GUI("Bouncing Ball Animation", 800, 600);
        Sleeper sleeper = new Sleeper();

        // Creating a ball with radius 30 and black color
        Ball ball = new Ball(start, 30, Color.BLACK);
        ball.setVelocity(dx, dy);

        // Set boundaries for the ball to match the GUI size
        ball.setBoundaries(0, 0, 800, 600);

        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // Fill the background with white to prevent trailing
            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, 800, 600);

            // Update the ball's position
            ball.moveOneStep();

            // Draw the ball on the cleared surface
            ball.drawOn(d);

            // Show the updated frame
            gui.show(d);

            // Wait for 50 milliseconds to achieve ~20 FPS
            sleeper.sleepFor(50);
        }
    }

    /**
     * The main entry point for the animation.
     * Expects 4 command-line arguments: x, y, dx, dy.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        // Basic validation for command-line arguments
        if (args.length < 4) {
            System.out.println("Usage: java BouncingBallAnimation <x> <y> <dx> <dy>");
            return;
        }

        try {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double dx = Double.parseDouble(args[2]);
            double dy = Double.parseDouble(args[3]);

            drawAnimation(new Point(x, y), dx, dy);
        } catch (NumberFormatException e) {
            System.out.println("Error: Arguments must be valid numbers.");
        }
    }
}