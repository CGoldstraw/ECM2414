public class Card {
    // Given that the value of the card never changes, this is thread safe.
    private final int value;

    public Card(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
