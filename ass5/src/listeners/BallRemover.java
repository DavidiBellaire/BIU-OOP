package listeners;
import sprites.Block;
import sprites.Ball;
import game.Game;
import game.Counter;
/**
 * A BallRemover is a HitListener responsible for removing balls that hit
 * the death-region from the game, and keeping count of the number of
 * remaining balls.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructor.
     *
     * @param game           the game to remove balls from.
     * @param remainingBalls a counter of the remaining balls.
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Called when the death-region block is hit by a ball. Removes the
     * ball from the game and decreases the remaining-balls counter.
     *
     * @param beingHit the death-region block.
     * @param hitter   the ball that fell and hit it.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}