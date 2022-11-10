import java.util.ArrayList;

public class CardDeck {
    private final int deckNumber;

    private ArrayList<Card> cards;

    public CardDeck(int deckNumber) {
        this.deckNumber = deckNumber;
        this.cards = new ArrayList<Card>();
    }

    public synchronized void dealCard(Card card) {
        // Cards should be dealt to the top
        cards.add(card);
    }

    public int getDeckNumber() {
        return deckNumber;
    }

    public synchronized ArrayList<Card> getCards() {
        // Multiple threads can change the deck, so to prevent a thread caching the result
        // and obtaining stale data, the method must be synchronized.
        return cards;
    }
}
