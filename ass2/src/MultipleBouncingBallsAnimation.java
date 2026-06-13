/*id: 214165417 */
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

/**
 * The MultipleBouncingBallsAnimation class creates an animation of
 * several balls bouncing within an 800x600 window. The sizes are taken
 * from command-line arguments — one argument per ball.
 */
public class MultipleBouncingBallsAnimation {

    /**
     * The main method that starts the animation.
     * Expects radii of balls as command-line arguments.
     *
     * @param args radii of the balls.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide radii as arguments.");
            return;
        }

        GUI gui = new GUI("Multiple Bouncing Balls", 800, 600);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();
        Ball[] balls = new Ball[args.length];

        for (int i = 0; i < args.length; i++) {
            int radius;
            try {
                radius = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                radius = Ball.DEFAULT_RADIUS;
            }
            // Clamp the radius so the ball can fit in the window.
            if (radius < 1) {
                radius = 1;
            }
            if (radius > 300) {
                radius = 300;
            }

            // Random starting position. Math.max guards against nextInt(0)
            // or nextInt(negative) when the radius is large.
            int rangeX = Math.max(1, 800 - 2 * radius);
            int rangeY = Math.max(1, 600 - 2 * radius);
            double x = rand.nextInt(rangeX) + radius;
            double y = rand.nextInt(rangeY) + radius;

            balls[i] = new Ball(new Point(x, y), radius, Color.BLACK);
            balls[i].setBoundaries(0, 0, 800, 600);

            // Speed: larger balls move slower. Balls of size >= 50 share
            // the same slow speed.
            double speed;
            if (radius >= 50) {
                speed = 1;
            } else {
                speed = 51 - radius;
            }

            double angle = rand.nextInt(360);
            balls[i].setVelocity(Velocity.fromAngleAndSpeed(angle, speed));
        }

        while (true) {
            DrawSurface d = gui.getDrawSurface();

            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, 800, 600);

            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}