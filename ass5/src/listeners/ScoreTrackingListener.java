package listeners;
import sprites.Block;
import sprites.Ball;
import game.Counter;
/**
 * A ScoreTrackingListener is a HitListener that updates a score counter
 * whenever a block is hit and removed. Each removed block is worth 5
 * points.
 */
public class ScoreTrackingListener implements HitListener {
    private static final int POINTS_PER_BLOCK = 5;

    private Counter currentScore;

    /**
     * Constructor.
     *
     * @param scoreCounter the counter tracking the score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Called when a block is hit — adds points to the score.
     *
     * @param beingHit the block that was hit.
     * @param hitter   the ball doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(POINTS_PER_BLOCK);
    }
}