package listeners;
import sprites.Block;
import sprites.Ball;
/**
 * The HitListener interface should be implemented by any class that wants
 * to be notified of hit events. A listener registers itself with a
 * HitNotifier, and is notified via hitEvent whenever a hit occurs.
 */
public interface HitListener {

    /**
     * Called whenever the beingHit object is hit.
     *
     * @param beingHit the block that was hit.
     * @param hitter   the ball that is doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}