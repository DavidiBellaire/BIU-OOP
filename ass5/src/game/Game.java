package game;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;
import java.awt.Color;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import collision.Collidable;
import collision.GameEnvironment;
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import listeners.BlockRemover;
import listeners.BallRemover;
import listeners.ScoreTrackingListener;
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

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private KeyboardSensor keyboard;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;

    /**
     * creates a new game with empty sprite collection
     * and game environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = new Counter();
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
        this.createDeathRegion();
        this.createBlocks();
        this.createPaddle();
        this.createBalls();
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);
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
            if (this.remainingBlocks.getValue() == 0) {
                this.score.increase(100);
                System.out.println("You Win!");
                System.out.println("Your score is: " + this.score.getValue());
                this.gui.close();
                return;
            }
            if (this.remainingBalls.getValue() == 0) {
                System.out.println("Game Over.");
                System.out.println("Your score is: " + this.score.getValue());
                this.gui.close();
                return;
            }
        }

    }

    /**
     * Creates the four boundary blocks that frame the play area.
     */
    private void createBoundaries() {
        Color borderColor = Color.GRAY;

        // Top boundary
        new Block(new Rectangle(new Point(0, 0), WIDTH, BORDER_THICKNESS),
                borderColor).addToGame(this);

        // Left boundary
        new Block(new Rectangle(new Point(0, 0), BORDER_THICKNESS, HEIGHT),
                borderColor).addToGame(this);

        // Right boundary
        new Block(new Rectangle(new Point(WIDTH - BORDER_THICKNESS, 0),
                BORDER_THICKNESS, HEIGHT), borderColor).addToGame(this);
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
        int ballY = HEIGHT - BORDER_THICKNESS - PADDLE_HEIGHT
                - BALL_OFFSET_FROM_PADDLE;
        double[] angles = {310, 0, 50};

        for (int i = 0; i < 3; i++) {
            int ballX = WIDTH / 2 + (i - 1) * BALL_HORIZONTAL_OFFSET;
            Ball ball = new Ball(new Point(ballX, ballY),
                    BALL_RADIUS, Color.WHITE);
            ball.setGameEnvironment(this.environment);
            ball.setVelocity(Velocity.fromAngleAndSpeed(angles[i], BALL_SPEED));
            ball.addToGame(this);
            this.remainingBalls.increase(1);
        }
    }
    /**
     * Removes the given collidable from the game environment.
     *
     * @param c the collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes the given sprite from the sprite collection.
     *
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }


    private void createBlocks() {
        Color[] rowColors = {Color.GRAY, Color.RED, Color.YELLOW,
                Color.BLUE, Color.PINK, Color.GREEN};

        BlockRemover blockRemover =
                new BlockRemover(this, this.remainingBlocks);
        ScoreTrackingListener scoreListener =
                new ScoreTrackingListener(this.score);

        for (int row = 0; row < BLOCK_ROWS; row++) {
            int blocksInRow = LONGEST_ROW - row;
            int y = BLOCKS_TOP_Y + row * BLOCK_HEIGHT;
            int rowRightEdge = WIDTH - BORDER_THICKNESS;
            int startX = rowRightEdge - blocksInRow * BLOCK_WIDTH;

            for (int col = 0; col < blocksInRow; col++) {
                int x = startX + col * BLOCK_WIDTH;
                Rectangle rect = new Rectangle(new Point(x, y),
                        BLOCK_WIDTH, BLOCK_HEIGHT);
                Block block = new Block(rect, rowColors[row]);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreListener);
                block.addToGame(this);
                this.remainingBlocks.increase(1);
            }
        }
    }
    /**
     * Creates the death-region block at the bottom of the screen, and
     * registers a BallRemover as its listener so that balls hitting it
     * are removed from the game.
     */
    private void createDeathRegion() {
        BallRemover ballRemover =
                new BallRemover(this, this.remainingBalls);
        Rectangle rect = new Rectangle(
                new Point(0, HEIGHT),
                WIDTH, BORDER_THICKNESS);
        Block deathRegion = new Block(rect, Color.BLACK);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
    }
}