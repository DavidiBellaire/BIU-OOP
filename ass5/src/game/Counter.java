package game;
/**
 * The Counter class is a simple counter used to keep track of counts such
 * as the number of remaining blocks, remaining balls, or the score.
 */
public class Counter {
    private int count;

    /**
     * Constructor — creates a counter initialized to zero.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Constructor — creates a counter initialized to a given value.
     *
     * @param initial the initial count.
     */
    public Counter(int initial) {
        this.count = initial;
    }

    /**
     * Increases the current count by the given number.
     *
     * @param number the amount to add.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decreases the current count by the given number.
     *
     * @param number the amount to subtract.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * @return the current count.
     */
    public int getValue() {
        return this.count;
    }
}