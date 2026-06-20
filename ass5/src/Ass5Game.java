/*id: 214165417 */
import game.Game;

/**
 * This class is the entry point of the Arkanoid game. It creates
 * a Game, initializes it, and runs the animation loop.
 */
public class Ass5Game {

    /**
     * Main method — starts the game.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}