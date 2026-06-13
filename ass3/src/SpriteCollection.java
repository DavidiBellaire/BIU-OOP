import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class holds the list of all sprites currently in
 * the game. It provides batch operations to draw all sprites on a given
 * surface, and to notify all sprites that a unit of time has passed.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructor — creates an empty sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds the given sprite to the collection.
     *
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Calls timePassed on every sprite in the collection.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : this.sprites) {
            s.timePassed();
        }
    }

    /**
     * Calls drawOn on every sprite in the collection.
     *
     * @param d the surface to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}