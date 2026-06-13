package listeners;
import sprites.Block;
import sprites.Ball;
import game.Game;
import game.Counter;
/**
 * A BlockRemover is a HitListener responsible for removing blocks from
 * the game when they are hit, changing the hitting ball's color to match
 * the removed block, and keeping count of the number of remaining blocks.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     *
     * @param game            the game to remove blocks from.
     * @param remainingBlocks a counter of the remaining blocks.
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Called when a block is hit. Changes the ball's color to the block's
     * color, removes the block from the game, unregisters this listener,
     * and decreases the remaining-blocks counter.
     *
     * @param beingHit the block that was hit.
     * @param hitter   the ball doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.setColor(beingHit.getColor());
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        this.remainingBlocks.decrease(1);
    }

}