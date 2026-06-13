import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

/**
 * Animation of balls bouncing inside two distinct frames:
 * - The first half of the balls (by command-line order) bounce inside
 *   a gray rectangle (50,50)-(500,500).
 * - The second half bounce inside the 800x600 window but outside both
 *   the gray rectangle and a yellow rectangle (450,450)-(600,600).
 */
public class MultipleFramesBouncingBallsAnimation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final Frame GRAY = new Frame(50, 50, 500, 500);
    private static final Frame YELLOW = new Frame(450, 450, 600, 600);
    private static final Frame WINDOW = new Frame(0, 0, WIDTH, HEIGHT);

    /**
     * 
     *
     * @param args ball radii.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: MultipleFramesBouncingBallsAnimation <size1> <size2> ...");
            return;
        }

        GUI gui = new GUI("Multiple Frames Animation", WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();
        Ball[] balls = new Ball[args.length];

        // we are allowed to assume an even count but this also handles odd correctly.
        int mid = args.length / 2;

        for (int i = 0; i < args.length; i++) {
            int radius;
            try {
                radius = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                radius = Ball.DEFAULT_RADIUS;
            }
            if (radius < 1) {
                radius = 1;
            }

            Ball ball;
            if (i < mid) {
                ball = makeInsideGray(radius, rand);
            } else {
                ball = makeOutsideBoth(radius, rand);
            }

            double speed = (radius >= 50) ? 1 : (51 - radius);
            double angle = rand.nextInt(360);
            ball.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));
            balls[i] = ball;
        }

        while (true) {
            DrawSurface d = gui.getDrawSurface();

            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, WIDTH, HEIGHT);

            d.setColor(Color.GRAY);
            d.fillRectangle(GRAY.getX1(), GRAY.getY1(),
                    GRAY.getWidth(), GRAY.getHeight());

            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }

            // Yellow on top: gray-frame balls passing under it are hidden,
            d.setColor(Color.YELLOW);
            d.fillRectangle(YELLOW.getX1(), YELLOW.getY1(),
                    YELLOW.getWidth(), YELLOW.getHeight());

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }

    /**
     * Creates a ball positioned randomly inside the gray frame, constrained
     * to stay inside it. 
     *
     * @param radius the ball's radius.
     * @param rand   a random number generator.
     * @return the created ball.
     */
    private static Ball makeInsideGray(int radius, Random rand) {
        int rangeX = GRAY.getWidth();
        int rangeY = GRAY.getHeight();
        double x;
        double y;
        if (rangeX > 2 * radius && rangeY > 2 * radius) {
            x = rand.nextInt(rangeX - 2 * radius) + GRAY.getX1() + radius;
            y = rand.nextInt(rangeY - 2 * radius) + GRAY.getY1() + radius;
        } else {
            // Radius bigger than the frame — center it.
            x = GRAY.getX1() + rangeX / 2.0;
            y = GRAY.getY1() + rangeY / 2.0;
        }
        Ball ball = new Ball(new Point(x, y), radius, Color.RED);
        ball.setAllowedFrame(GRAY);
        return ball;
    }

    /**
     * Creates a ball positioned randomly inside the window but outside both
     * obstacle rectangles. Both rectangles are registered as obstacles.
     *
     * @param radius the ball's radius.
     * @param rand   a random number generator.
     * @return the created ball.
     */
    private static Ball makeOutsideBoth(int radius, Random rand) {
        double x;
        double y;
        int attempts = 0;
        do {
            int rx = Math.max(1, WIDTH - 2 * radius);
            int ry = Math.max(1, HEIGHT - 2 * radius);
            x = rand.nextInt(rx) + radius;
            y = rand.nextInt(ry) + radius;
            attempts++;
            if (attempts > 1000) {
            
                x = WIDTH - radius - 1;
                y = radius + 1;
                break;
            }
        } while (GRAY.overlapsCircle(x, y, radius)
                || YELLOW.overlapsCircle(x, y, radius));

        Ball ball = new Ball(new Point(x, y), radius, Color.BLUE);
        ball.setAllowedFrame(WINDOW);
        ball.setObstacles(new Frame[]{GRAY, YELLOW});
        return ball;
    }
}