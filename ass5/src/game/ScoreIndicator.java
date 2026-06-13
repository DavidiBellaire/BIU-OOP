package game;
import biuoop.DrawSurface;
import java.awt.Color;
import sprites.Sprite;
/**
 * A ScoreIndicator is a sprite that displays the current score at the top
 * of the screen. It holds a reference to the score counter and draws its
 * value each frame.
 */
public class ScoreIndicator implements Sprite {
    private static final int WIDTH = 800;
    private static final int BAR_HEIGHT = 20;
    private static final int TEXT_X = 350;
    private static final int TEXT_Y = 15;
    private static final int FONT_SIZE = 15;

    private Counter score;

    /**
     * Constructor.
     *
     * @param score the counter holding the current score.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * Draws the score bar and the current score text.
     *
     * @param d the surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, WIDTH, BAR_HEIGHT);
        d.setColor(Color.BLACK);
        d.drawText(TEXT_X, TEXT_Y, "Score: " + this.score.getValue(),
                FONT_SIZE);
    }

    /**
     * A score indicator does not change over time.
     */
    public void timePassed() {
        // Nothing to update.
    }

    /**
     * Adds this indicator to the game as a sprite.
     *
     * @param g the game to add to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}