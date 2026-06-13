import biuoop.DrawSurface;

/**
 * The Sprite interface defines a game object that has a visual presence
 * and can be updated over time.
 */
public interface Sprite {

    /**
     * Draws this sprite on the given surface.
     *
     * @param d the surface to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies this sprite that a unit of time has passed. The sprite
     * should update its internal state accordingly (movement, etc).
     */
    void timePassed();
}