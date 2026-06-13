package sprites;
import biuoop.DrawSurface;
import java.util.List;
import java.util.ArrayList;

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
     * Notifies all sprites that a unit of time has passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }
    /**
    * Draws all sprites on the given surface.
    *
    * @param d the surface to draw on.
    */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.drawOn(d);
        }
    }
    /**
     * Removes the given sprite from the collection.
     *
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}