import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.Random;

/**
 * The Game class holds the sprite collection and the game environment,
 * initializes all game
 * objects and runs the animation loop at 60 FPS.
 */
public class Game {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BORDER_THICKNESS = 25;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLIS_PER_FRAME = 1000 / FRAMES_PER_SECOND;

    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 15;

    private static final int BLOCK_WIDTH = 50;
    private static final int BLOCK_HEIGHT = 25;
    private static final int BLOCKS_TOP_Y = 100;
    private static final int BLOCK_ROWS = 6;
    private static final int LONGEST_ROW = 12;

    private static final int BALL_RADIUS = 6;
    private static final int BALL_SPEED = 5;

    private static final int BALL_OFFSET_FROM_PADDLE = 40;
    private static final int BALL_HORIZONTAL_OFFSET = 30;
    private static final double BALL_BASE_ANGLE = 30;
    private static final double BALL_ANGLE_RANDOM_RANGE = 40;
    private static final double BALL_LEFT_ANGLE_SHIFT = 280;

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private KeyboardSensor keyboard;

    /**
     * creates a new game with empty sprite collection
     * and game environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }

    /**
     * Adds the given collidable to the game environment.
     *
     * @param c the collidable to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds the given sprite to the sprite collection.
     *
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes the game which means creates the GUI, the boundary blocks, the
     * brick pyramid, the paddle, and the two balls.
     */
    public void initialize() {
        this.gui = new GUI("Arkanoid", WIDTH, HEIGHT);
        this.keyboard = this.gui.getKeyboardSensor();

        this.createBoundaries();
        this.createBlocks();
        this.createPaddle();
        this.createBalls();
    }

    /**
     * Runs the animation loop until the user closes the window.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();

        while (true) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = this.gui.getDrawSurface();
            d.setColor(new Color(0, 80, 160));
            d.fillRectangle(0, 0, WIDTH, HEIGHT);
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = MILLIS_PER_FRAME - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Creates the four boundary blocks that frame the play area.
     */
    private void createBoundaries() {
        Color borderColor = Color.GRAY;

        new Block(new Rectangle(new Point(0, 0), WIDTH, BORDER_THICKNESS),
                borderColor).addToGame(this);

        new Block(new Rectangle(new Point(0, HEIGHT - BORDER_THICKNESS),
                WIDTH, BORDER_THICKNESS), borderColor).addToGame(this);

        new Block(new Rectangle(new Point(0, 0), BORDER_THICKNESS, HEIGHT),
                borderColor).addToGame(this);

        new Block(new Rectangle(new Point(WIDTH - BORDER_THICKNESS, 0),
                BORDER_THICKNESS, HEIGHT), borderColor).addToGame(this);
    }

    /**
     * Creates the brick pyramid — rows of blocks decreasing in width,
     * each row a different color.
     */
    private void createBlocks() {
        Color[] rowColors = {Color.GRAY, Color.RED, Color.YELLOW,
                Color.BLUE, Color.PINK, Color.GREEN};

        for (int row = 0; row < BLOCK_ROWS; row++) {
            int blocksInRow = LONGEST_ROW - row;
            int y = BLOCKS_TOP_Y + row * BLOCK_HEIGHT;
            int rowRightEdge = WIDTH - BORDER_THICKNESS;
            int startX = rowRightEdge - blocksInRow * BLOCK_WIDTH;

            for (int col = 0; col < blocksInRow; col++) {
                int x = startX + col * BLOCK_WIDTH;
                Rectangle rect = new Rectangle(new Point(x, y),
                        BLOCK_WIDTH, BLOCK_HEIGHT);
                new Block(rect, rowColors[row]).addToGame(this);
            }
        }
    }

    /**
     * Creates the paddle, centered at the bottom of
     * the play area.
     */
    private void createPaddle() {
        int paddleX = (WIDTH - PADDLE_WIDTH) / 2;
        int paddleY = HEIGHT - BORDER_THICKNESS - PADDLE_HEIGHT;
        Rectangle rect = new Rectangle(new Point(paddleX, paddleY),
                PADDLE_WIDTH, PADDLE_HEIGHT);
        Paddle paddle = new Paddle(rect, Color.ORANGE, this.keyboard,
                BORDER_THICKNESS, WIDTH - BORDER_THICKNESS);
        paddle.addToGame(this);
    }

    /**
     * Creates two balls with initial velocities, positioned above the
     * paddle.
     */
    private void createBalls() {
        Random rand = new Random();
        int ballY = HEIGHT - BORDER_THICKNESS - PADDLE_HEIGHT
                - BALL_OFFSET_FROM_PADDLE;

        for (int i = 0; i < 2; i++) {
            int ballX = WIDTH / 2
                    + (i == 0 ? -BALL_HORIZONTAL_OFFSET : BALL_HORIZONTAL_OFFSET);
            Ball ball = new Ball(new Point(ballX, ballY),
                    BALL_RADIUS, Color.WHITE);
            ball.setGameEnvironment(this.environment);
            double angle = BALL_BASE_ANGLE
                    + rand.nextInt((int) BALL_ANGLE_RANDOM_RANGE)
                    + (i == 1 ? BALL_LEFT_ANGLE_SHIFT : 0);
            ball.setVelocity(Velocity.fromAngleAndSpeed(angle, BALL_SPEED));
            ball.addToGame(this);
        }
    }
}