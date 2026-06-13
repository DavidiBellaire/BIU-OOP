import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player-controlled paddle in the game.
 * Implements both Sprite and Collidable.
 */
public class Paddle implements Sprite, Collidable {
    private static final int NUM_REGIONS = 5;
    private static final int MOVE_STEP = 5;
    private static final double[] REGION_ANGLES = {300, 330, 0, 30, 60};

    private KeyboardSensor keyboard;
    private double paddleX;
    private double paddleY;
    private double paddleWidth;
    private double paddleHeight;
    private Color color;
    private int leftBound;
    private int rightBound;

    /**
     * Constructs a paddle with the given rectangle, color, keyboard sensor, and bounds.
     *
     * @param rect       the initial position and size of the paddle
     * @param color      the color of the paddle
     * @param keyboard   the keyboard sensor for detecting key presses
     * @param leftBound  the left boundary of the play area
     * @param rightBound the right boundary of the play area
     */
    public Paddle(Rectangle rect, Color color, KeyboardSensor keyboard,
                  int leftBound, int rightBound) {
        this.paddleX = rect.getUpperLeft().getX();
        this.paddleY = rect.getUpperLeft().getY();
        this.paddleWidth = rect.getWidth();
        this.paddleHeight = rect.getHeight();
        this.color = color;
        this.keyboard = keyboard;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    /**
     * Returns the width of the play area.
     *
     * @return play area width
     */
    private double playWidth() {
        return this.rightBound - this.leftBound;
    }

    /**
     * Moves the paddle left by MOVE_STEP.
     */
    public void moveLeft() {
        this.paddleX -= MOVE_STEP;
        if (this.paddleX < this.leftBound) {
            this.paddleX += playWidth();
        }
    }

    /**
     * Moves the paddle right by MOVE_STEP.
     */
    public void moveRight() {
        this.paddleX += MOVE_STEP;
        if (this.paddleX >= this.leftBound + playWidth()) {
            this.paddleX -= playWidth();
        }
    }

    /**
     * Moves the paddle according to the currently pressed arrow keys.
     */
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * Returns the physical rectangles the paddle currently occupies.
     * Returns two rectangles when the paddle straddles a boundary, otherwise one.
     *
     * @return list of physical rectangles
     */
    public List<Rectangle> physicalRects() {
        List<Rectangle> rects = new ArrayList<>();
        double rightEdge = this.paddleX + this.paddleWidth;

        if (rightEdge > this.rightBound) {
            double leftPieceWidth = this.rightBound - this.paddleX;
            double rightPieceWidth = this.paddleWidth - leftPieceWidth;
            rects.add(new Rectangle(
                new Point(this.paddleX, this.paddleY),
                leftPieceWidth, this.paddleHeight));
            rects.add(new Rectangle(
                new Point(this.leftBound, this.paddleY),
                rightPieceWidth, this.paddleHeight));
        } else {
            rects.add(new Rectangle(
                new Point(this.paddleX, this.paddleY),
                this.paddleWidth, this.paddleHeight));
        }
        return rects;
    }

    /**
     * Returns the primary collision rectangle of the paddle.
     *
     * @return the primary collision Rectangle
     */
    public Rectangle getCollisionRectangle() {
        return physicalRects().get(0);
    }

    /**
     * Returns all physical rectangles of the paddle for collision detection.
     *
     * @return list of collision rectangles
     */
    public List<Rectangle> getCollisionRectangles() {
        return physicalRects();
    }

    /**
     * Returns the new velocity after a collision with the paddle.
     * The result depends on which of the 5 regions of the paddle was hit.
     *
     * @param collisionPoint  the point at which the collision occurred
     * @param currentVelocity the velocity before the collision
     * @return the new velocity after the collision
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double speed = Math.sqrt(
            currentVelocity.getDx() * currentVelocity.getDx()
            + currentVelocity.getDy() * currentVelocity.getDy());
        if (speed == 0) {
            return currentVelocity;
        }

        Rectangle hitRect = null;
        for (Rectangle r : physicalRects()) {
            double lx = r.getUpperLeft().getX();
            double rx = lx + r.getWidth();
            double ty = r.getUpperLeft().getY();
            double by = ty + r.getHeight();
            double hx = collisionPoint.getX();
            double hy = collisionPoint.getY();
            if (hx >= lx - 1 && hx <= rx + 1
                    && hy >= ty - 1 && hy <= by + 1) {
                hitRect = r;
                break;
            }
        }
        if (hitRect == null) {
            hitRect = physicalRects().get(0);
        }

        double pieceLeft = hitRect.getUpperLeft().getX();
        double logicalOffset;
        if (Math.abs(pieceLeft - this.paddleX) < 1) {
            logicalOffset = collisionPoint.getX() - pieceLeft;
        } else {
            double leftPieceWidth = this.rightBound - this.paddleX;
            logicalOffset = leftPieceWidth
                + (collisionPoint.getX() - pieceLeft);
        }

        double regionWidth = this.paddleWidth / NUM_REGIONS;
        int region = (int) (logicalOffset / regionWidth);
        if (region < 0) {
            region = 0;
        }
        if (region >= NUM_REGIONS) {
            region = NUM_REGIONS - 1;
        }

        if (region == 2) {
            return new Velocity(
                currentVelocity.getDx(),
                -Math.abs(currentVelocity.getDy()));
        }
        return Velocity.fromAngleAndSpeed(REGION_ANGLES[region], speed);
    }

    /**
     * Draws the paddle on the surface.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        for (Rectangle r : physicalRects()) {
            d.setColor(this.color);
            d.fillRectangle(
                (int) r.getUpperLeft().getX(),
                (int) r.getUpperLeft().getY(),
                (int) r.getWidth(),
                (int) r.getHeight());
            d.setColor(Color.BLACK);
            d.drawRectangle(
                (int) r.getUpperLeft().getX(),
                (int) r.getUpperLeft().getY(),
                (int) r.getWidth(),
                (int) r.getHeight());
        }
    }

    /**
     * Adds this paddle to the game as both a sprite and a collidable.
     *
     * @param g the game to add to
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}