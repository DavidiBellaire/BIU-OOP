package listeners;
/**
 * The HitNotifier interface is implemented by objects that send
 * notifications when they are hit. Listeners can register and unregister
 * to receive these notifications.
 */
public interface HitNotifier {

    /**
     * Adds the given listener to the list of hit-event listeners.
     *
     * @param hl the listener to add.
     */
    void addHitListener(HitListener hl);

    /**
     * Removes the given listener from the list of hit-event listeners.
     *
     * @param hl the listener to remove.
     */
    void removeHitListener(HitListener hl);
}