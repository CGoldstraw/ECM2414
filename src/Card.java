/**
 * Card stores a single card's value and provides methods to get and set the value.
 *
 * @author Charlie Goldstraw, Charles MacDonald-Smith
 * @version 1.0
 */
public class Card {
    // Given that the value of the card never changes, this is thread safe.
    private final int value;

    /**
     * Creates a new card with the given value.
     *
     * @param value The value of the card
     */
    public Card(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the card.
     *
     * @return The value of the card
     */
    public int getValue() {
        return this.value;
    }
}
