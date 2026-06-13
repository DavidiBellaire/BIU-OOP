import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The Block class represents a colored, axis-aligned rectangle that the
 * ball can collide with. A Block implements both Sprite (it can draw
 * itself on the screen) and Collidable (it reacts to being hit by a
 * ball, returning the ball's new velocity after the bounce).
 */
public class Block implements Collidable, Sprite {
    private static final double THRESHOLD = 0.00001;

    private Rectangle rect;
    private Color color;

    /**
     * Constructor that creates a block with the given rectangle and color.
     *
     * @param rect  the rectangle defining the block's shape and position.
     * @param color the color used to draw the block.
     */
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
    }

    /**
     * @return the rectangle defining this block's collision shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Notifies this block that a ball has hit it at the given collision
     * point with the given velocity. Returns the new velocity for the
     * ball, with dx and/or dy flipped depending on which side of the
     * block was hit.
     *
     * @param collisionPoint  the point at which the collision occurred.
     * @param currentVelocity the velocity of the ball before the hit.
     * @return the new velocity for the ball after the bounce.
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        double leftX = this.rect.getUpperLeft().getX();
        double rightX = leftX + this.rect.getWidth();
        double topY = this.rect.getUpperLeft().getY();
        double bottomY = topY + this.rect.getHeight();

        double hitX = collisionPoint.getX();
        double hitY = collisionPoint.getY();

        // Hit on left or right side so flip dx.
        if (Math.abs(hitX - leftX) < THRESHOLD
                || Math.abs(hitX - rightX) < THRESHOLD) {
            dx = -dx;
        }
        // Hit on top or bottom side so flip dy.
        if (Math.abs(hitY - topY) < THRESHOLD
                || Math.abs(hitY - bottomY) < THRESHOLD) {
            dy = -dy;
        }

        return new Velocity(dx, dy);
    }

    /**
     * Draws this block on the surface.
     *
     * @param d the surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int w = (int) this.rect.getWidth();
        int h = (int) this.rect.getHeight();

        d.setColor(this.color);
        d.fillRectangle(x, y, w, h);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }

    /**
     * Notifies this block that a unit of time has passed. Blocks are
     * static, so this does nothing.
     */
    public void timePassed() {
        // Static blocks — no movement to update.
    }

    /**
     * Adds this block to the given game, as both a sprite and a
     * collidable.
     *
     * @param g the game to add this block to.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}